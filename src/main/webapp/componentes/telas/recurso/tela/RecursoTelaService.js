import {sistema} from "../../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class RecursoTelaService {

	colunas = [ 'Nome do recurso', 'Detalhes', 'Remover' ];

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
		sistema.limpaMensagem( 'mensagem-el' );
							
		const instance = this;
		
		sistema.ajax( "POST", "/api/recurso/filtra", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.recurso_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {				
				let dados = JSON.parse( resposta );
																											
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "recursoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "recursoTela.removeConfirm( " + dados[ i ].id + " )" );								
				
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].nome );
					tdados[ i ].push( detalhesLink );										
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
			titulo : "Remoção de recurso",
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
		sistema.ajax( "DELETE", "/api/recurso/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				sistema.mostraMensagemInfo( "mensagem-el", 'Recurso deletado com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'recurso-detalhes', { recursoId : id } );																	
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'recurso-form', { titulo : "Registro de recurso", op : "cadastrar" } )
	}		

}
export const recursoTela = new RecursoTelaService();