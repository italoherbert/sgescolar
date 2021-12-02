
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';
import {conversor} from '../../../../sistema/util/Conversor.js'; 

import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class ListaFrequenciaFormComponent extends RootFormComponent {
	
	listaFrequenciaTabelaCampos = [ 'Aluno', 'Modalidade', 'Presença' ];
	matriculas = [];
		
	constructor() {
		super( 'lista_frequencia_form', 'mensagem-el' );
		
		this.tabelaComponent = new TabelaComponent( '', 'lista-frequencia-tabela-el', this.listaFrequenciaTabelaCampos );
		
		super.addFilho( this.tabelaComponent );
	}
					
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/lista-frequencia/get/"+this.globalParams.listaFrequenciaId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		} else {
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
							instance.setFieldValue( 'turma_disciplina', '-1' );							
						}
					} );
				
				},
				onchange : () => {
					let turmaId = instance.getFieldValue( 'turma' );
					selectService.carregaTurmaDisciplinasSelect( turmaId, 'turma_disciplinas_select', {
						onload : () => {
							instance.setFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );
						},
						onchange : () => {
							let turmaDisciplinaId = instance.getFieldValue( 'turma_disciplina' );
							selectService.carregaAulasSelect( turmaDisciplinaId, 'aulas_select' );
						}
					} );
				}
			} );
		}					
	}
	
	carregaMatriculas( matriculas ) {		
		let tdados = [];
		for( let i = 0; i < matriculas.length; i++ ) {			
			tdados[ i ] = [];
			tdados[ i ].push( matriculas[ i ].alunoNome );
			tdados[ i ].push( "<select id=\"matricula_ftipo_"+i+"\" name=\"matricula_ftipo_"+i+"\" class=\"form-select\"></select>" );			
			tdados[ i ].push( htmlBuilder.novoCheckboxHTML( 'matricula_cbx_'+i, true ) );
		}
		
		this.tabelaComponent.carregaTBody( tdados );
		
		for( let i = 0; i < matriculas.length; i++ )
			selectService.carregaFrequenciaTiposSelect( 'matricula_ftipo_'+i );
		
		this.matriculas = matriculas;
	}
				
	getJSON() {
		let frequencias = [];
		for( let i = 0; i < this.matriculas.length; i++ ) {
			frequencias[ i ] = { 
				matriculaId : this.matriculas[ i ].id,
				estevePresente : super.getFieldChecked( 'matricula_cbx_'+i ),
				frequenciaTipo : super.getFieldValue( 'matricula_ftipo_'+i )
			}
		}
		
		return {
			dataDia : conversor.formataData( new Date() ),
			numeroAula : super.getFieldValue( 'numero_aula' ),
			frequencias : frequencias
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
		
	limpaForm() {			
		super.setFieldValue( 'turma', '-1' );		
	}		
}
