import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";
import {conversor} from '../../../../sistema/util/Conversor.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import AvaliacaoTelaComponent from './AvaliacaoTelaComponent.js';

export default class AvaliacaoTelaService {

	colunas = [ 'Data de avaliação', 'Peso', 'Turma', 'Resultado', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new AvaliacaoTelaComponent();		
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'avaliacao-detalhes', { avaliacaoId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.lista();
	}
	
	lista() {	
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
		
		let turmaDisciplinaId = this.telaComponent.getFieldValue( 'turma_disciplina' );			
										
		const instance = this;				
		sistema.ajax( "GET", "/api/avaliacao/lista/"+turmaDisciplinaId, {			
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
																											
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "avaliacaoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "avaliacaoTela.removeConfirm( " + dados[ i ].id + " )" );
										
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].dataAgendamento );
					tdados[ i ].push( conversor.formataFloat( dados[ i ].peso ) );					
					tdados[ i ].push( dados[ i ].turmaDisciplina.turmaDescricaoDetalhada );
					
					if ( dados[ i ].notasDisponiveis === 'false' ) {
						let editaNotasLink = htmlBuilder.novoLinkHTML( 'editar', "avaliacaoTela.paraResultadoForm( " + dados[ i ].id + " )", 'fas fa-edit', 'link-primary' );				
						tdados[ i ].push( editaNotasLink );
					} else {
						tdados[ i ].push( '<span class="text-primary">disponíveis</span>' );
					}
					
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );				
				
				if ( dados.length == 0 )
					instance.tabelaComponent.mostraInfo( 'Nenhum avaliação encontrado pelos critérios de busca informados.' );		
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de avaliacao",
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
		sistema.ajax( "DELETE", "/api/avaliacao/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				instance.tabelaComponent.mostraInfo( 'Avaliação deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraAgendamentoForm() {
		sistema.carregaPagina( 'agendamento-avaliacao-form' );
	}
	
	paraResultadoForm( id ) {
		sistema.carregaPagina( 'resultado-avaliacao-form', { avaliacaoId : id } );
	}
	
}
export const avaliacaoTela = new AvaliacaoTelaService();