
import {sistema} from '../../../sistema/Sistema.js';

import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';
import {conversor} from '../../../sistema/util/Conversor.js'; 

import {selectService} from '../../service/SelectService.js';

import {perfilService} from '../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../component/RootFormComponent.js';
import TabelaComponent from '../../component/tabela/TabelaComponent.js';

export default class ListaFrequenciaComponent extends RootFormComponent {
	
	requenciaTabelaCampos = [];
	matriculas = [];	
	horarioAulas = [];
		
	constructor() {
		super( 'lista_frequencia_form', 'mensagem-el' );
		
		this.tabelaComponent = new TabelaComponent( '', 'lista-frequencia-tabela-el', this.requenciaTabelaCampos );
		
		super.addFilho( this.tabelaComponent );
	}
					
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
					
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId === '-1' ) {
			this.mostraErro( 'O ano letivo não foi selecionado.' ); 
			return;
		}
		
		selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
			onload : () => {
				let turmaId = perfilService.getTurmaID();
				instance.setFieldValue( 'turma', turmaId );
				if ( turmaId !== '-1' ) {				
					selectService.carregaTurmaDisciplinasSelect( turmaId, 'turma_disciplinas_select', {
						onload : () => {							
							instance.setSelectFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );							
						}
					} );
				}			
			},
			onchange : () => {
				let turmaId = instance.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasSelect( turmaId, 'turma_disciplinas_select', {
					onload : () => {
						instance.setSelectFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );
					}
				} );
			}
		} );						
	}
	
	buscaEOuCarrega() {		
		let dataDia = super.getFieldValue( 'data_dia' );
		let turmaDisciplinaId = super.getFieldValue( 'turma_disciplina' );
		
		const instance = this;
		sistema.ajax( 'POST', '/api/lista-frequencia/filtra/'+turmaDisciplinaId, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				dataDia : conversor.formataData( dataDia )
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				if ( dados.temUmaOuMais === 'true' ) {
					this.carregaTabela( dados.matriculas, dados.horarioAulas, new PresencaCallback( dados ) );			
				} else {
					this.carregaNovas();
				}
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );
	}
	
	carregaNovas() {
		let turmaId = super.getFieldValue( 'turma' );

		if ( turmaId === undefined || turmaId === null || turmaId === '' || turmaId === '-1' ) {
			this.tabelaComponent.mostraErro( 'Selecione a turma primeiro.' );
			return;
		}

		const instance = this;
		sistema.ajax( "POST", "/api/matricula/filtra/"+turmaId, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				nomeIni : '*'
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaNovasPorMatriculas( dados );						
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );
	}
	
	carregaNovasPorMatriculas( matriculas ) {
		this.requenciaTabelaCampos = [ 'Aluno', 'Modalidade' ];
			
		let turmaDisciplinaId = super.getFieldValue( 'turma_disciplina' );
		let dataDia = super.getFieldValue( 'data_dia' );	
						
		const instance = this;
		sistema.ajax( 'POST', '/api/horario/filtra/porsemanadia/'+turmaDisciplinaId, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				dataDia : conversor.formataData( dataDia )
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );	
				if ( dados.length === 0 ) {
					instance.tabelaComponent.limpaTudo();
					instance.mostraInfo( 'Nenhuma aula encontrada para a data e disciplina informadas.')
				} else {												
					instance.carregaTabela( matriculas, dados, new PresencaCallback() );
				}
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );							
	}
						
	carregaTabela( matriculas, horarioAulas, presencaCallback ) {
		this.matriculas = matriculas;
		this.horarioAulas = horarioAulas;
				
		this.requenciaTabelaCampos = [ 'Aluno', 'Modalidade' ];
		for( let i = 0; i < horarioAulas.length; i++ ) {
			let numeroAula = parseInt( horarioAulas[ i ].numeroAula ); 
			this.requenciaTabelaCampos.push( numeroAula+'ª Aula' );
		}
						
		this.tabelaComponent.tabelaCampos = this.requenciaTabelaCampos;
		this.tabelaComponent.carregaTHead();		
		
		const instance = this;
				
		let tdados = [];
		for( let i = 0; i < matriculas.length; i++ ) {			
			tdados[ i ] = [];
			tdados[ i ].push( matriculas[ i ].alunoNome );
			tdados[ i ].push( "<select id=\"modalidade_"+i+"\" name=\"modalidade_"+i+"\" class=\"form-select\"></select>" );
			for( let j = 0; j < horarioAulas.length; j++ ) {
				let checked = presencaCallback.isChecked( i, j );
				
				tdados[ i ].push( htmlBuilder.novoCheckboxHTML( 'esta_presente_cbx_'+i+"_"+j, checked ) );
			}
			
			selectService.carregaFrequenciaTiposSelect( 'modalidade_'+i, {
				onload : () => {
					let modalidade = presencaCallback.getModalidade( i );
					if ( modalidade !== '-1' ) {						
						instance.setFieldValue( 'modalidade_'+i, modalidade );
						
						presencaCallback.disableCBXSeRemota( i, horarioAulas.length );
					}
				}, 
				onchange : () => presencaCallback.disableCBXSeRemota( i, horarioAulas.length )
			} );	
			
		}
		
		this.tabelaComponent.carregaTBody( tdados );										
	}
						
	getJSON() {
		let frequenciaListas = [];
		
		let dataDia = this.getFieldValue( 'data_dia' );
		
		for( let i = 0; i < this.horarioAulas.length; i++ ) {
			let frequencias = [];
			for( let j = 0; j < this.matriculas.length; j++ ) {
				frequencias[ j ] = {
					matriculaId : this.matriculas[ j ].id,
					estevePresente : super.getFieldChecked( 'esta_presente_cbx_'+j+"_"+i ),
					modalidade : super.getFieldValue( 'modalidade_'+j )		
				}		
			}
			frequenciaListas[ i ] = {
				dataDia : conversor.formataData( dataDia ),
				horarioAulaId : this.horarioAulas[ i ].id,
				frequencias : frequencias
			}
		}
		
		return {
			frequenciaListas : frequenciaListas
		}
	}	
							
}

class PresencaCallback {
	
	constructor( dados ) {
		this.dados = dados;					
	}
	
	isChecked( matI, horarioAulaJ ) {
		if ( this.dados === undefined || this.dados === null )
			return true;
					
		return this.dados.estevePresenteMatriz[ horarioAulaJ ][ matI ] === 'true';
	}
	
	getModalidade( matI ) {
		if ( this.dados === undefined || this.dados === null )
			return "-1";
					
		return this.dados.horarioAula0Modalidades[ matI ].name;
	}	
	
	disableCBXSeRemota( matI, horarioAulasQuant ) {					
		let modalidadesSelect = document.getElementById( 'modalidade_'+matI );				
								
		for( let j = 0; j < horarioAulasQuant; j++ ) {																		
			let cbx = document.getElementById( 'esta_presente_cbx_'+matI+'_'+j );			
			if ( modalidadesSelect.value === 'REMOTA' ) {
				cbx.checked = true;
				cbx.disabled = true;
			} else {
				cbx.disabled = false;
			}
		}	
	}
		
}
