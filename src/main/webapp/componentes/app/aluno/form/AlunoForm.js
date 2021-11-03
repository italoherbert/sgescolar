
class AlunoForm {
				
	ajaxPessoaFormCarregandoCont = 0;			
								
	onCarregado() {				
		const instance = this;
		
		instance.ajaxPessoaFormCarregandoCont = 3;
			
		sistema.carregaComponente( 'pessoa-form-content', 'aluno-pessoa-form-el', {
			prefixo : 'aluno_',
			verificar_btn_atribs : "onclick=\"alunoForm.verificaAlunoCpf()\"",
			carregado : function ( html, xmlhttp ) {
				sistema.carregaComponente( 'endereco-form-content', 'aluno_endereco_form_el', {
					prefixo : "aluno_",										
				} );
				sistema.carregaComponente( 'contatoinfo-form-content', 'aluno_contatoinfo_form_el', {
					prefixo : "aluno_"
				} );
						
				instance.ajaxPessoaFormCarregandoCont--;
				instance.inicializa();
			}
		} );						
		
		sistema.carregaComponente( 'filiacao-form-content', 'aluno-filiacao-form-el', {
			prefixo : 'aluno_'			
		} );
		
		sistema.carregaComponente( 'usuario-form-content', 'aluno-usuario-form-el', {
			prefixo : 'aluno_'			
		} );
		
		sistema.carregaComponente( 'pai-ou-mae-form-modal', 'pai-form-modal-el', {
			titulo : "Formulario de pai do aluno",
			prefixo : "pai_",
			fechar_btn_atribs : "onclick=\"alunoForm.mostraEscondePaiModal()\"",			
			finalizar_btn_atribs : "onclick=\"alunoForm.validaDadosPai()\"",			
			carregado : ( html, xmlhttp ) => {
				sistema.carregaComponente( 'pai-ou-mae-form-content', 'pai_form_el', {
					titulo : "Dados extras",
					prefixo : "pai_",
					carregado : ( html, xmlhttp ) => {
						sistema.carregaComponente( 'pessoa-form-content', 'pai_pessoa_form_el', {
							titulo : "Dados da pessoa pai",
							prefixo : "pai_",
							verificar_btn_atribs : "onclick=\"alunoForm.verificaPaiCpf()\"",
							carregado : (html, xmlhttp) => {
								sistema.carregaComponente( 'endereco-form-content', 'pai_endereco_form_el', {
									titulo : "Dados do endereço do pai",
									prefixo : "pai_",										
								} );
								sistema.carregaComponente( 'contatoinfo-form-content', 'pai_contatoinfo_form_el', {
									titulo : "Dados de contato do pai",
									prefixo : "pai_"
								} );
								
								instance.ajaxPessoaFormCarregandoCont--;
								instance.inicializa();
							}
						} );	
					} 
				} );
			}
		} );
		
		sistema.carregaComponente( 'pai-ou-mae-form-modal', 'mae-form-modal-el', {
			titulo : "Formulário de mãe do aluno",
			prefixo : "mae_",
			fechar_btn_atribs : "onclick=\"alunoForm.mostraEscondeMaeModal()\"",
			finalizar_btn_atribs : "onclick=\"alunoForm.validaDadosMae()\"",			
			carregado : ( html, xmlhttp ) => {
				sistema.carregaComponente( 'pai-ou-mae-form-content', 'mae_form_el', {
					prefixo : "mae_",
					carregado : ( html, xmlhttp ) => {
						sistema.carregaComponente( 'pessoa-form-content', 'mae_pessoa_form_el', {
							prefixo : "mae_",
							verificar_btn_atribs : "onclick=\"alunoForm.verificaMaeCpf()\"",							
							carregado : (html, xmlhttp) => {
								sistema.carregaComponente( 'endereco-form-content', 'mae_endereco_form_el', {
									prefixo : "mae_",										
								} );
								sistema.carregaComponente( 'contatoinfo-form-content', 'mae_contatoinfo_form_el', {
									prefixo : "mae_"
								} );
								
								instance.ajaxPessoaFormCarregandoCont--;
								instance.inicializa();				
							}
						} );	
					} 
				} );				
			}
		} );
	}
	
