
import {sistema} from '../../../../sistema/Sistema.js';

import RecursoDetalhesComponent from './RecursoDetalhesComponent.js';

export default class RecursoDetalhesService {			
		
	constructor() {
		this.component = new RecursoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			recursoId : this.params.recursoId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {	
		sistema.carregaPagina( 'recurso-form', { recursoId : this.params.recursoId, op : 'editar', titulo : "Edição de recurso" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'recurso-tela' );
	}
	
}
export const recursoDetalhes = new RecursoDetalhesService(); 