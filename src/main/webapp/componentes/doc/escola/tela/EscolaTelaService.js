import {sistema} from "../../../../sistema/Sistema.js";
import {modeloManager} from "../../../../sistema/nucleo/ModeloManager.js";

export default class EscolaTelaService {

	onCarregado() {						
		this.filtra();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'escola-detalhes', { escolaId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {						
		sistema.ajax( "POST", "/api/escola/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.escola_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = modeloManager.criaLinkDetalhesHTML( "escolaTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = modeloManager.criaLinkRemoverHTML( "escolaTela.removeConfirm( " + dados[ i ].id + " )" );
					
					html += "<tr>" 
						+ "<td>" + dados[ i ].pessoa.nome + "</td>" 
						+ "<td>" + dados[ i ].pessoa.contatoInfo.telefoneFixo + "</td>" 
						+ "<td>" + dados[ i ].pessoa.contatoInfo.email + "</td>"
						+ "<td>" + detalhesLink + "</td>" 	 
						+ "<td>" + removerLink + "</td>" 	 
						+ "</tr>";
				}
								
				document.getElementById( "tbody-escolas-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de escola",
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
		sistema.ajax( "DELETE", "/api/escola/deleta/"+id, {
			sucesso : function( resposta ) {						
				sistema.mostraMensagemInfo( "mensagem-el", 'Escola deletada com êxito.' );
				instance.filtra();
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'escola-form', { titulo : "Registro de escola" } )
	}		

}
export const escolaTela = new EscolaTelaService();