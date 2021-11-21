
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class AnoLetivoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );		
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/anoletivo/get/"+this.globalParams.anoLetivoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );	
				instance.carrega( dados );																
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );		
	}
	
	carrega( dados ) {			
		super.setHTMLCampoValor( 'ano', 'Ano:', dados.ano );		
	}
	
}