	inicializa() {
		if ( this.ajaxPessoaFormCarregandoCont > 0 )
			return;
		
		const instance = this;
		sistema.ajax( "GET", "/api/tipos/todos", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				instance.carregaSelects( "aluno_", dados );
				instance.carregaSelects( "pai_", dados );
				instance.carregaSelects( "mae_", dados );					
				
				if ( instance.params.op === 'editar' )			
					instance.carregar();				
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
		
	validaDadosPai() {				
		sistema.limpaMensagem( "pai_mensagem_el" );

		const instance = this;
		sistema.ajax( "POST", "/api/paioumae/valida", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				falecido : document.aluno_form.pai_falecido.checked,
				pessoa : {
					cpf : document.aluno_form.pai_cpf.value,
					nome : document.aluno_form.pai_nome.value
				}
			} ),
			sucesso : function( resposta ) {
				document.aluno_form.aluno_resumo_pai_cpf.value = document.aluno_form.pai_cpf.value;
				document.aluno_form.aluno_resumo_pai_nome.value = document.aluno_form.pai_nome.value;
				document.aluno_form.aluno_resumo_pai_cpf.disabled = true;
				document.aluno_form.aluno_resumo_pai_nome.disabled = true;
				instance.mostraEscondePaiModal();							
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "pai_mensagem_el", msg );	
			}
		} );
	}
	
	validaDadosMae() {		
		sistema.limpaMensagem( "mae_mensagem_el" );
		
		const instance = this;
		sistema.ajax( "POST", "/api/paioumae/valida", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				falecido : document.aluno_form.mae_falecido.checked,
				pessoa : {
					cpf : document.aluno_form.mae_cpf.value,
					nome : document.aluno_form.mae_nome.value
				}
			} ),
			sucesso : function( resposta ) {
				document.aluno_form.aluno_resumo_mae_cpf.value = document.aluno_form.mae_cpf.value;
				document.aluno_form.aluno_resumo_mae_nome.value = document.aluno_form.mae_nome.value;
				document.aluno_form.aluno_resumo_mae_cpf.disabled = true;
				document.aluno_form.aluno_resumo_mae_nome.disabled = true;
				instance.mostraEscondeMaeModal();							
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mae_mensagem_el", msg );	
			}
		} );			
	}
		
	verificaAlunoCpf() {
		let cpf = document.aluno_form.aluno_cpf.value;
		if ( cpf.trim() === '' ) {
			sistema.mostraMensagemErro( "aluno_validacao_cpf_mensagem_el", "Preencha o campo CPF." );
			return;	
		}
			
		sistema.ajax( "GET", "/api/pessoa/cpf/disponivel/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				sistema.mostraMensagemInfo( "aluno_validacao_cpf_mensagem_el", "Ok! Ninguém registrado com o cpf informado." );							
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "aluno_validacao_cpf_mensagem_el", msg );	
			}
		} );
	}	
		
	verificaPaiCpf() {
		let cpf = document.aluno_form.pai_cpf.value;
		if ( cpf.trim() === '' ) {
			sistema.mostraMensagemErro( "pai_validacao_cpf_mensagem_el", "Preencha o campo CPF." );
			return;	
		}
				
		const instance = this;
		sistema.ajax( "GET", "/api/paioumae/busca/cpf/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				if ( dados.pessoaEncontrada == 'true' || dados.paiOuMaeEncontrado == 'true' ) {
					if ( dados.pessoaEncontrada == 'true' ) {
						instance.carregaPessoaPai( dados.pessoa );
					} else {
						instance.carregaPessoaPai( dados.pessoaPaiOuMae );
					}	
				} else {
					sistema.mostraMensagemInfo( "pai_validacao_cpf_mensagem_el", "Ok! Ninguém registrado com o cpf informado." );	
				}
											
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "pai_validacao_cpf_mensagem_el", msg );	
			}
		} );
	}
	
	verificaMaeCpf() {
		let cpf = document.aluno_form.mae_cpf.value;
		if ( cpf.trim() === '' ) {
			sistema.mostraMensagemErro( "mae_validacao_cpf_mensagem_el", "Preencha o campo CPF." );
			return;	
		}			
		
		const instance = this;
		sistema.ajax( "GET", "/api/paioumae/busca/cpf/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				if ( dados.pessoaEncontrada == 'true' || dados.paiOuMaeEncontrado == 'true' ) {
					if ( dados.pessoaEncontrada == 'true' ) {
						instance.carregaPessoaMae( dados.pessoa );
					} else {
						instance.carregaPessoaMae( dados.pessoaPaiOuMae );
					}	
				} else {
					sistema.mostraMensagemInfo( "mae_validacao_cpf_mensagem_el", "Ok! Ninguém registrado com o cpf informado." );	
				}
											
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mae_validacao_cpf_mensagem_el", msg );	
			}
		} );
	}
	
	carrega() {
		const instance = this;
		sistema.ajax( "GET", "/api/aluno/get/"+instance.params.alunoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaAluno( dados );		
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );
	}
	
	carregaAluno( dados ) {
		this.carregaPessoaAluno( dados.pessoa );
		this.carregaUsuarioAluno( dados.usuario );
		this.carregaPai( dados.pai );
		this.carregaMae( dados.mae );
	}
	
	carregaUsuarioAluno( dados ) {
		document.aluno_form.pai_username.value = dados.username;
		document.aluno_form.pai_password.value = dados.password;
		document.aluno_form.pai_password2.value = dados.password;
	}
	
	carregaPai( dados ) {
		document.aluno_form.pai_falecido.checked = ( dados.falecido == 'true' ? true : false );
		this.carregaPessoaPai( dados.pessoa );
	}
	
	carregaMae( dados ) {
		document.aluno_form.mae_falecido.checked = ( dados.falecido == 'true' ? true : false );
		this.carregaPessoaMae( dados.pessoa );
	}		

	carregaPessoaAluno( dados ) {
		document.aluno_form.aluno_cpf.value = dados.cpf;
		document.aluno_form.aluno_rg.value = dados.rg;
		document.aluno_form.aluno_nome.value = dados.nome;
		document.aluno_form.aluno_nome_social.value = dados.nomeSocial
		document.aluno_form.aluno_data_nascimento.value = dados.dataNascimento;
		document.aluno_form.aluno_sexo.value = dados.sexo;
		document.aluno_form.aluno_estado_civil.value = dados.estadoCivil;
		document.aluno_form.aluno_nacionalidade.value = dados.nacionalidade;
		document.aluno_form.aluno_raca.value = dados.raca;
		document.aluno_form.aluno_religiao.value = dados.religiao;
		document.aluno_form.aluno_logradouro.value = dados.endereco.logradouro;
		document.aluno_form.aluno_complemento.value = dados.endereco.complemento;
		document.aluno_form.aluno_bairro.value = dados.endereco.bairro;
		document.aluno_form.aluno_cidade.value = dados.endereco.cidade;
		document.aluno_form.aluno_uf.value = dados.endereco.uf;
		document.aluno_form.aluno_telefone_residencial.value = dados.contatoInfo.telefoneResidencial;
		document.aluno_form.aluno_telefone_celular.value = dados.contatoInfo.telefoneCelular;
		document.aluno_form.aluno_email.value = dados.contatoInfo.email;
	}

	carregaPessoaPai( dados ) {
		document.aluno_form.pai_cpf.value = dados.cpf;
		document.aluno_form.pai_rg.value = dados.rg;
		document.aluno_form.pai_nome.value = dados.nome;
		document.aluno_form.pai_nome_social.value = dados.nomeSocial
		document.aluno_form.pai_data_nascimento.value = dados.dataNascimento;
		document.aluno_form.pai_sexo.value = dados.sexo;
		document.aluno_form.pai_estado_civil.value = dados.estadoCivil;
		document.aluno_form.pai_nacionalidade.value = dados.nacionalidade;
		document.aluno_form.pai_raca.value = dados.raca;
		document.aluno_form.pai_religiao.value = dados.religiao;
		document.aluno_form.pai_logradouro.value = dados.endereco.logradouro;
		document.aluno_form.pai_complemento.value = dados.endereco.complemento;
		document.aluno_form.pai_bairro.value = dados.endereco.bairro;
		document.aluno_form.pai_cidade.value = dados.endereco.cidade;
		document.aluno_form.pai_uf.value = dados.endereco.uf;
		document.aluno_form.pai_telefone_residencial.value = dados.contatoInfo.telefoneResidencial;
		document.aluno_form.pai_telefone_celular.value = dados.contatoInfo.telefoneCelular;
		document.aluno_form.pai_email.value = dados.contatoInfo.email;
	}
	
	carregaPessoaMae( dados ) {
		document.aluno_form.pai_cpf.value = dados.cpf;
		document.aluno_form.pai_rg.value = dados.rg;
		document.aluno_form.pai_nome.value = dados.nome;
		document.aluno_form.pai_nome_social.value = dados.nomeSocial
		document.aluno_form.pai_data_nascimento.value = dados.dataNascimento;
		document.aluno_form.pai_sexo.value = dados.sexo;
		document.aluno_form.pai_estado_civil.value = dados.estadoCivil;
		document.aluno_form.pai_nacionalidade.value = dados.nacionalidade;
		document.aluno_form.pai_raca.value = dados.raca;
		document.aluno_form.pai_religiao.value = dados.religiao;
		document.aluno_form.pai_logradouro.value = dados.endereco.logradouro;
		document.aluno_form.pai_complemento.value = dados.endereco.complemento;
		document.aluno_form.pai_bairro.value = dados.endereco.bairro;
		document.aluno_form.pai_cidade.value = dados.endereco.cidade;
		document.aluno_form.pai_uf.value = dados.endereco.uf;
		document.aluno_form.pai_telefone_residencial.value = dados.contatoInfo.telefoneResidencial;
		document.aluno_form.pai_telefone_celular.value = dados.contatoInfo.telefoneCelular;
		document.aluno_form.pai_email.value = dados.contatoInfo.email;
	}

	salva() {		
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
		
		let aluno = {
			usuario : {
				username : document.aluno_form.aluno_username.value,
				password
			},
			pessoa : {
				cpf : document.aluno_form.aluno_cpf.value,
				rg : document.aluno_form.aluno_rg.value,
				nome : document.aluno_form.aluno_nome.value,
				nomeSocial : document.aluno_form.aluno_nome_social.value,
				dataNascimento : conversor.formataData( document.aluno_form.aluno_data_nascimento.value ),
				sexo : document.aluno_form.aluno_sexo.value,
				estadoCivil : document.aluno_form.aluno_estado_civil.value,
				nacionalidade : document.aluno_form.aluno_nacionalidade.value,
				raca : document.aluno_form.aluno_raca.value,
				religiao : document.aluno_form.aluno_religiao.value,
				endereco : {
					logradouro : document.aluno_form.aluno_logradouro.value,
					complemento : document.aluno_form.aluno_complemento.value,
					bairro : document.aluno_form.aluno_bairro.value,
					cidade : document.aluno_form.aluno_cidade.value,
					uf : document.aluno_form.aluno_uf.value,	
					cep : document.aluno_form.aluno_cep.value		
				},
				contatoinfo : {
					telefoneResidencial : document.aluno_form.aluno_telefone_residencial.value,
					telefoneCelular : document.aluno_form.aluno_telefone_celular.value,
					email : document.aluno_form.aluno_email.value,
				}
			},
			pai : {
				falecido : document.aluno_form.pai_falecido.checked,
				pessoa : {
					cpf : document.aluno_form.pai_cpf.value,
					rg : document.aluno_form.pai_rg.value,
					nome : document.aluno_form.pai_nome.value,
					nomeSocial : document.aluno_form.pai_nome_social.value,
					dataNascimento : conversor.formataData( document.aluno_form.pai_data_nascimento.value ),
					sexo : document.aluno_form.pai_sexo.value,
					estadoCivil : document.aluno_form.pai_estado_civil.value,
					nacionalidade : document.aluno_form.pai_nacionalidade.value,
					raca : document.aluno_form.pai_raca.value,
					religiao : document.aluno_form.pai_religiao.value,
					endereco : {
						logradouro : document.aluno_form.pai_logradouro.value,
						complemento : document.aluno_form.pai_complemento.value,
						bairro : document.aluno_form.pai_bairro.value,
						cidade : document.aluno_form.pai_cidade.value,
						uf : document.aluno_form.pai_uf.value,
						cep : document.aluno_form.pai_cep.value					
					},
					contatoinfo : {
						telefoneResidencial : document.aluno_form.pai_telefone_residencial.value,
						telefoneCelular : document.aluno_form.pai_telefone_celular.value,
						email : document.aluno_form.pai_email.value,
					}
				}
			},
			mae : {
				falecido : document.aluno_form.mae_falecido.checked,
				pessoa : {
					cpf : document.aluno_form.mae_cpf.value,
					rg : document.aluno_form.mae_rg.value,
					nome : document.aluno_form.mae_nome.value,
					nomeSocial : document.aluno_form.mae_nome_social.value,
					dataNascimento : conversor.formataData( document.aluno_form.mae_data_nascimento.value ),
					sexo : document.aluno_form.mae_sexo.value,
					estadoCivil : document.aluno_form.mae_estado_civil.value,
					nacionalidade : document.aluno_form.mae_nacionalidade.value,
					raca : document.aluno_form.mae_raca.value,
					religiao : document.aluno_form.mae_religiao.value,
					endereco : {
						logradouro : document.aluno_form.mae_logradouro.value,
						complemento : document.aluno_form.mae_complemento.value,
						bairro : document.aluno_form.mae_bairro.value,
						cidade : document.aluno_form.mae_cidade.value,
						uf : document.aluno_form.mae_uf.value,
						cep : document.aluno_form.mae_cep.value				
					},
					contatoinfo : {
						telefoneResidencial : document.aluno_form.mae_telefone_residencial.value,
						telefoneCelular : document.aluno_form.mae_telefone_celular.value,
						email : document.aluno_form.mae_email.value,
					}
				}
			}
		};
		
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( aluno ),
			sucesso : function( resposta ) {							
				sistema.mostraMensagemInfo( "mensagem-el", 'Pessoa salva com êxito.' );																
				instance.limpaForm();
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );
	}
		
	limpaForm() {
		document.aluno_form.aluno_nome.value = "";
	}
	
	mostraEscondePaiModal() {
		sistema.limpaMensagem( "pai_mensagem_el" );
		showHide( 'pai_form_modal' );
	}
	
	mostraEscondeMaeModal() {
		sistema.limpaMensagem( "mae_mensagem_el" );		
		showHide( 'mae_form_modal' );
	}
	
}