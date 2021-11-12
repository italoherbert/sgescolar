
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';
import UsuarioDetalhesComponent from '../../../component/usuario/detalhes/UsuarioDetalhesComponent.js';

export default class UsuarioDetalhesComponent2 extends RootComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.usuarioDetalhesComponent = new UsuarioDetalhesComponent( '', 'usuario_el' );
		
		super.addFilho( this.usuarioDetalhesComponent );
	}
		
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/usuario/get/"+this.globalParams.usuarioId, {		
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
		this.usuarioDetalhesComponent.carrega( dados );				
	}
	
}