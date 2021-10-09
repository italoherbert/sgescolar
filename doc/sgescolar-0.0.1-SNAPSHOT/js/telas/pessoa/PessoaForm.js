
class PessoaForm {
	
	params = {};
	
	onCarregado( jsObj, params ) {	
		this.params = params;
				
		let id = ( params ? params.id : undefined );		
		if ( id ) {			
			ajax2( "GET", "/pessoa/get/"+id, {		
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					
					document.pessoa_form.nome.value = dados.nome;
					document.pessoa_form.telefone.value = dados.telefone;
					document.pessoa_form.email.value = dados.email;				
				},
				erro : function( msg ) {
					sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
				}
			} );
		}
	}

	salvar() {		
		var instance = this;

		sistema.limpaMensagem( "mensagem-el" );

		let url;
		let metodo;
		
		let id = ( this.params ? this.params.id : undefined );		
		if ( id ) {
			metodo = "PUT";
			url = "/pessoa/atualiza/"+id;
		} else {
			metodo = "POST";
			url = "/pessoa/registra";
		}
						
		ajax2( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				"nome" : document.pessoa_form.nome.value,
				"telefone" : document.pessoa_form.telefone.value,
				"email" : document.pessoa_form.email.value
			} ),
			sucesso : function( resposta ) {
				sistema.mostraMensagem( "mensagem-el", 'info', 'Pessoa salva com êxito.' );					
				instance.limpaForm();
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
			}
		} );
	}

	limpaForm() {
		document.pessoa_form.nome.value = "";
		document.pessoa_form.telefone.value = "";
		document.pessoa_form.email.value = "";
	}
	
}