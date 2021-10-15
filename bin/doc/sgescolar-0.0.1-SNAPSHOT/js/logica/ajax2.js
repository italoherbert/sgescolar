
function ajax2( metodo, url, params ) {		
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
