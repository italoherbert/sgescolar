
import {sistema} from '../../../../sistema/Sistema.js';

import AnoLetivoDetalhesComponent from './AnoLetivoDetalhesComponent.js';

export default class AnoLetivoDetalhesService {			
		
	constructor() {
		this.component = new AnoLetivoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			anoLetivoId : this.params.anoLetivoId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraFormEditar() {				
		sistema.carregaPagina( 'anoletivo-form', { anoLetivoId : this.params.anoLetivoId, op : 'editar', titulo : "Edição de anoletivo" } );
	}
			
	paraAnoLetivoTela() {
		sistema.carregaPagina( 'anoletivo-tela' );
	}
	
}
export const anoletivoDetalhes = new AnoLetivoDetalhesService(); 