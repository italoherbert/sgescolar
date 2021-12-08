
import {sistema} from '../../../../sistema/Sistema.js';

import SecretarioDetalhesComponent from './SecretarioDetalhesComponent.js';

export default class SecretarioDetalhesService {			
		
	constructor() {
		this.component = new SecretarioDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			secretarioId : this.params.secretarioId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'secretario-form', { secretarioId : this.params.secretarioId, op : 'editar', titulo : "Edição de secretario" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'secretario-tela' );
	}
	
}
export const secretarioDetalhes = new SecretarioDetalhesService(); 