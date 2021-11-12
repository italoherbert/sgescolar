
import {sistema} from '../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';

import RootFormComponent from '../../component/RootFormComponent.js';

import UsuarioFormComponent from '../../component/usuario/form/UsuarioFormComponent.js';

export default class UsuarioTelaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'form-mensagem-el' );
		
		this.usuarioFormComponent = new UsuarioFormComponent( formNome, '', 'form-el' );		
		this.usuarioFormComponent.end.comp = 'usuario-form-end';
		this.usuarioFormComponent.carregaPerfis = ( sel_elid ) => this.carregaUsuarioPerfis( sel_elid );
				
		super.addFilho( this.usuarioFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();																		
	}
		
	carregaUsuarioPerfis( select_elid ) {		
		sistema.ajax( "GET", "/api/tipos/perfis/admin", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				super.getEL( select_elid ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : dados, 
					defaultOption : { texto : 'Selecione o perfil', valor : '0' }
				} );				
			}
		} );	
	}
			
	getJSON() {
		return this.usuarioFormComponent.getJSON();
	}	
		
	carregaJSON( dados ) {
		this.usuarioFormComponent.carregaJSON( dados );		
	}	
								
}
