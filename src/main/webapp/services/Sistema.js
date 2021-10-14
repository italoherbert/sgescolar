
class Sistema {
			
	componentes = {}	
	globalVars = {
		usuarioLogado : {},
		token : null,
		logado : false
	}
	
	constructor( componentes ) {
		this.componentes = componentes;
	}
			
	carregaLayout( compID, params ) {
		this.carregaComponente( compID, "layout", params );
	}
		
	carregaPagina( compID, params ) {
		this.carregaComponente( compID, "pagina", params );
	}
	
	carregaConfirmModal( elid, params ) {
		this.carregaConfirmModalComponente( 'confirm-modal', elid, params );
	}	
			
	mostraMensagem( id, tipo, msg ) {		
		let className = ( tipo === 'info' ? 'alert-primary' : 'alert-danger' );
		document.getElementById( id ).innerHTML = "<div class='alert "+className+"'>"+msg+"</div>";
		
		this.scrollTo( id );	
	}
	
	limpaMensagem( id ) {
		document.getElementById( id ).innerHTML = "";
	}
	
	scrollTo( strEL ) {
		let el = document.querySelector( "#"+strEL );
		if ( el )
			el.scrollIntoView();			
	}
		
	showHide( elid ) {
		let el = document.getElementById( elid );
		if ( el === undefined || el === null )
			throw "Elemento HTML não encontrado pelo ID. ID="+elid;
		
		el.classList.toggle( "d-block" );
		el.classList.toggle( "d-none" );
		el.classList.toggle( "visible" );
		el.classList.toggle( "hidden" );
	}
	
	formataData( date ) {
		if ( date === null || date === undefined || date === '' )
			return "Formato de data não reconhecido";
		
		return moment( date ).format( "DD/MM/YYYY" );
	}
	
	formataReal( valor ) {
		let v = parseFloat( parseFloat( valor ).toFixed( 2 ) );
		if ( v === 0 ) 
			return "R$ 0,00";
				
		if ( Math.abs( v ) >= 1.0 ) {
			let n = parseInt( v * 100 );
			let s = ""+n;
			return "R$ "+s.substring( 0, s.length-2 ) + ',' + s.substring( s.length-2, s.length );		
		} else {
			return "R$ " +( ( ""+v ).replace( '.', ',' ) );			
		}				
	}
	
	valorFloat( valor ) {
		if ( isNaN( (""+valor).replace( ',', '.' ) ) === true )
			return valor;
		
		if ( (""+valor) === "0" ) 
			return 0;
		
		return parseFloat( parseFloat( (""+valor).replace( ',', '.' ) ).toFixed( 2 ) );
	}
	
	formataFloat( valor ) {
		if ( (""+valor) === "0" ) 
			return "0";
		
		let v = (""+valor).split( '.' );
		
		let frac = 0;
		if ( v.length === 2 )
			frac = parseInt( v[1] );
		
		return ( frac === 0 ? v[0] : v[0]+','+v[1] );				
	}
	
	carregaComponente( compID, elid, params ) {
		this.carregaComponente2( compID, elid, null, params );
	}
	
	carregaComponente2( compID, elid, vars, params ) {
		let comp = this.componentes[ compID ];
		let pagina = comp.pagina;
		let jsObj = comp.jsObj;
																	
		ajaxCarregaHTML( elid, pagina, {
			vars : vars, 
			sucesso : function() {
				if ( jsObj !== undefined && jsObj !== null )
					if ( typeof( jsObj.onCarregado ) === "function" )
						jsObj.onCarregado.call( this, jsObj, params );
			}
		} )
	}
	
	carregaConfirmModalComponente( compID, elid, params ) {		
		let comp = this.componentes[ compID ];
		let pagina = comp.pagina;
		
		let modalID = 'id-'+new Date().getTime();
		let execBTID = 'bt-'+new Date().getTime()+1;
		let cancelarBTID = 'bt-'+new Date().getTime()+2;
		let fecharBTID = 'bt-'+new Date().getTime()+3;
		let msgID = 'msg-'+new Date().getTime();
				
		const instance = this;		
				
		ajaxCarregaHTML( elid, pagina, {
			vars : {
				'modal-id' : modalID,
				'titulo' : params[ 'titulo' ],
				'corpo-msg' : params[ 'corpoMsg' ],
				'exec-bt-id' : execBTID,
				'cancelar-bt-id' : cancelarBTID,
				'fechar-bt-id' : fecharBTID,
				'exec-bt-rotulo' : params[ 'execBTRotulo' ],
				'confirm-texto' : params[ 'confirmTexto' ],
				'msg-id' : msgID,				
			},
			sucesso : (xmlhttp, html) => {				
				instance.showHide( modalID );
								
				let bt = document.body.querySelector( "#"+execBTID );
				bt.addEventListener( "click", (event) => {
					let confirmTexto = document.getElementsByName( params[ 'confirmTexto' ] )[0].value;
					if ( confirmTexto === params[ 'confirmTexto' ] ) { 
						instance.showHide( modalID );
						if ( typeof( params.execFunc ) == "function" )
							params.execFunc.call( this, params.params );						
					} else {
						instance.mostraMensagem( msgID, 'erro', 'Você não informou o texto de confirmação acima.' );
					}
				} );
				
				let fecharFunc = ( event ) => {
					instance.showHide( modalID );
				};
				
				
				document.body.querySelector( "#"+cancelarBTID ).addEventListener( "click", fecharFunc );
				document.body.querySelector( "#"+fecharBTID ).addEventListener( "click", fecharFunc );				
			},
			erro : (xmlhttp) => {
				if ( typeof( params.erro ) == "function" )
					params.erro.call( this, xmlhttp );				
			}
		} )		
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
			instance.showHide( 'carregando' );
		}
		
		this.showHide( 'carregando' );
		
		ajax( metodo, url, params );	
	}
		
}

