import {sistema} from "../../../../sistema/Sistema.js";
import {conversor} from '../../../../sistema/util/Conversor.js';

import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import MatriculaTelaComponent from './MatriculaTelaComponent.js';

export default class MatriculaTelaService {

	colunas = [ 'Aluno', 'Nº da matrícula', 'Turma', 'Abertura', 'Encerramento',  'Encerrar/Reabrir', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new MatriculaTelaComponent();
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();	
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();
	}
	
	filtra() {	
		this.telaComponent.limpaMensagem();
		
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
		
		let turmaId = this.telaComponent.getFieldValue( 'turma' );				
					
		if ( isNaN( parseInt( turmaId ) ) === true ) {
			this.tabelaComponent.mostraErro( 'A seleção da turma é obrigatória para esta filtragem.' );
			return;
		}			
								
		const instance = this;	
		sistema.ajax( "POST", '/api/matricula/filtra/todas/'+turmaId, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( this.telaComponent.getJSON() ),		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "matriculaTela.deleteConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].alunoNome );
					tdados[ i ].push( dados[ i ].numero );
					tdados[ i ].push( dados[ i ].turma.descricaoDetalhada );
					tdados[ i ].push( conversor.formataData( dados[ i ].dataInicio ) );
					tdados[ i ].push( conversor.formataData( dados[ i ].dataEncerramento ) );
					
					if ( dados[ i ].encerrada === 'true' ) {
						tdados[ i ].push( htmlBuilder.novoLinkHTML( 'reabrir', 'matriculaTela.reabre( '+dados[i].id+' )', 'fas fa-folder-open', 'link-primary' ) );
						//tdados[ i ].push( '<a href="#!" class="btn btn-link btn-success" onclick="matriculaTela.reabre( '+dados[i].id+')">Reabrir</a>' );
					} else {
						tdados[ i ].push( htmlBuilder.novoLinkHTML( 'encerrar', 'matriculaTela.encerra( '+dados[i].id+' )', 'fas fa-times-circle', 'link-success' ) );
						//tdados[ i ].push( '<a href="#!" class="btn btn-link btn-warning" onclick="matriculaTela.encerra( '+dados[i].id+')">Encerrar</a>' );
					}
					
					tdados[ i ].push( removerLink );					
				}				
												
				instance.tabelaComponent.carregaTBody( tdados );				
				
				if ( dados.length == 0 )
					instance.tabelaComponent.mostraInfo( 'Nenhuma matrícula encontrada pelos critérios de busca informados.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );	
	}
	
	encerra( id ) {
		const instance = this;
		sistema.ajax( 'POST', '/api/matricula/encerra/'+id, {
			sucesso : ( resposta ) => {
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Encerramento concluído com sucesso' );
			},
			erro : ( msg ) => {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );
	}
	
	reabre( id ) {
		const instance = this;
		sistema.ajax( 'POST', '/api/matricula/reabre/'+id, {
			sucesso : ( resposta ) => {
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Reabertura concluída com sucesso' );
			},
			erro : ( msg ) => {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );
	}
							
	deleteConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de matrícula",
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
		sistema.ajax( "DELETE", '/api/matricula/deleta/'+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Matrícula de aluno deletada com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );		
	}
	
	paraForm() {
		sistema.carregaPagina( 'matricula-form' );
	}

}
export const matriculaTela = new MatriculaTelaService();