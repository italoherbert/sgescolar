
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';

export default class RecursoDetalhesComponent extends RootComponent {
	
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
		sistema.carregaComponente( 'campo', super.getELID( 'nome' ), { rotulo : "Nome do recurso:", valor : dados.nome } );		
	}
	
}