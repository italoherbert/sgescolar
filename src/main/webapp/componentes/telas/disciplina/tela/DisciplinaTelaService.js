import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {selectService} from '../../../service/SelectService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class DisciplinaTelaService {

	colunas = [ 'Descrição', 'Serie', 'Curso', 'Escola', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
	}

	onCarregado() {
		selectService.carregaEscolasSelect( 'escolas_select', { onchange : () => this.onChangeEscola() } );
		
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();					
	}
	
	onChangeEscola() {
		let escolaId = document.disciplina_filtro_form.escola.value;
		selectService.carregaCursosSelect( escolaId, 'cursos_select', { onchange : () => this.onChangeCurso() } );				
	}
	
	onChangeCurso() {
		let cursoId = document.disciplina_filtro_form.curso.value;
		selectService.carregaSeriesSelect( cursoId, 'series_select', { onchange : () => this.onChangeSerie() } );
	}
	
	onChangeSerie() {
		this.filtra();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'disciplina-detalhes', { disciplinaId : id } );																	
	}
		
	filtra() {	
		sistema.limpaMensagem( 'mensagem-el' );
				
		let serieId = document.disciplina_filtro_form.serie.value;
								
		const instance = this;	
		sistema.ajax( "POST", "/api/disciplina/filtra/"+serieId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {				
				descricaoIni : document.disciplina_filtro_form.descricaoini.value,
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "disciplinaTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "disciplinaTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( dados[ i ].serie.descricao);
					tdados[ i ].push( dados[ i ].serie.cursoNome );
					tdados[ i ].push( dados[ i ].serie.escolaNome );
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
			titulo : "Remoção de disciplina",
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
		sistema.ajax( "DELETE", "/api/disciplina/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				sistema.mostraMensagemInfo( "mensagem-el", 'Disciplina deletada com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'disciplina-form', { titulo : "Registro de disciplina", op : "cadastrar" } )
	}		

}
export const disciplinaTela = new DisciplinaTelaService();