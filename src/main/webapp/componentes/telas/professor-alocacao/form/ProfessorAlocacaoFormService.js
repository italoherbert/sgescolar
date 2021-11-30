
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
		
		let turmaId = this.formComponent.getFieldValue( 'turma' );
		let disciplinaId = this.formComponent.getFieldValue( 'disciplina' );
		let professorId = this.professorAutoCompleteFormComponent.selectedId;
			
		if ( isNaN( parseInt( turmaId ) ) === true || isNaN(  parseInt( disciplinaId ) ) === true || isNaN(  parseInt( professorId ) ) === true ) {
			this.formComponent.mostraErro( 'A seleção de turma, disciplina e professor são obrigatórias para esta alocação.' );
			return;
		}	
						
		const instance = this;	
		sistema.ajax( 'POST', '/api/professor-alocacao/registra/'+turmaId+'/'+disciplinaId+'/'+professorId, {			
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