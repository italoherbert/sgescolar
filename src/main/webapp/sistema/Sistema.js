
import * as ajax from './util/ajax.js';
import * as elutil from './util/elutil.js';

import ConfirmModalManager from './util/confirm-modal/ConfirmModalManager.js';

import ComponenteManager from './nucleo/ComponenteManager.js';
import MensagemManager from './nucleo/MensagemManager.js';

export default class Sistema {
			
	globalVars = {
		usuario : null,
		permissoes : null,
		perfil : null,
		token : null,
		logado : false,		
	}
	
	ultimaPaginaCompID = null;
	ultimaPaginaParams = null;
	
	confirmModalManager = new ConfirmModalManager( 'sistema/util/confirm-modal/confirm-modal.html' );
	mensagemManager = new MensagemManager();
	componenteManager = null;
	
	constructor() {
		this.componenteManager = new ComponenteManager();
	}
	
	inicializa( componentes ) {
		this.componenteManager.inicializa( componentes );
	}
						
	carregaConfirmModal( elid, params ) {
		this.confirmModalManager.carregaModal( elid, params );
	}
	
	carregaLayout( compID, params ) {
		this.carregaComponente( compID, "layout", params );
	}
		
	recarregaPaginaCorrente() {
		this.carregaPagina( this.ultimaPaginaCompID, this.ultimaPaginaParams );
	}
		
	carregaPagina( compID, params ) {
		this.carregaComponente( compID, "pagina", params );
		window.scrollTo( 0, 0 )
		
		this.ultimaPaginaCompID = compID;
		this.ultimaPaginaParams = params;
	}
	
	carregaComponente( compID, elid, params ) {
		this.componenteManager.carregaComponente( compID, elid, params );
	}
	
	mostraMensagemInfo( id, msg ) {		
		this.mensagemManager.mostraMensagem( id, 'info', msg );
	}
	
	mostraMensagemErro( id, msg ) {		
		this.mensagemManager.mostraMensagem( id, 'erro', msg );
	}
	
	mostraMensagemAlerta( id, msg ) {		
		this.mensagemManager.mostraMensagem( id, 'alerta', msg );
	}
	
	mostraMensagem( id, msg ) {		
		this.mensagemManager.mostraMensagem( id, '', msg );
	}
		
	limpaMensagem( id ) {
		document.getElementById( id ).innerHTML = "";
	}
				
	ajax( metodo, url, params ) {	
		let mensagem_el = document.getElementById( 'layout-mensagem-el' );
		if ( mensagem_el !== undefined && mensagem_el !== null )
			mensagem_el.innerHTML = "";
		
		if ( this.globalVars[ 'logado' ] === true ) {	 
			if ( params.cabecalhos === undefined || params.cabecalhos === null )
				params.cabecalhos = {};
			params.cabecalhos['Authorization'] = "Bearer "+sistema.globalVars.token;
		}
		
		const instance = this;					
		params.respostaChegou = function( xmlhttp ) {			
			switch ( xmlhttp.status ) {
				case 200:
					if ( typeof( params.sucesso ) == 'function' )						
						params.sucesso.call( this, xmlhttp.responseText );					
					break; 			
				default:
					let erroMsg = instance.erroMensagem( xmlhttp );
					if ( erroMsg !== null ) {
						//erroMsg += "<br />URL do endpoint: "+url;
						if ( typeof( params.erro ) == 'function' ) {
							params.erro.call( this, erroMsg );
						} else {
							sistema.mostraMensagemErro( 'layout-mensagem-el', erroMsg );
						}
					}
			}
			elutil.showHide( 'carregando' );
		}
				
		elutil.showHide( 'carregando' );
		
		ajax.ajax( metodo, url, params );	
	}
	
	erroMensagem( xmlhttp ) {
		switch( xmlhttp.status ) {
			case 400:
				return JSON.parse( xmlhttp.responseText ).mensagem;									
			case 401:
			case 403:
				return "Você não tem permissões suficientes para acessar o recurso requisitado.";				
			case 404:
				return "Recurso não encontrado.";
			case 405:
				return "O recurso foi encontrado, mas, não para o método http informado.";
			case 500:
				return "Erro interno no servidor.";
		}
		return null;
	}	
		
}
export const sistema = new Sistema();

