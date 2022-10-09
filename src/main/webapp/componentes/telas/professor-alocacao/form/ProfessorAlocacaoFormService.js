
import {sistema} from "../../../../sistema/Sistema.js";

import ProfessorAutoCompleteFormComponent from '../../../autocomplete/ProfessorAutoCompleteFormComponent.js';
import ProfessorAlocacaoFormComponent from './ProfessorAlocacaoFormComponent.js';

export default class ProfessorAlocacaoFormService {

	constructor() {
		this.formComponent = new ProfessorAlocacaoFormComponent();
		this.professorAutoCompleteFormComponent = new ProfessorAutoCompleteFormComponent( 'professor_alocacao_form', 'form-professor-el' );		
	}

	onCarregado() {		
		this.formComponent.configura( {} );
		this.formComponent.carregaHTML();
							
		this.professorAutoCompleteFormComponent.configura( {} );
		this.professorAutoCompleteFormComponent.carregaHTML();
	}
			
	aloca() {
		this.formComponent.limpaMensagem();				
		
		let turmaDisciplinaId = this.formComponent.getFieldValue( 'turma_disciplina' );
		let professorId = this.professorAutoCompleteFormComponent.selectedId;
			
		if ( isNaN(  parseInt( turmaDisciplinaId ) ) === true || isNaN(  parseInt( professorId ) ) === true ) {
			this.formComponent.mostraErro( 'As seleções da disciplina e do professor são obrigatórias para esta alocação.' );
			return;
		}	
						
		const instance = this;	
		sistema.ajax( 'POST', '/api/professor-alocacao/registra/'+turmaDisciplinaId+'/'+professorId, {			
			sucesso : ( resposta ) => {
				instance.formComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Professor alocado com sucesso!' );
			},
			erro : ( msg ) => {
				instance.formComponent.mostraErro( msg );
			}
		} );
	}
				
	paraProfessorAlocacaoTela() {
		sistema.carregaPagina( 'professor-alocacao-tela' );
	}

}
export const professorAlocacaoForm = new ProfessorAlocacaoFormService();