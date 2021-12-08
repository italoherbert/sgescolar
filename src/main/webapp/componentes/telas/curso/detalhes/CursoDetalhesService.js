
import {sistema} from '../../../../sistema/Sistema.js';

import CursoDetalhesComponent from './CursoDetalhesComponent.js';

export default class CursoDetalhesService {			
		
	constructor() {
		this.component = new CursoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			cursoId : this.params.cursoId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'curso-form', { cursoId : this.params.cursoId, op : 'editar', titulo : "Edição de curso" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'curso-tela' );
	}
	
}
export const cursoDetalhes = new CursoDetalhesService(); 