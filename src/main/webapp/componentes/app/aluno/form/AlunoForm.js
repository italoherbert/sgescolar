
class AlunoForm {
								
	onCarregado() {
		const instance = this;	
		sistema.carregaComponente( 'pessoa-form', 'aluno-pessoa-form-el', {
			titulo : 'Dados do aluno',
			prefixo : 'aluno_',
			sucessoCarregamento : ( html, xmlhttp ) => {
				sistema.carregaComponente( 'endereco-form', 'aluno_endereco_form_el', {
					titulo : "Dados do endereço do aluno",
					prefixo : "aluno_",										
				} );
				sistema.carregaComponente( 'contatoinfo-form', 'aluno_contatoinfo_form_el', {
					titulo : "Dados de contato do aluno",
					prefixo : "aluno_"
				} );
				instance.inicializa();
			}
		} );						
		
		sistema.carregaComponente( 'pessoa-form-modal', 'pai-form-modal-el', {
			titulo : "Pai do aluno",
			prefixo : "pai_",
			fechar_modal_onclick : "onclick=\"alunoForm.mostraEscondePaiModal()\"",			
			sucessoCarregamento : ( html, xmlhttp ) => {
				sistema.carregaComponente( 'pessoa-form', 'pai_pessoa_form_el', {
					titulo : "Dados da pessoa pai",
					prefixo : "pai_",
					sucessoCarregamento : (html, xmlhttp) => {
						sistema.carregaComponente( 'endereco-form', 'pai_endereco_form_el', {
							titulo : "Dados do endereço do pai",
							prefixo : "pai_",										
						} );
						sistema.carregaComponente( 'contatoinfo-form', 'pai_contatoinfo_form_el', {
							titulo : "Dados de contato do pai",
							prefixo : "pai_"
						} );
					}
				} );
			}
		} );
		
		sistema.carregaComponente( 'pessoa-form-modal', 'mae-form-modal-el', {
			titulo : "Mãe do aluno",
			prefixo : "mae_",
			fechar_modal_onclick : "onclick=\"alunoForm.mostraEscondeMaeModal()\"",			
			sucessoCarregamento : ( html, xmlhttp ) => {
				sistema.carregaComponente( 'pessoa-form', 'mae_pessoa_form_el', {
					titulo : "Dados da pessoa mãe",
					prefixo : "mae_",
					sucessoCarregamento : (html, xmlhttp) => {
						sistema.carregaComponente( 'endereco-form', 'mae_endereco_form_el', {
							titulo : "Dados do endereço da mãe",
							prefixo : "mae_",										
						} );
						sistema.carregaComponente( 'contatoinfo-form', 'mae_contatoinfo_form_el', {
							titulo : "Dados de contato da mãe",
							prefixo : "mae_"
						} );
					}
				} );
			}
		} );
	}
	
	inicializa() {
		const instance = this;
		sistema.ajax( "GET", "/api/tipos/todos", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				instance.carregaSelects( "aluno_", dados );
				instance.carregaSelects( "pai_", dados );
				instance.carregaSelects( "mae_", dados );					
				
				if ( instance.params.op === 'editar' ) {			
					sistema.ajax( "GET", "/api/aluno/get/"+instance.params.alunoId, {		
						sucesso : function( resposta ) {
							let dados = JSON.parse( resposta );
									
						},
						erro : function( msg ) {
							sistema.mostraMensagemErro( "mensagem-el", msg );	
						}
					} );
				} 
			}
		} );				
	}
	
	carregaSelects( prefixo, dados ) {
		document.getElementById( prefixo+"sexo_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.sexos, "Selecione o sexo" ); 
		document.getElementById( prefixo+"estado_civil_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.estadosCivis, "Selecione o estado civil" );
		document.getElementById( prefixo+"nacionalidade_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.nacionalidades, "Selecione a nacionalidade" );
		document.getElementById( prefixo+"raca_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.racas, "Selecione a raça" );
		document.getElementById( prefixo+"religiao_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.religioes, "Selecione a religião" );				
	}
	

	salvar() {		
		var instance = this;

		sistema.limpaMensagem( "mensagem-el" );

		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/aluno/atualiza/"+this.params.alunoId;
		} else {
			metodo = "POST";
			url = "/api/aluno/registra";
		}
		
		let formDados = {
			pessoa : {
				
				endereco : {
					
				},
				contatoinfo : {
					
				}
			}
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
	
	mostraEscondePaiModal() {
		showHide( 'pai_pessoa_form_modal' );
	}
	
	mostraEscondeMaeModal() {
		showHide( 'mae_pessoa_form_modal' );
	}
	
}