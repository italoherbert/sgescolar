
import {sistema} from '../../../../sistema/Sistema.js';

import UsuarioGrupoDetalhesComponent from './UsuarioGrupoDetalhesComponent.js';

export default class UsuarioGrupoDetalhesService {			
		
	constructor() {
		this.component = new UsuarioGrupoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			usuarioGrupoId : this.params.usuarioGrupoId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraFormEditar() {	
		sistema.carregaPagina( 'usuario-grupo-form', { usuarioGrupoId : this.params.usuarioGrupoId, op : 'editar', titulo : "Edição de grupo de usuário" } );
	}
			
	paraUsuarioGruposTela() {
		sistema.carregaPagina( 'usuario-grupo-tela' );
	}
	
}
export const usuarioGrupoDetalhes = new UsuarioGrupoDetalhesService(); 