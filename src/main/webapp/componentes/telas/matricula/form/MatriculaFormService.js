
import {sistema} from "../../../../sistema/Sistema.js";

import AlunoAutoCompleteFormComponent from '../../../autocomplete/AlunoAutoCompleteFormComponent.js';
import MatriculaFormComponent from './MatriculaFormComponent.js';

export default class MatriculaFormService {

	constructor() {
		this.formComponent = new MatriculaFormComponent();
		this.alunoAutoCompleteFormComponent = new AlunoAutoCompleteFormComponent( 'matricula_form', 'aluno-autocomplete-el' );		
	}

	onCarregado() {		
		this.formComponent.configura( {} );
		this.formComponent.carregaHTML();
							
		this.alunoAutoCompleteFormComponent.configura( {} );
		this.alunoAutoCompleteFormComponent.carregaHTML();
	}
			
	aloca() {
		this.formComponent.limpaMensagem();				
		
		let turmaId = this.formComponent.getFieldValue( 'turma' );
		let alunoId = this.alunoAutoCompleteFormComponent.selectedId;
			
		if ( isNaN( parseInt( turmaId ) ) === true || isNaN(  parseInt( alunoId ) ) === true ) {
			this.formComponent.mostraErro( 'A seleção de turma e aluno são obrigatórias para a matrícula.' );
			return;
		}	
						
		const instance = this;	
		sistema.ajax( 'POST', '/api/matricula/registra/'+alunoId+"/"+turmaId, {			
			sucesso : ( resposta ) => {
				instance.alunoAutoCompleteFormComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Aluno matriculado com sucesso!' );
			},
			erro : ( msg ) => {
				instance.formComponent.mostraErro( msg );
			}
		} );
	}
				
	paraTela() {
		sistema.carregaPagina( 'matricula-tela' );
	}

}
export const matriculaForm = new MatriculaFormService();