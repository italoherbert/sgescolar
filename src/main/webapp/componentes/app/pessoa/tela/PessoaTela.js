
class PessoaTela {

	onCarregado() {						
		this.filtrar();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'pessoa-detalhes', { pessoaId : id } );																	
	}
	
	filtrar() {			
		let nomeIni = document.pessoa_filtro_form.nomeini.value;
			
		sistema.ajax( "GET", "/api/pessoa/filtra/"+nomeIni, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					html += "<tr>" 
						+ "<td>" + dados[ i ].id + "</td>" 					
						+ "<td>" + dados[ i ].nome + "</td>" 
						+ "<td>" + dados[ i ].telefone + "</td>" 
						+ "<td>" + dados[ i ].email + "</td>"
						+ "<td><a href=\"#\" onclick=\"pessoaTela.detalhes( " + dados[ i ].id + " )\">detalhes</a></td>" 	 
						+ "</tr>";
				}
				document.getElementById( "tbody-pessoas-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );	
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'pessoa-form', { titulo : "Registro de pessoa" } )
	}

}