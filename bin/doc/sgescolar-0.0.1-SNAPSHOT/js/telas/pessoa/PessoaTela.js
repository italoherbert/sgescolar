
class PessoaTela {

	onCarregado( jsObj ) {
		jsObj.filtrar();
	}

	editar( id ) {	
		sistema.carregaPagina( 'pessoa-form', { id : id } );		
	}

	remover( id ) {
		let instance = this;
		
		sistema.limpaMensagem( "mensagem-el" );
		
		ajax2( "DELETE", "/pessoa/deleta/"+id, {
			sucesso : function( resposta ) {
				sistema.mostraMensagem( "mensagem-el", 'info', 'Pessoa deletada com êxito.' );
				instance.filtrar();				
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
			}
		} );
	}

	filtrar() {			
		let nomeIni = document.pessoa_filtro_form.nomeini.value;
			
		ajax2( "GET", "/pessoa/filtra/"+nomeIni, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					html += "<tr>" 
						+ "<td>" + dados[ i ].id + "</td>" 					
						+ "<td>" + dados[ i ].nome + "</td>" 
						+ "<td>" + dados[ i ].telefone + "</td>" 
						+ "<td>" + dados[ i ].email + "</td>"
						+ "<td><a href=\"#\" onclick=\"pessoaTela.editar( " + dados[ i ].id + " )\">editar</a></td>" 	 
						+ "<td><a href=\"#\" onclick=\"pessoaTela.remover( " + dados[ i ].id + " )\">remover</a></td>" 	  	
						+ "</tr>";
				}
				document.getElementById( "tbody-pessoas-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
			}
		} );	
	}

}