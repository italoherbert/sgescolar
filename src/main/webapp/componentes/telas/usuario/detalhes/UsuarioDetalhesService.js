
import {sistema} from '../../../../../sistema/Sistema.js';

import UsuarioDetalhesComponent2 from './UsuarioDetalhesComponent2.js';

export default class UsuarioDetalhesService {			
		
	constructor() {
		this.component = new UsuarioDetalhesComponent2();
	}	
		
	onCarregado() {
		this.component.configura( {
			usuarioId : this.params.usuarioId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'usuario-form-2', { usuarioId : this.params.usuarioId, op : 'editar', titulo : "Edição de usuario" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'usuario-tela' );
	}
	
}
export const usuarioDetalhes = new UsuarioDetalhesService(); 