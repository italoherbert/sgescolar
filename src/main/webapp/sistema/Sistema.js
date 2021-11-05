
import * as ajax from './util/ajax.js';
import * as elutil from './util/elutil.js';
import ConfirmModalManager from './util/confirm-modal/ConfirmModalManager.js';
import ComponenteManager from './nucleo/ComponenteManager.js';
import MensagemManager from './nucleo/MensagemManager.js';

export default class Sistema {
			
	globalVars = {
		usuarioLogado : {},
		token : null,
		logado : false
	}
	
	confirmModalManager = new ConfirmModalManager( 'sistema/util/confirm-modal/confirm-modal.html' );
	mensagemManager = new MensagemManager();
	componenteManager = null;
	
	constructor() {
		this.componenteManager = new ComponenteManager();
	}
	
	inicializa( componentes ) {
		this.componenteManager.inicializa( componentes );
	}
	
	selectOptionsHTML( valores, defaultOpTexto ) {
		let html = "";
		
		if ( defaultOpTexto !== undefined && defaultOpTexto !== null )
			html += "<option value=\"NULO\">"+defaultOpTexto+"</option>";
			
		for( let i = 0; i < valores.length; i++ )
			html += "<option value=\"" + valores[i] + "\">" + valores[i] + "</option>";
		
		return html;			
	}
	
	carregaConfirmModal( elid, params ) {
		this.confirmModalManager.carregaModal( elid, params );
	}
	
	carregaLayout( compID, params ) {
		this.carregaComponente( compID, "layout", params );
	}
		
	carregaPagina( compID, params ) {
		this.carregaComponente( compID, "pagina", params );
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
		if ( this.globalVars[ 'logado' ] === true ) {	 
			if ( params.cabecalhos === undefined || params.cabecalhos === null )
				params.cabecalhos = {};
			params.cabecalhos['Authorization'] = "Bearer "+sistema.globalVars.token;
		}
							
		params.respostaChegou = function( xmlhttp ) {
			switch ( xmlhttp.status ) {
				case 200:
					if ( typeof( params.sucesso ) == 'function' )
						params.sucesso.call( this, xmlhttp.responseText );
					break; 			
				case 400:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, JSON.parse( xmlhttp.responseText ).mensagem );
					break;
				case 401:
				case 403:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, "Você não tem permissões suficientes para acessar o recurso requisitado.");
					break;
				case 404:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, "Recurso não encontrado." );
					break;
				case 500:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, "Erro interno no servidor." );
					break;
			}
			elutil.showHide( 'carregando' );
		}
		
		elutil.showHide( 'carregando' );
		
		ajax.ajax( metodo, url, params );	
	}
		
}
export const sistema = new Sistema();

