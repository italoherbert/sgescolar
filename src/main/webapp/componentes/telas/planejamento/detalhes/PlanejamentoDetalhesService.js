
import {sistema} from '../../../../sistema/Sistema.js';

import PlanejamentoDetalhesComponent from './PlanejamentoDetalhesComponent.js';

export default class PlanejamentoDetalhesService {			
		
	constructor() {
		this.component = new PlanejamentoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			planejamentoId : this.params.planejamentoId,
			op : this.params.op
		} );			
		this.component.carregaHTML();			
	}
				
	paraEdicaoForm() {
		sistema.carregaPagina( 'planejamento-form', { op : 'editar', planejamentoId : this.params.planejamentoId, titulo : 'Edição de planejamento' } );
	}
	
	paraTela() {
		sistema.carregaPagina( 'planejamento-tela' );
	}
	
}
export const planejamentoDetalhes = new PlanejamentoDetalhesService(); 