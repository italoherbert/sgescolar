import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

export default class ProfessorTelaService {

	onCarregado() {						
		this.filtra();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'professor-detalhes', { professorId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {						
		sistema.ajax( "POST", "/api/professor/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.professor_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
																											
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "professorTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "professorTela.removeConfirm( " + dados[ i ].id + " )" );
					
					html += "<tr>" 
						+ "<td>" + dados[ i ].funcionario.pessoa.nome + "</td>" 
						+ "<td>" + dados[ i ].funcionario.pessoa.contatoInfo.telefoneCelular + "</td>" 
						+ "<td>" + dados[ i ].funcionario.pessoa.contatoInfo.email + "</td>"
						+ "<td>" + detalhesLink + "</td>" 	 
						+ "<td>" + removerLink + "</td>" 	 
						+ "</tr>";
				}
								
				document.getElementById( "tbody-professores-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de professor",
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
		sistema.ajax( "DELETE", "/api/professor/deleta/"+id, {
			sucesso : function( resposta ) {						
				sistema.mostraMensagemInfo( "mensagem-el", 'Professor deletado com êxito.' );
				instance.filtra();
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'professor-form', { titulo : "Registro de professor", op : "cadastrar" } )
	}		

}
export const professorTela = new ProfessorTelaService();