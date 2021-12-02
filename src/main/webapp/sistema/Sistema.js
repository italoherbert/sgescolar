
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
	
	verificaSeTemPermissao( permissoes ) {
		if ( this.globalVars.permissoes === undefined || this.globalVars.permissoes === null )
			return false;
					
		if ( permissoes === undefined || permissoes === null )
			return false;			
					
		for( let i = 0; i < permissoes.length; i++ ) {				
			let achou = false;

			let len = this.globalVars.permissoes.length;
			for( let j = 0; achou === false && j < len; j++ )
				if ( permissoes[ i ] === this.globalVars.permissoes[ j ] )
					achou = true;
						
			if ( achou === false )
				return false;
		}			
		
		return true;
	}
		
}
export const sistema = new Sistema();

