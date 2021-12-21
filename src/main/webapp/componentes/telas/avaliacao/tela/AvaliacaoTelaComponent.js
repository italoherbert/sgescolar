
import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class AvaliacaoTelaComponent extends RootFormComponent {
										
	constructor() {
		super( 'avaliacao_filtro_form', 'mensagem-el' );						
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let professorId = this.globalParams.professorId;
		
		const instance = this;
		selectService.carregaTurmasPorProfessorSelect( professorId, 'turmas_select', {
			onload : () => {
				instance.setSelectFieldValue( 'turma', perfilService.getTurmaID() );
			},
			onchange : () => {
				let turmaId = instance.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasPorTurmaEProfessorSelect( turmaId, professorId, 'turmas_disciplinas_select', {
					onload : () => {
						instance.setSelectFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );				
					}		
				} );
			}
		} );								
	}
											
}
