
class PessoaForm {
		
	onCarregado() {						
		if ( this.params.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/pessoa/get/"+this.params.pessoaId, {		
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					
					document.pessoa_form.nome.value = dados.nome;
					document.pessoa_form.telefone.value = dados.telefone;
					document.pessoa_form.email.value = dados.email;		
					
					document.getElementById( "remover-bt" ).disabled = false;			
				},
				erro : function( msg ) {
					sistema.mostraMensagemErro( "mensagem-el", msg );	
				}
			} );
		} else {
			document.getElementById( "remover-bt" ).disabled = true;			
		}
	}

	salvar() {		
		var instance = this;

		sistema.limpaMensagem( "mensagem-el" );

		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/pessoa/atualiza/"+this.params.pessoaId;
		} else {
			metodo = "POST";
			url = "/api/pessoa/registra";
		}
						
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				"nome" : document.pessoa_form.nome.value,
				"telefone" : document.pessoa_form.telefone.value,
				"email" : document.pessoa_form.email.value
			} ),
			sucesso : function( resposta ) {							
				sistema.mostraMensagemInfo( "mensagem-el", 'Pessoa salva com êxito.' );
				
				document.getElementById( "remover-bt" ).disabled = true;									
				instance.limpaForm();
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );
	}
	
	removerConfirm() {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de pessoa",
			msg :  "Digite abaixo o nome <span class='text-danger'>remova</span> para confirmar a remoção",			
			confirm : {
				texto : 'remova',
				bt : {
					rotulo : "Remover",
					onclick : {
						func : this.remover,
						thisref : this,
						params : null
					}
				}
			}			
		} );
	}

	remover() {
		let instance = this;
				
		sistema.limpaMensagem( "mensagem-el" );
		
		sistema.ajax( "DELETE", "/api/pessoa/deleta/"+this.params.pessoaId, {
			sucesso : function( resposta ) {
				instance.limpaForm();				

				document.getElementById( "remover-bt" ).disabled = true;			
						
				sistema.mostraMensagemInfo( "mensagem-el", 'Pessoa deletada com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", 'erro', msg );	
			}
		} );		
	}

	limpaForm() {
		document.pessoa_form.nome.value = "";
		document.pessoa_form.telefone.value = "";
		document.pessoa_form.email.value = "";
	}
	
}