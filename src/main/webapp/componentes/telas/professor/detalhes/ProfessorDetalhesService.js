
import {sistema} from '../../../../sistema/Sistema.js';

import ProfessorDetalhesComponent from './ProfessorDetalhesComponent.js';

export default class ProfessorDetalhesService {			
		
	constructor() {
		this.component = new ProfessorDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			professorId : this.params.professorId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'professor-form', { professorId : this.params.professorId, op : 'editar', titulo : "Edição de professor" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'professor-tela' );
	}
	
}
export const professorDetalhes = new ProfessorDetalhesService(); 