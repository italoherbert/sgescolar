
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class RecursoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem-el' );		
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		
		sistema.ajax( "GET", "/api/recurso/get/"+this.globalParams.recursoId, {		
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
		super.setHTMLCampoValor( 'nome', 'Nome do recurso:', dados.nome );
	}
	
}