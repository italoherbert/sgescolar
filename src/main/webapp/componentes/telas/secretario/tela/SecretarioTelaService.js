import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import SecretarioFiltroFormComponent from './SecretarioFiltroFormComponent.js';

export default class SecretarioTelaService {

	colunas = [ 'Nome', 'Telefone', 'E-Mail', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );		
		this.filtroFormComponent = new SecretarioFiltroFormComponent();
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
		
		this.filtroFormComponent.configura( {} );
		this.filtroFormComponent.carregaHTML();
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
		this.filtroFormComponent.limpaMensagem();
							
		let escolaId = this.filtroFormComponent.getFieldValue( 'escola' );
							
		const instance = this;
		sistema.ajax( "POST", "/api/secretario/filtra/"+escolaId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.filtroFormComponent.getJSON() ),
			sucesso : function( resposta ) {				
				let dados = JSON.parse( resposta );
																											
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "secretarioTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "secretarioTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].funcionario.pessoa.nome );
					tdados[ i ].push( dados[ i ].funcionario.pessoa.contatoInfo.telefoneCelular );
					tdados[ i ].push( dados[ i ].funcionario.pessoa.contatoInfo.email );
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );			
			},
			erro : function( msg ) {
				instance.filtroFormComponent.mostraErro( msg );	
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
		this.filtroFormComponent.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/secretario/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.filtroFormComponent.mostraInfo( 'Secretario deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.filtroFormComponent.mostraErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'secretario-form', { titulo : "Registro de secretario", op : "cadastrar" } )
	}		

}
export const secretarioTela = new SecretarioTelaService();