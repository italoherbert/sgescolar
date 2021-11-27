
import {sistema} from '../../../../sistema/Sistema.js';

import AdministradorDetalhesComponent from './AdministradorDetalhesComponent.js';

export default class AdministradorDetalhesService {			
		
	constructor() {
		this.component = new AdministradorDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			administradorId : this.params.administradorId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraFormEditar() {				
		sistema.carregaPagina( 'administrador-form', { administradorId : this.params.administradorId, op : 'editar', titulo : "Edição de administrador" } );
	}
			
	paraAdministradorsTela() {
		sistema.carregaPagina( 'administrador-tela' );
	}
	
}
export const administradorDetalhes = new AdministradorDetalhesService(); 