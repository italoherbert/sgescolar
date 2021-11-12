import {sistema} from "../../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/TabelaComponent.js';

export default class UsuarioTelaService {

	colunas = [ 'Nome de usuário', 'Perfil', 'Detalhes', 'Remover' ];

	constructor() {		
		this.tabelaComponent = new TabelaComponent( 'tabela', 'tabela-el', this.colunas );
		this.tabelaComponent.onTabelaModeloCarregado = () => this.filtra();
	}

	onCarregado() {					
		this.tabelaComponent.configura( {} );		
		this.tabelaComponent.carregaHTML();
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {				
		const instance = this;
		sistema.ajax( "POST", "/api/usuario/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				usernameIni : document.usuario_filtro_form.usernameini.value
			} ),
			sucesso : function( resposta ) {				
				let dados = JSON.parse( resposta );
																											
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "usuarioTela.detalhes( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].username );
					tdados[ i ].push( dados[ i ].perfil );
					tdados[ i ].push( detalhesLink );
					
					let removerLink;
					if ( dados[ i ].perfil === 'ADMIN' ) {
						removerLink = htmlBuilder.novoLinkRemoverHTML( "usuarioTela.removeConfirm( " + dados[ i ].id + " )" );								
					} else {
						removerLink = "<span class=\"text-success\">Não admin!</span>";	
					}	
					
					tdados[ i ].push( removerLink );	
				}
								
				instance.tabelaComponent.carregaTBody( tdados );			
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de usuario",
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
		sistema.ajax( "DELETE", "/api/usuario/deleta/"+id, {
			sucesso : function( resposta ) {						
				sistema.mostraMensagemInfo( "mensagem-el", 'Usuario deletado com êxito.' );
				instance.filtra();
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'usuario-detalhes-2', { usuarioId : id } );																	
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'usuario-form-2', { titulo : "Registro de usuario", op : "cadastrar" } )
	}		

}
export const usuarioTela = new UsuarioTelaService();