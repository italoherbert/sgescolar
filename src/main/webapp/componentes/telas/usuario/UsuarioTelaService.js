import {sistema} from "../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../component/TabelaComponent.js';
import UsuarioTelaFormComponent from './UsuarioTelaFormComponent.js';

export default class UsuarioTelaService {

	colunas = [ 'Nome de usuário', 'Perfil', 'Detalhes', 'Remover' ];

	constructor() {
		this.formComponent = new UsuarioTelaFormComponent( 'usuario_form' ); 		
		
		this.tabelaComponent = new TabelaComponent( 'tabela', 'tabela-el', this.colunas );
		this.tabelaComponent.onTabelaModeloCarregado = () => this.filtra();
	}

	onCarregado() {			
		this.formComponent.configura( {
			usuarioId : this.params.usuarioId,
			op : this.params.op,			
		} );		
				
		this.tabelaComponent.configura( {} );
		
		this.formComponent.carregaHTML();
		this.tabelaComponent.carregaHTML();
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/usuario/atualiza/"+this.params.usuarioId;
		} else {
			metodo = "POST";
			url = "/api/usuario/registra";
		}
		
		this.formComponent.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.formComponent.getJSON() ),
			sucesso : function( resposta ) {	
				instance.formComponent.mostraInfo( 'Usuario salvo com êxito.' );																
				instance.formComponent.limpaTudo();
				instance.params.op = 'cadastrar';
				
				instance.filtra();
			},
			erro : function( msg ) {
				instance.formComponent.mostraErro( msg );	
			}
		} );
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
					let editarLink = htmlBuilder.novoLinkEditarHTML( "usuarioTela.edita( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].username );
					tdados[ i ].push( dados[ i ].grupo.perfil );
					tdados[ i ].push( editarLink );
					
					let removerLink;
					if ( dados[ i ].grupo.perfil === 'ADMIN' ) {
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
	
	
	edita( id ) {
		const instance = this;						
		sistema.ajax( "GET", "/api/usuario/get/"+id, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.formComponent.carregaJSON( dados );	
				document.getElementById( 'form-el' ).scrollIntoView();					
			},
			erro : function( msg ) {
				instance.formComponent.mostraErro( msg );	
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
	
	paraFormRegistro() {
		sistema.carregaPagina( 'usuario-form', { titulo : "Registro de usuario", op : "cadastrar" } )
	}		

}
export const usuarioTela = new UsuarioTelaService();