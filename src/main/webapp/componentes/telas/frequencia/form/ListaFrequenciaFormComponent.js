
import {sistema} from '../../../../sistema/Sistema.js';

import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';
import {conversor} from '../../../../sistema/util/Conversor.js'; 

import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class ListaFrequenciaFormComponent extends RootFormComponent {
	
	listaFrequenciaTabelaCampos = [];
	matriculas = [];	
	aulas = [];
		
	constructor() {
		super( 'lista_frequencia_form', 'mensagem-el' );
		
		this.tabelaComponent = new TabelaComponent( '', 'lista-frequencia-tabela-el', this.listaFrequenciaTabelaCampos );
		
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
				
				selectService.carregaTurmaDisciplinasSelect( turmaId, 'turma_disciplinas_select', {
					onload : () => {							
						instance.setFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );							
					}
				} );
			
			},
			onchange : () => {
				let turmaId = instance.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasSelect( turmaId, 'turma_disciplinas_select', {
					onload : () => {
						instance.setFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );
					}
				} );
			}
		} );						
	}
	
	buscaEOuCarrega() {		
		let dataDia = super.getFieldValue( 'data_dia' );
		
		const instance = this;
		sistema.ajax( 'POST', '/api/lista-frequencia/busca/', {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				dataDia : conversor.formataData( dataDia )
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				if ( dados.temUmaOuMais === 'true' ) {
					this.carregaTabela( dados.matriculas, dados.aulas, new PresencaCallback( dados ) );			
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
			this.component.mostraErro( 'Selecione a turma primeiro.' );
			return;
		}

		const instance = this;
		sistema.ajax( "GET", "/api/matricula/lista/porturma/"+turmaId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaNovasPorMatriculas( dados );						
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	carregaNovasPorMatriculas( matriculas ) {
		this.listaFrequenciaTabelaCampos = [ 'Aluno', 'Modalidade' ];
			
		let turmaDisciplinaId = super.getFieldValue( 'turma_disciplina' );
		let dataDia = super.getFieldValue( 'data_dia' );	
						
		const instance = this;
		sistema.ajax( 'POST', '/api/aula/filtra/porsemanadia/'+turmaDisciplinaId, {
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
						
	carregaTabela( matriculas, aulas, presencaCallback ) {
		this.matriculas = matriculas;
		this.aulas = aulas;
				
		this.listaFrequenciaTabelaCampos = [ 'Aluno', 'Modalidade' ];
		for( let i = 0; i < aulas.length; i++ ) {
			let numeroAula = parseInt( aulas[ i ].numeroAula ); 
			this.listaFrequenciaTabelaCampos.push( numeroAula+'ª Aula' );
		}
						
		this.tabelaComponent.tabelaCampos = this.listaFrequenciaTabelaCampos;
		this.tabelaComponent.carregaTHead();		
		
		const instance = this;
				
		let tdados = [];
		for( let i = 0; i < matriculas.length; i++ ) {			
			tdados[ i ] = [];
			tdados[ i ].push( matriculas[ i ].alunoNome );
			tdados[ i ].push( "<select id=\"matricula_ftipo_"+i+"\" name=\"matricula_ftipo_"+i+"\" class=\"form-select\"></select>" );
			for( let j = 0; j < aulas.length; j++ ) {
				let checked = presencaCallback.isChecked( i, j );
				
				tdados[ i ].push( htmlBuilder.novoCheckboxHTML( 'matricula_cbx_'+i+"_"+j, checked ) );
			}
			
			selectService.carregaFrequenciaTiposSelect( 'matricula_ftipo_'+i, {
				onload : () => {
					let ftipo = presencaCallback.getFTipo( i );
					if ( ftipo !== '-1' )
						instance.setFieldValue( 'matricula_ftipo_'+i, ftipo );
				}
			} );			
		}
		
		this.tabelaComponent.carregaTBody( tdados );			
	}
						
	getJSON() {
		let frequenciaListas = [];
		
		let dataDia = this.getFieldValue( 'data_dia' );
		
		for( let i = 0; i < this.aulas.length; i++ ) {
			let frequencias = [];
			for( let j = 0; j < this.matriculas.length; j++ ) {
				frequencias[ j ] = {
					matriculaId : this.matriculas[ j ].id,
					estevePresente : super.getFieldChecked( 'matricula_cbx_'+j+"_"+i ),
					frequenciaTipo : super.getFieldValue( 'matricula_ftipo_'+j )		
				}		
			}
			frequenciaListas[ i ] = {
				dataDia : conversor.formataData( dataDia ),
				aulaId : this.aulas[ i ].id,
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
	
	isChecked( matI, aulaJ ) {
		if ( this.dados === undefined || this.dados === null )
			return true;
					
		return this.dados.estevePresenteMatriz[ aulaJ ][ matI ];
	}
	
	getFTipo( matI ) {
		if ( this.dados === undefined || this.dados === null )
			return "-1";
					
		return this.dados.frequenciaTiposAula0[ matI ].name;
	}
	
}
