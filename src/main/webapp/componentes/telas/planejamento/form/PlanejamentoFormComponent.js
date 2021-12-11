
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import BNCCHabilidadeAutoCompleteFormComponent from '../../../autocomplete/BNCCHabilidadeAutoCompleteFormComponent.js';

export default class PlanejamentoFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'planejamento_form', 'mensagem-el' );
		
		this.bnccHabilidadeAutoCompleteComponent = new BNCCHabilidadeAutoCompleteFormComponent( 'planejamento_form', 'objetivo-autocomplete-el' );
		
		super.addFilho( this.bnccHabilidadeAutoCompleteComponent );						
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
					let turmaId = super.getFieldValue( 'turma' );
					selectService.carregaTurmaDisciplinasSelect( turmaId, 'turmas_disciplinas_select', {
						onload : () => {
							let turmaDisciplinaId = perfilService.getTurmaDisciplinaID();
							if ( turmaDisciplinaId != '-1' )
								instance.setSelectFieldValue( 'turma_disciplina', turmaDisciplinaId );
						},
						onchange : () => {
							let turmaDisciplinaId = super.getFieldValue( 'turma_disciplina' );
							selectService.carregaProfessorAlocacaoPorTurmaDisciplinaSelect( turmaDisciplinaId, 'professores_alocacoes_select' );
						}
					} );
				}
			} );
		}
		
	}
		
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			metodologia : super.getFieldValue( 'metodologia' ),
			metodosAvaliacao : super.getFieldValue( 'metodos_avaliacao' ),
			recursos : super.getFieldValue( 'recursos' ),
			referencias : super.getFieldValue( 'referencias' ),
			
			objetivos : [],
			conteudos : [],
			anexos : []
		}
	}	
		
	carregaJSON( dados ) {
		let turmaId = dados.professorAlocacao.turmaDisciplina.turmaId;
		let turmaDesc = dados.professorAlocacao.turmaDisciplina.turmaDescricaoDetalhada;
		
		let turmaDisciplinaId = dados.professorAlocacao.turmaDisciplina.id;
		let turmaDisciplinaDesc = dados.professorAlocacao.turmaDisciplina.descricao;
		
		let professorAlocacaoId = dados.professorAlocacao.id;
		let professorNome = dados.professorAlocacao.professorNome;
		
		selectService.carregaUmaOptionSelect( 'turmas_select', turmaId, turmaDesc );
		selectService.carregaUmaOptionSelect( 'turmas_disciplinas_select', turmaDisciplinaId, turmaDisciplinaDesc );
		selectService.carregaUmaOptionSelect( 'professores_alocacoes_select', professorAlocacaoId, professorNome );
		
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'metodologia', dados.metodologia );
		super.setFieldValue( 'metodos_avaliacao', dados.metodosAvaliacao );
		super.setFieldValue( 'recursos', dados.recursos );
		super.setFieldValue( 'referencias', dados.referencias );
	}	
		
	limpaForm() {
		super.setFieldValue( 'descricao', '' );
		super.setFieldValue( 'metodologia', '' );
		super.setFieldValue( 'metodos_avaliacao', '' );
		super.setFieldValue( 'recursos', '' );
		super.setFieldValue( 'referencias', '' );	
	}		
}
