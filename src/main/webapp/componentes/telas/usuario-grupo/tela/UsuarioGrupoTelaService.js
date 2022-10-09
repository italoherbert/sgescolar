import {sistema} from "../../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class UsuarioGrupoTelaService {

	colunas = [ 'Nome do grupo', 'Detalhes', 'Remover' ];

	constructor() {		
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
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
		this.tabelaComponent.limpaMensagem();
							
		const instance = this;
		
		sistema.ajax( "POST", "/api/usuario/grupo/filtra", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.usuario_grupo_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {				
				let dados = JSON.parse( resposta );
																											
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "usuarioGrupoTela.detalhes( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].nome );
					tdados[ i ].push( detalhesLink );
					
					let removerLink;
					if ( dados[ i ].deletavel === 'true' ) {
						removerLink = htmlBuilder.novoLinkRemoverHTML( "usuarioGrupoTela.removeConfirm( " + dados[ i ].id + " )" );								
					} else {
						removerLink = "<span class=\"text-success\">Não deletável!</span>";	
					}	
					
					tdados[ i ].push( removerLink );	
				}
								
				instance.tabelaComponent.carregaTBody( tdados );			
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de grupo de usuário",
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
		this.tabelaComponent.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/usuario/grupo/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Grupo de usuário deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'usuario-grupo-detalhes', { usuarioGrupoId : id } );																	
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'usuario-grupo-form', { titulo : "Registro de grupo de usuário", op : "cadastrar" } )
	}		

}
export const usuarioGrupoTela = new UsuarioGrupoTelaService();