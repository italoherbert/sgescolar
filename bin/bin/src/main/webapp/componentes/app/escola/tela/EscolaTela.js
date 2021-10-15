
class EscolaTela {
	
	detalhes( id ) {
		alert( id );
	}
	
	remover( id ) {
		
	}
	
	filtrar() {			
		let nomeIni = document.escola_filtro_form.nomeini.value;
			
		sistema.ajax( "GET", "/api/escola/filtra/"+nomeIni, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					html += "<tr>" 
						+ "<td>" + dados[ i ].id + "</td>" 
						+ "<td>" + dados[ i ].nome + "</td>" 
						+ "<td><a href=\"#\" onclick=\"escolaTela.detalhes( " + dados[ i ].id + " )\">detalhes</a></td>" 	 
						+ "<td><a href=\"#\" onclick=\"escolaTela.remover( " + dados[ i ].id + " )\">remover</a></td>" 	  	
						+ "</tr>";
				}
				document.getElementById( "tbody-escolas-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
			}
		} );	
	}
	
}