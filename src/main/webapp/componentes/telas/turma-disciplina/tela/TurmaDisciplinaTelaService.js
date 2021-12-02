import {sistema} from "../../../../sistema/Sistema.js";

import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';
import TurmaDisciplinaTelaComponent from './TurmaDisciplinaTelaComponent.js';

export default class TurmaDisciplinaTelaService {

	colunas = [ 'Disciplina', 'Turma', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new TurmaDisciplinaTelaComponent( 'listagem_turma_disciplina_form', 'lista-mensagem-el' );
		
		this.telaComponent.onChangeTurma = () => this.lista();
	}

	onCarregado() {				
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();						
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();
	}
	
	lista() {	
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaMensagem();
		
		let turmaId = this.telaComponent.getFieldValue( 'turma' );
		
		if ( isNaN( parseInt( turmaId ) ) === true ) {
			this.tabelaComponent.mostraErro( 'A seleção da turma é obrigatória para esta listagem.' );
			return;
		}				
								
		const instance = this;	
		sistema.ajax( "GET", '/api/turma-disciplina/lista/porturma/'+turmaId, {			
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "turmaDisciplinaTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].disciplinaDescricao );
					tdados[ i ].push( dados[ i ].turmaDescricaoDetalhada );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );
			},
			erro : function( msg ) {
				instance.telaComponent.mostraErro( msg );	
			}
		} );	
	}
							
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de vínculo de disciplina",
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
		sistema.ajax( "DELETE", '/api/turma-disciplina/deleta/'+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				instance.tabelaComponent.mostraInfo( 'Vínculo de turma e disciplina deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'turma-disciplina-form' );
	}

}
export const turmaDisciplinaTela = new TurmaDisciplinaTelaService();