
import RootFormComponent from '../../../../component/RootFormComponent.js';

import {selectService} from '../../../../service/SelectService.js';

export default class PlanejamentoRelatorioFormComponent extends RootFormComponent {
	
	constructor() {
		super( 'planejamento_relatorio_form', 'mensagem-el' );
	}
	
	carregouHTMLCompleto() {
		const instance = this;
		
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId !== '-1' ) {
			selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
				onload : () => {
					instance.setFieldValue( 'turma', perfilService.getTurmaID() );			
				},
				onchange : () => {
					let turmaId = instance.getFieldValue( 'turma' );					
					selectService.carregaTurmaDisciplinasSelect( turmaId, 'turmas_disciplinas_select', {
						onchange : () => {
							let turmaDisciplinaId = instance.getFieldValue( 'turma_disciplina' );
							selectService.carregaProfessorAlocacoesPorTurmaDisciplinaSelect( turmaDisciplinaId, 'professores_alocacoes_select', {
								onchange : () => {
									let professorAlocacaoId = instance.getFieldValue( 'professor_alocacao' );
									selectService.carregaPlanejamentosSelect( professorAlocacaoId, 'planejamentos_select' );
								}
							} );
						}
					} );								
				}
			} );
		}		
	}
	
}