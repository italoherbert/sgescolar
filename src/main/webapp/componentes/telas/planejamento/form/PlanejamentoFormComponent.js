
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import BNCCHabilidadeAutoCompleteFormComponent from '../../../autocomplete/BNCCHabilidadeAutoCompleteFormComponent.js';

export default class PlanejamentoFormComponent extends RootFormComponent {
	
	objetivos = [];
	conteudos = [];
	
	anexosContador = 0;
					
	removeObjetivoHTMLLink = null;
	removeConteudoHTMLLink = null;
	removeAnexoHTMLLink = null;
										
	constructor() {
		super( 'planejamento_form', 'mensagem-el' );
		
		this.bnccHabilidadeAutoCompleteComponent = new BNCCHabilidadeAutoCompleteFormComponent( 'planejamento_form', 'objetivo-autocomplete-el' );
		this.objetivosTabelaComponent = new TabelaComponent( 'objetivos_', 'tabela_el', [] );
		this.conteudosTabelaComponent = new TabelaComponent( 'conteudos_', 'tabela_el', [] );
		
		this.objetivosTabelaComponent.tabelaClasses = "tabela-plano-obj-con";
		this.conteudosTabelaComponent.tabelaClasses = "tabela-plano-obj-con";
				
		super.addFilho( this.bnccHabilidadeAutoCompleteComponent );
		super.addFilho( this.objetivosTabelaComponent );
		super.addFilho( this.conteudosTabelaComponent );						
	}	
				
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/planejamento/get/"+this.globalParams.planejamentoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				}
			} );
		} else {
			let anoLetivoId = perfilService.getAnoLetivoID();
			if ( anoLetivoId == '-1' ) {
				super.mostraErro( 'Ano letivo não selecionado.' );
				return;
			}
			
			const instance = this;
			selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
				onload : () => {
					let turmaId = perfilService.getTurmaID();
					if ( turmaId != '-1' )
						instance.setSelectFieldValue( 'turma', turmaId );
				},
				onchange : () => {
					let turmaId = instance.getFieldValue( 'turma' );
					selectService.carregaTurmaDisciplinasSelect( turmaId, 'turmas_disciplinas_select', {
						onload : () => {
							let turmaDisciplinaId = perfilService.getTurmaDisciplinaID();
							if ( turmaDisciplinaId != '-1' )
								instance.setSelectFieldValue( 'turma_disciplina', turmaDisciplinaId );
						},
						onchange : () => {
							let turmaDisciplinaId = instance.getFieldValue( 'turma_disciplina' );
							selectService.carregaProfessorAlocacaoPorTurmaDisciplinaSelect( turmaDisciplinaId, 'professores_alocacoes_select', {
								onload : () => {
									let professorAlocId = instance.getFieldValue( 'professor_alocacao' );
									selectService.carregaEnsinoPlanejamentosSelect( professorAlocId, 'planos_ensinos_select' );
								}
							} );
						}
					} );
				}
			} );						
			
			selectService.carregaPlanejamentoTiposSelect( 'planejamento_tipos_select' );
		}
		
	}
	
	copiaPlanoEnsinoConteudo() {
		let planejamentoId = super.getFieldValue( 'plano_ensino' );
		
		const instance = this;
		sistema.ajax( 'GET', '/api/planejamento/get/'+planejamentoId, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				instance.removeTodosOsConteudos();
				
				for( let i = 0; i < dados.conteudos.length; i++ )
					instance.conteudos.push( { conteudo : dados.conteudos[ i ].conteudo } );
					
				instance.carregaConteudos();				
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );	
	}
	
	addObjetivo() {		
		this.objetivos.push( { objetivo : this.bnccHabilidadeAutoCompleteComponent.getInputValue() } );
		this.bnccHabilidadeAutoCompleteComponent.limpaTudo();		
		
		this.carregaObjetivos();		
	}	
	
	addConteudo() {		
		this.conteudos.push( { conteudo : super.getFieldValue( 'conteudo' ) } );
		super.setFieldValue( 'conteudo', '' );	
		
		this.carregaConteudos();			
	}
		
	removeObjetivo( i ) {
		this.objetivos.splice( i, 1 );
		this.carregaObjetivos();		
	}
		
	removeConteudo( i ) {
		this.conteudos.splice( i, 1 );		
		this.carregaConteudos();
	}
	
	removeAnexo( i ) {
		document.getElementById( 'campo-anexos-el'+i ).innerHTML = "";	
	}
	
	removeTodosOsConteudos() {
		this.conteudos.splice( 0, this.conteudos.length );
	}
				
	carregaObjetivos() {
		let tdados = [];
				
		for( let i = 0; i < this.objetivos.length; i++ ) {
			let objetivo = this.objetivos[ i ].objetivo;
			
			let removerLink = this.removeObjetivoHTMLLink( i );
			
			tdados[ i ] = new Array();			
			tdados[ i ].push( objetivo );
			tdados[ i ].push( removerLink );							
		}
		
		this.objetivosTabelaComponent.carregaTBody( tdados );
	}	
	
	carregaConteudos() {
		let tdados = [];
		
		for( let i = 0; i < this.conteudos.length; i++ ) {
			let conteudo = this.conteudos[ i ].conteudo;
			
			let removerLink = this.removeConteudoHTMLLink( i );
			
			tdados[ i ] = new Array();			
			tdados[ i ].push( conteudo );
			tdados[ i ].push( removerLink );							
		}		
		
		this.conteudosTabelaComponent.carregaTBody( tdados );
	}
	
	addAnexoField() {
		let removeAnexoLink = this.removeAnexoHTMLLink( this.anexosContador );
		
		let html = "<div id=\"campo-anexos-el"+( this.anexosContador )+"\" class=\"d-flex align-items-center justify-content-between\">" + 
						"<input type=\"file\" id=\"file"+( this.anexosContador )+"\" name=\"file"+( this.anexosContador )+"\" class=\"form-control d-inline-block\" />" +
						"<div class=\"px-2\">" + removeAnexoLink + "</div>" +
					"</div>" +
					"<span id=\"add-anexo-el" + ( this.anexosContador + 1 ) + "\"></span>";
										
		document.getElementById( 'add-anexo-el' + this.anexosContador ).innerHTML = html;
		
		this.anexosContador++;		
	}
		
	getJSON() {			
		return {
			descricao : super.getFieldValue( 'descricao' ),
			metodologia : super.getFieldValue( 'metodologia' ),
			metodosAvaliacao : super.getFieldValue( 'metodos_avaliacao' ),
			recursos : super.getFieldValue( 'recursos' ),
			referencias : super.getFieldValue( 'referencias' ),
			
			tipo : super.getFieldValue( 'planejamento_tipo' ),
			dataInicio : conversor.formataData( super.getFieldValue( 'data_inicio' ) ),
			dataFim : conversor.formataData( super.getFieldValue( 'data_fim' ) ),
			
			objetivos : this.objetivos,
			conteudos : this.conteudos,
		}
	}	
		
	getFiles() {
		let files = [];	
		for( let i = 0; i < this.anexosContador; i++ ) {
			let fel = document.getElementById( "file"+i );
			files.push( fel.files[ 0 ] );
		}
		
		return files;
	}	
		
	carregaJSON( dados ) {				
		let turmaId = dados.professorAlocacao.turmaDisciplina.turmaId;
		let turmaDesc = dados.professorAlocacao.turmaDisciplina.turmaDescricaoDetalhada;
		
		let turmaDisciplinaId = dados.professorAlocacao.turmaDisciplina.id;
		let turmaDisciplinaDesc = dados.professorAlocacao.turmaDisciplina.disciplinaDescricao;
		
		let professorAlocacaoId = dados.professorAlocacao.id;
		let professorNome = dados.professorAlocacao.professorNome;
		
		selectService.carregaUmaOptionSelect( 'turmas_select', turmaId, turmaDesc );
		selectService.carregaUmaOptionSelect( 'turmas_disciplinas_select', turmaDisciplinaId, turmaDisciplinaDesc );
		selectService.carregaUmaOptionSelect( 'professores_alocacoes_select', professorAlocacaoId, professorNome );
		selectService.carregaUmaOptionSelect( 'planejamento_tipos_select', dados.tipo.name, dados.tipo.label );
		
		selectService.carregaEnsinoPlanejamentosSelect( professorAlocacaoId, 'planos_ensinos_select' );
			
		super.setFieldValue( 'data_inicio', conversor.valorData( dados.dataInicio ) );
		super.setFieldValue( 'data_fim', conversor.valorData( dados.dataFim ) );
				
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'metodologia', dados.metodologia );
		super.setFieldValue( 'metodos_avaliacao', dados.metodosAvaliacao );
		super.setFieldValue( 'recursos', dados.recursos );
		super.setFieldValue( 'referencias', dados.referencias );
		
		for( let i = 0; i < dados.objetivos.length; i++ )
			this.objetivos.push( { objetivo : dados.objetivos[ i ].objetivo } );
		for( let i = 0; i < dados.conteudos.length; i++ )
			this.conteudos.push( { conteudo : dados.conteudos[ i ].conteudo } );
			
		this.carregaObjetivos();
		this.carregaConteudos();
	}	
		
	limpaForm() {
		this.objetivos = [];
		this.conteudo = [];
		this.anexos = [];
		
		this.objetivosTabelaComponent.limpaTBody();
		this.conteudosTabelaComponent.limpaTBody();		
		
		super.setFieldValue( 'data_inicio', '' );
		super.setFieldValue( 'data_fim', '' );
		
		super.setFieldValue( 'descricao', '' );
		super.setFieldValue( 'metodologia', '' );
		super.setFieldValue( 'metodos_avaliacao', '' );
		super.setFieldValue( 'recursos', '' );
		super.setFieldValue( 'referencias', '' );	
	}		
}
