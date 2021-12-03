
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
	
	carregaMatriculas( matriculas ) {
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
					instance.tabelaComponent.limpaTBody();
					instance.mostraInfo( 'Nenhuma aula encontrada para a data e turma informadas.')
				} else {							
					instance.carregaTabela( matriculas, dados );
				}
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );							
	}
	
	carregaTabela( matriculas, aulas ) {
		this.matriculas = matriculas;
		this.aulas = aulas;
		
		this.listaFrequenciaTabelaCampos = [ 'Aluno', 'Modalidade' ];
		for( let i = 0; i < aulas.length; i++ ) {
			let numeroAula = parseInt( aulas[ i ].numeroAula ); 
			this.listaFrequenciaTabelaCampos.push( numeroAula+'ª Aula' );
		}
						
		this.tabelaComponent.tabelaCampos = this.listaFrequenciaTabelaCampos;
		this.tabelaComponent.carregaTHead();		
				
		let tdados = [];
		for( let i = 0; i < matriculas.length; i++ ) {			
			tdados[ i ] = [];
			tdados[ i ].push( matriculas[ i ].alunoNome );
			tdados[ i ].push( "<select id=\"matricula_ftipo_"+i+"\" name=\"matricula_ftipo_"+i+"\" class=\"form-select\"></select>" );
			for( let j = 0; j < aulas.length; j++ )
				tdados[ i ].push( htmlBuilder.novoCheckboxHTML( 'matricula_cbx_'+i+"_"+j, true ) );			
		}
		
		this.tabelaComponent.carregaTBody( tdados );
		
		for( let i = 0; i < matriculas.length; i++ )
			selectService.carregaFrequenciaTiposSelect( 'matricula_ftipo_'+i );
		
		this.matriculas = matriculas;		
	}
	
	carregaListasFrequencias() {
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
				alert( JSON.stringify( dados ) );
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );
	}
				
	getJSON() {
		let listasFrequencias = [];
		
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
			listasFrequencias[ i ] = {
				dataDia : conversor.formataData( dataDia ),
				aulaId : this.aulas[ i ].id,
				frequencias : frequencias
			}
		}
		
		return {
			listas : listasFrequencias
		}
	}	
		
	carregaJSON( dados ) {		
		let turmaId = dados.aula.turmaDisciplina.turmaId;		
		let turmaDescricao = dados.aula.turmaDisciplina.turmaDescricao;
		
		let turmaDisciplinaId = dados.aula.turmaDisciplina.id;
		let disciplinaDescricao = dados.aula.turmaDisciplina.disciplinaDescricao;
									
		selectService.carregaUmaOptionSelect( 'turmas_select', turmaId, turmaDescricao );					
		selectService.carregaUmaOptionSelect( 'turmas_disciplinas_select', turmaDisciplinaId, disciplinaDescricao );					
		
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'sigla', dados.sigla );
	}	
		
}
