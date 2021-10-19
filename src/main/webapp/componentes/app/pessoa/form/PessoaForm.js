
class PessoaForm {
		
	onCarregado() {						
		if ( this.props.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/pessoa/get/"+this.props.pessoaId, {		
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					
					document.pessoa_form.nome.value = dados.nome;
					document.pessoa_form.telefone.value = dados.telefone;
					document.pessoa_form.email.value = dados.email;		
					
					document.getElementById( "remover-bt" ).disabled = false;			
				},
				erro : function( msg ) {
					sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
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
		
		if ( this.props.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/pessoa/atualiza/"+this.props.pessoaId;
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
				sistema.mostraMensagem( "mensagem-el", 'info', 'Pessoa salva com êxito.' );
				
				document.getElementById( "remover-bt" ).disabled = true;									
				instance.limpaForm();
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
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
		
		sistema.ajax( "DELETE", "/api/pessoa/deleta/"+this.props.pessoaId, {
			sucesso : function( resposta ) {
				instance.limpaForm();				

				document.getElementById( "remover-bt" ).disabled = true;			
						
				sistema.mostraMensagem( "mensagem-el", 'info', 'Pessoa deletada com êxito.' );
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