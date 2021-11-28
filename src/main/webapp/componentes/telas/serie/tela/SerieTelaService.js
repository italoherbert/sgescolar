import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {selectService} from '../../../service/SelectService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class SerieTelaService {

	colunas = [ 'Descrição', 'Curso', 'Escola', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
	}

	onCarregado() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = document.serie_filtro_form.instituicao.value;
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', { 
					onchange : () => {
						let escolaId = document.serie_filtro_form.escola.value;
						selectService.carregaCursosSelect( escolaId, 'cursos_select', { 
							onchange : () => {
								instance.filtra();
							}
						} );
					} 
				} );				
			}
		} );
		
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();					
	}		

	detalhes( id ) {
		sistema.carregaPagina( 'serie-detalhes', { serieId : id } );																	
	}
		
	filtra() {	
		sistema.limpaMensagem( 'mensagem-el' );
				
		let cursoId = document.serie_filtro_form.curso.value;
								
		const instance = this;	
		sistema.ajax( "POST", "/api/serie/filtra/"+cursoId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {				
				descricaoIni : document.serie_filtro_form.descricaoini.value,
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "serieTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "serieTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( dados[ i ].curso.descricao );
					tdados[ i ].push( dados[ i ].curso.escolaNome );
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
			titulo : "Remoção de serie",
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
		sistema.ajax( "DELETE", "/api/serie/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				sistema.mostraMensagemInfo( "mensagem-el", 'Serie deletada com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'serie-form', { titulo : "Registro de serie", op : "cadastrar" } )
	}		

}
export const serieTela = new SerieTelaService();