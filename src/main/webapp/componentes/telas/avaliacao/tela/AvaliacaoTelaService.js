import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import AvaliacaoTelaComponent from './AvaliacaoTelaComponent.js';

export default class AvaliacaoTelaService {

	colunas = [ 'Data de avaliação', 'Disponíveis', 'Turma', 'Resultado', 'Detalhes', 'Remover' ];

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
	
	editarNotas( id ) {
		sistema.carregaPagina( 'resultado-avaliacao-form', { avaliacaoId : id } );
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
					let editaNotasLink = htmlBuilder.novoLinkHTML( 'editar', "avaliacaoTela.editarNotas( " + dados[ i ].id + " )", 'fas fa-edit', 'link-primary' );					
								
					let disponiveisSim = "<span class='text-primary'><b>Sim</b></span>";
					let disponiveisNao = "<span class='text-secondary'><b>Não</b></span>";
										
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].dataAgendamento );
					tdados[ i ].push( dados[ i ].notasDisponiveis === 'true' ? disponiveisSim : disponiveisNao );					
					tdados[ i ].push( dados[ i ].turmaDisciplina.turmaDescricaoDetalhada );					
					tdados[ i ].push( editaNotasLink );										
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
			
}
export const avaliacaoTela = new AvaliacaoTelaService();