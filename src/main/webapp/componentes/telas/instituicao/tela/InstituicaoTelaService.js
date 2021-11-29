import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import InstituicaoTelaComponent from './InstituicaoTelaComponent.js'

export default class InstituicaoTelaService {

	colunas = [ 'CNPJ', 'Razão social',  'Telefone', 'E-Mail', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new InstituicaoTelaComponent();
		
		this.tabelaComponent.onTabelaModeloCarregado = () => this.filtra();						
	}

	onCarregado() {
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();	
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'instituicao-detalhes', { instituicaoId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {	
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
						
		const instance = this;	
		sistema.ajax( "POST", "/api/instituicao/filtra", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.telaComponent.getJSON() ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "instituicaoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "instituicaoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].cnpj );
					tdados[ i ].push( dados[ i ].razaoSocial );
					tdados[ i ].push( dados[ i ].contatoInfo.telefoneFixo );
					tdados[ i ].push( dados[ i ].contatoInfo.email );
					tdados[ i ].push( detalhesLink );
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
			titulo : "Remoção de instituicao",
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
		sistema.ajax( "DELETE", "/api/instituicao/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Instituição deletada com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'instituicao-form', { titulo : "Registro de instituicao", op : "cadastrar" } );		
	}		

}
export const instituicaoTela = new InstituicaoTelaService();