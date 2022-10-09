import {sistema} from "../../../../sistema/Sistema.js";

import TurmaDisciplinaFormComponent from './TurmaDisciplinaFormComponent.js';

export default class TurmaDisciplinaFormService {

	constructor() {
		this.formComponent = new TurmaDisciplinaFormComponent();
	}

	onCarregado() {								
		this.formComponent.configura( {} );
		this.formComponent.carregaHTML();	
	}
	
	sincroniza() {
		this.formComponent.limpaMensagem();				
		let turmaId = this.formComponent.getFieldValue( 'turma' );
		
		if ( isNaN( parseInt( turmaId ) ) === true ) {
			this.formComponent.mostraErro( 'A seleção da turma é obrigatória para esta operação.' );
			return;
		}
		
		const instance = this;	
		sistema.ajax( 'POST', '/api/turma-disciplina/sincroniza/'+turmaId, {			
			sucesso : ( resposta ) => {
				instance.formComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Disciplina e turma vinculadas com sucesso!' );
			},
			erro : ( msg ) => {
				this.formComponent.mostraErro( msg );
			}
		} );
	}
	
	vincula() {
		this.formComponent.limpaMensagem();				
		
		let disciplinaId = this.formComponent.getFieldValue( 'disciplina' );
		let turmaId = this.formComponent.getFieldValue( 'turma' );
		
		if ( isNaN( parseInt( disciplinaId ) ) === true || isNaN( parseInt( turmaId ) ) === true ) {
			this.formComponent.mostraErro( 'A seleção da turma e disciplina são obrigatórias para este registro.' );
			return;
		}
						
		const instance = this;	
		sistema.ajax( 'POST', '/api/turma-disciplina/registra/'+turmaId+"/"+disciplinaId, {			
			sucesso : ( resposta ) => {
				instance.formComponent.setFieldValue( 'disciplina', '0' );
				instance.formComponent.mostraInfo( 'Disciplina e turma vinculadas com sucesso!' );
			},
			erro : ( msg ) => {
				this.formComponent.mostraErro( msg );
			}
		} );
	}
		
	paraTurmaDisciplinaTela() {
		sistema.carregaPagina( 'turma-disciplina-tela' );
	}		

}
export const turmaDisciplinaForm = new TurmaDisciplinaFormService();