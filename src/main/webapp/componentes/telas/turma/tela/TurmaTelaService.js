import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {selectService} from '../../../service/SelectService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class TurmaTelaService {

	colunas = [ 'Descrição', 'Serie', 'Curso', 'Ano letivo', 'Escola', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
	}

	onCarregado() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', { 
			onchange : () => {
				let instituicaoId = document.turma_filtro_form.instituicao.value;
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', {
					onchange : () => {
						let escolaId = document.turma_filtro_form.escola.value;
						selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', { 
							onchange : () => {
								instance.filtra( 'anoletivo' );
							} 
						} );				
						selectService.carregaCursosSelect( escolaId, 'cursos_select', { 
							onchange : () => {
								let cursoId = document.turma_filtro_form.curso.value;
								selectService.carregaSeriesSelect( cursoId, 'series_select', { 
									onchange : () => {
										instance.filtra( 'serie' );
									} 
								} );
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
		sistema.carregaPagina( 'turma-detalhes', { turmaId : id } );																	
	}
		
	filtra( tipo ) {	
		sistema.limpaMensagem( 'mensagem-el' );
			
		let url;	
		if ( tipo === 'serie' ) {
			let serieId = document.turma_filtro_form.serie.value;
			url = '/api/turma/filtra/porserie/'+serieId;
		} else {
			let anoLetivoId = document.turma_filtro_form.anoletivo.value;
			url = '/api/turma/filtra/poranoletivo/'+anoLetivoId;			
		}	
				
								
		const instance = this;	
		sistema.ajax( "POST", url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {				
				descricaoIni : document.turma_filtro_form.descricaoini.value,
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "turmaTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "turmaTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( dados[ i ].serie.descricao );
					tdados[ i ].push( dados[ i ].serie.curso.descricao );
					tdados[ i ].push( dados[ i ].anoLetivoAno );
					tdados[ i ].push( dados[ i ].serie.curso.escolaNome );
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
			titulo : "Remoção de turma",
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
		sistema.ajax( "DELETE", "/api/turma/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				sistema.mostraMensagemInfo( "mensagem-el", 'Turma deletada com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'turma-form', { op : 'cadastrar', titulo : 'Registro de turma' } );
	}	

}
export const turmaTela = new TurmaTelaService();