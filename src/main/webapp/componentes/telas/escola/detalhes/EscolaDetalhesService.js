
import {sistema} from '../../../../sistema/Sistema.js';

import EscolaDetalhesComponent from './EscolaDetalhesComponent.js';

export default class EscolaDetalhesService {			
		
	constructor() {
		this.component = new EscolaDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			escolaId : this.params.escolaId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'escola-form', { escolaId : this.params.escolaId, op : 'editar', titulo : "Edição de escola" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'escola-tela' );
	}
	
}
export const escolaDetalhes = new EscolaDetalhesService(); 