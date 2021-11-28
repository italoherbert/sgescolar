import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {selectService} from '../../../service/SelectService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class AnoLetivoTelaService {

	colunas = [ 'Ano', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
	}

	onCarregado() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : ( e ) => {
				let instituicaoId = e.target.value;
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', {
					onchange : () => {
						instance.busca();			
					}
				} );
			}
		} );
		
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();	
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'anoletivo-detalhes', { anoLetivoId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	busca() {
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();	
						
		let escolaId = document.anoletivo_filtro_form.escola.value;		
		let todosOsAnos = document.anoletivo_filtro_form.anostodos.checked;					
						
		let url;
		if ( todosOsAnos === true ) {
			url = '/api/anoletivo/lista/'+escolaId;
		} else {
			let ano = document.anoletivo_filtro_form.ano.value;
			url = '/api/anoletivo/busca/'+escolaId+"/"+ano;
		}			
						
		const instance = this;	
		sistema.ajax( "GET", url, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
	
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "anoletivoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "anoletivoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].ano );
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
			titulo : "Remoção de anoletivo",
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
		sistema.ajax( "DELETE", "/api/anoletivo/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				sistema.mostraMensagemInfo( "mensagem-el", 'Ano letivo deletado com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'anoletivo-form', { titulo : "Registro de ano letivo", op : "cadastrar" } )
	}		

}
export const anoletivoTela = new AnoLetivoTelaService();