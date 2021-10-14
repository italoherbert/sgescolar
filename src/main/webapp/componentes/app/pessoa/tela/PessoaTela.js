
class PessoaTela {

	onCarregado( jsObj, params ) {						
		jsObj.filtrar();
	}

	editar( id ) {	
		sistema.carregaPagina( 'pessoa-form', { id : id } );		
	}

	removerConfirm( id ) {
		sistema.carregaConfirmModal( 'modal-el', {
			titulo : "Remoção de pessoa",
			corpoMsg : "Digite abaixo o nome <span class='text-danger'>remova</span> para confirmar a remoção",
			execBTRotulo : "Remover",
			confirmTexto : "remova",
			execFunc : this.remover,
			params : { objRef : this, id : id }
		} );
	}

	remover( params ) {
		let objRef = params.objRef;
		let id = params.id;
		
		sistema.limpaMensagem( "mensagem-el" );
		
		sistema.ajax( "DELETE", "/api/pessoa/deleta/"+id, {
			sucesso : function( resposta ) {
				sistema.mostraMensagem( "mensagem-el", 'info', 'Pessoa deletada com êxito.' );
				objRef.filtrar();				
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
			}
		} );
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
						+ "<td><a href=\"#\" onclick=\"pessoaTela.editar( " + dados[ i ].id + " )\">editar</a></td>" 	 
						+ "<td><a href=\"#\" onclick=\"pessoaTela.removerConfirm( " + dados[ i ].id + " )\">remover</a></td>" 	  	
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