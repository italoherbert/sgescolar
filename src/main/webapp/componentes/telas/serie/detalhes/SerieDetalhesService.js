
import {sistema} from '../../../../sistema/Sistema.js';

import SerieDetalhesComponent from './SerieDetalhesComponent.js';

export default class SerieDetalhesService {			
		
	constructor() {
		this.component = new SerieDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			serieId : this.params.serieId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraFormEditar() {				
		sistema.carregaPagina( 'serie-form', { serieId : this.params.serieId, op : 'editar', titulo : "Edição de serie" } );
	}
			
	paraSeriesTela() {
		sistema.carregaPagina( 'serie-tela' );
	}
	
}
export const serieDetalhes = new SerieDetalhesService(); 