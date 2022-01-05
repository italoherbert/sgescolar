import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class PeriodoTelaService {

	colunas = [ 'Tipo', 'Descricao', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
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
	
	detalhes( id ) {
		sistema.carregaPagina( 'periodo-detalhes', { periodoId : id } );																	
	}
		
	lista() {	
		this.tabelaComponent.limpaMensagem();					
		this.tabelaComponent.limpaTBody();					
		
		let anoLetivoId = perfilService.getAnoLetivoID();
								
		const instance = this;	
		sistema.ajax( "GET", '/api/periodo/lista/'+anoLetivoId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "periodoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "periodoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].tipo.label );
					tdados[ i ].push( dados[ i ].descricao );
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
			titulo : "Remoção de periodo",
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
		sistema.ajax( "DELETE", "/api/periodo/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				instance.tabelaComponent.mostraInfo( 'Período deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraInfo( msg );	
			}
		} );		
	}

}
export const periodoTela = new PeriodoTelaService();