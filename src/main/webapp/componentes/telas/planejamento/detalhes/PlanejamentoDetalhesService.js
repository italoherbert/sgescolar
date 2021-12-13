
import {sistema} from '../../../../sistema/Sistema.js';

import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import PlanejamentoDetalhesComponent from './PlanejamentoDetalhesComponent.js';

export default class PlanejamentoDetalhesService {			
		
	constructor() {
		this.component = new PlanejamentoDetalhesComponent();
		this.component.clickAnexoHTMLLink = this.clickAnexoHTMLLink;
	}	
		
	onCarregado() {
		this.component.configura( {
			planejamentoId : this.params.planejamentoId,
			op : this.params.op
		} );			
		this.component.carregaHTML();			
	}
	
	clickAnexoHTMLLink( label, id ) {
		return htmlBuilder.novoLinkHTML( label, 'planejamentoDetalhes.downloadAnexo('+id+')', 'fas fa-download', 'link-primary' );
	}
	
	downloadAnexo( id ) {
		this.component.downloadAnexo( id );
	}
				
	paraEdicaoForm() {
		sistema.carregaPagina( 'planejamento-form', { op : 'editar', planejamentoId : this.params.planejamentoId, titulo : 'Edição de planejamento' } );
	}
	
	paraTela() {
		sistema.carregaPagina( 'planejamento-tela' );
	}
	
}
export const planejamentoDetalhes = new PlanejamentoDetalhesService(); 