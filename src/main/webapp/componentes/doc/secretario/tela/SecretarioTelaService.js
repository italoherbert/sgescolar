import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

export default class SecretarioTelaService {

	onCarregado() {						
		this.filtra();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'secretario-detalhes', { secretarioId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {						
		sistema.ajax( "POST", "/api/secretario/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.secretario_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
																											
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "secretarioTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "secretarioTela.removeConfirm( " + dados[ i ].id + " )" );
					
					html += "<tr>" 
						+ "<td>" + dados[ i ].funcionario.pessoa.nome + "</td>" 
						+ "<td>" + dados[ i ].funcionario.pessoa.contatoInfo.telefoneCelular + "</td>" 
						+ "<td>" + dados[ i ].funcionario.pessoa.contatoInfo.email + "</td>"
						+ "<td>" + detalhesLink + "</td>" 	 
						+ "<td>" + removerLink + "</td>" 	 
						+ "</tr>";
				}
								
				document.getElementById( "tbody-secretarios-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de secretario",
			msg :  "Digite abaixo o nome <span class='text-danger'>remova</span> para confirmar a remoção",			
			confirm : {
				texto : 'remova',
				bt : {
					rotulo : "Remover",
					onclick : {
						func : function( pars ) {
							this.remove( pars.id );	
						},
						thisref : this,
						params : { id : id }
					}
				}
			}			
		} );
	}

	remove( id ) {				
		sistema.limpaMensagem( "mensagem-el" );
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/secretario/deleta/"+id, {
			sucesso : function( resposta ) {						
				sistema.mostraMensagemInfo( "mensagem-el", 'Secretario deletado com êxito.' );
				instance.filtra();
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'secretario-form', { titulo : "Registro de secretario", op : "cadastrar" } )
	}		

}
export const secretarioTela = new SecretarioTelaService();