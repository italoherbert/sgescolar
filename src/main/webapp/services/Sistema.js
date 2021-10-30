
class Sistema {
			
	globalVars = {
		usuarioLogado : {},
		token : null,
		logado : false
	}
	
	confirmModalManager = new ConfirmModalManager( 'lib/ext/confirm-modal/confirm-modal.html' );
	mensagemManager = new MensagemManager();
	componenteManager = null;
	
	constructor( componentes ) {
		this.componenteManager = new ComponenteManager( componentes );
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
		
		const instance = this;
					
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
			showHide( 'carregando' );
		}
		
		showHide( 'carregando' );
		
		ajax( metodo, url, params );	
	}
		
}

