
function ajax( metodo, url, params ) {	
	if ( metodo === undefined || metodo === null )
		throw "O método é um parâmetro obrigatório. Metodo="+metodo;
	if ( url === undefined || url === null )
		throw "A url é um parâmetro obrigatório. URL="+url;
		
	var xmlhttp = novoXMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if ( xmlhttp.readyState == 4 ) {
			if ( params !== undefined && params !== null )
				if ( typeof( params.respostaChegou ) == 'function' )
					params.respostaChegou.call( this, xmlhttp );			
		}
	}
	
	xmlhttp.open( metodo, url, true );
	
	if ( params.cabecalhos !== undefined && params.cabecalhos !== null ) {		
		Object.keys( params.cabecalhos ).forEach( (key) => {
			xmlhttp.setRequestHeader( key, params.cabecalhos[ key ] );
		} );
	}
	
	let corpo = null;
	if ( params.corpo !== undefined && params.corpo !== null )		
		corpo = params.corpo;	
		
	xmlhttp.send( corpo );		
}

function ajaxCarregaHTML( id, url, params ) {		
	let xmlhttp = novoXMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if ( xmlhttp.readyState == 4 ) {
			if ( xmlhttp.status == 200 ) {
				let html = xmlhttp.responseText;								
				let el = document.getElementById( id );
				if ( el === undefined || el === null )
					throw "ID de elemento HTML, não encontrado. ID="+id;
				
				if ( params !== undefined && params !== null ) {
					if ( params.vars !== undefined && params.vars != null ) {
						Object.keys( params.vars ).forEach( (key) => {
							html = html.replaceAll( '#{'+key+'}', params.vars[ key ] );
						} );
					}
					
					el.innerHTML = html;
					
					if ( typeof( params.sucesso ) == "function" )
						params.sucesso.call( this, xmlhttp, html );
				} else {
					el.innerHTML = html;
				}
			} else {
				if ( params !== undefined && params !== null ) {
					if ( typeof( params.erro ) == "function" ) {
						params.erro.call( this, xmlhttp );
					} else {
						let el = document.getElementById( id );					
						if ( el === undefined || el === null )
							throw "ID de elemento HTML, não encontrado. ID="+id;
							
						el.innerHTML = "<span style=\"color:red\">Não carregado</span>";
					}
				}
			}			
		}
	}
	xmlhttp.open( "GET", url, true );
	xmlhttp.send( null );
}

function ajaxGetRecurso( url, params ) {
	let xmlhttp = novoXMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if ( xmlhttp.readyState == 4 ) {
			if ( xmlhttp.status == 200 ) {
				if ( params !== undefined && params !== null )
					if ( typeof( params.sucesso ) == "function" )
						params.sucesso.call( this, xmlhttp );
			} else {
				if ( params !== undefined && params !== null ) {
					if ( typeof( params.erro ) == "function" )
						params.erro.call( this, xmlhttp );					
				}
			}			
		}
	}
	xmlhttp.open( "GET", url, true );
	xmlhttp.send( null );
}

function novoXMLHttpRequest() {
	if (window.ActiveXObject) {
		var versoes = ["Microsoft.XMLHttp", "MSXML2.XMLHttp",
						"MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp.4.0",
						"MSXML2.XMLHttp.5.0", "MSXML2.XMLHttp.6.0"];
		for (var i=0; i<versoes.length; i++) {
			try {
				var xmlHttp = new ActiveXObject(versoes[i]);
				return xmlHttp;
			} catch (ex) {}
		}
	} else if (window.XMLHttpRequest) {			
		return new XMLHttpRequest();
	} else {
		return null;
	}
}