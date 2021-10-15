
class Sistema {
			
	componentes = {}	
	icones = []
	globalVars = {
		usuarioLogado : {},
		token : null,
		logado : false
	}
	
	constructor( componentes, icones ) {
		this.componentes = componentes;
		this.icones = icones;
	}
			
	carregaLayout( compID, params ) {
		this.carregaComponente( compID, "layout", params );
	}
		
	carregaPagina( compID, params ) {
		this.carregaComponente( compID, "pagina", params );
	}
		
	carregaComponente( compID, elid, params ) {
		let comp = this.componentes[ compID ];
		let pagina = comp.pagina;
		let jsObj = comp.jsObj;
																	
		ajaxCarregaHTML( elid, pagina, {
			sucesso : function() {
				if ( jsObj !== undefined && jsObj !== null )
					if ( typeof( jsObj.onCarregado ) === "function" )
						jsObj.onCarregado.call( this, jsObj, params );
			}
		} )
	}	
	
	getIconeSrc( nome ) {
		for( let i = 0; i < this.icones.length; i++ )
			if ( this.icones[ i ].nome === nome )
				return this.icones[ i ].src;					
		return null;
	}
	
	mostraMensagem( id, tipo, msg ) {
		let className = ( tipo === 'info' ? 'alert-primary' : 'alert-danger' );
		document.getElementById( id ).innerHTML = "<div class='alert "+className+"'>"+msg+"</div>";	
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
		}
		
		ajax( metodo, url, params );	
	}
		
}

