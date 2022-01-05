import {sistema} from "../../../../sistema/Sistema.js";

import PeriodoDetalhesComponent from './PeriodoDetalhesComponent.js';

export default class PeriodoDetalhesService {

	constructor() {
		this.component = new PeriodoDetalhesComponent();
	}

	onCarregado() {		
		this.component.configura( {} );
		this.component.carregaHTML();			
	}
						
	onCarregado() {
		this.component.configura( {
			periodoId : this.params.periodoId,
		} );			
		this.component.carregaHTML();			
	}
						
	paraEdicaoForm() {
		sistema.carregaPagina( 'periodo-form', { op : 'editar', periodoId : this.params.periodoId, titulo : 'Edição de período' } );
	}
	
	paraTela() {
		sistema.carregaPagina( 'periodo-tela' );
	}		

}
export const periodoDetalhes = new PeriodoDetalhesService();