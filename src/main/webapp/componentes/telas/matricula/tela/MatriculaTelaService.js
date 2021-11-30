import {sistema} from "../../../../sistema/Sistema.js";

import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';
import AlunoAutoCompleteFormComponent from '../../../autocomplete/AlunoAutoCompleteFormComponent.js';

export default class MatriculaTelaService {

	colunas = [ 'Aluno', 'Nº da matrícula', 'Turma', 'Serie', 'Curso', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.alunoAutoCompleteFormComponent = new AlunoAutoCompleteFormComponent( 'lst_matricula_form', 'aluno-autocomplete-el' );
		
		this.alunoAutoCompleteFormComponent.onItemSelecionado = ( id, value ) => this.lista();
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();				
		
		this.alunoAutoCompleteFormComponent.configura( {} );
		this.alunoAutoCompleteFormComponent.carregaHTML();
	}
	
	lista() {	
		this.tabelaComponent.limpaMensagem();
		
		let alunoId = this.alunoAutoCompleteFormComponent.selectedId;				
					
		if ( isNaN( parseInt( alunoId ) ) === true ) {
			this.tabelaComponent.mostraErro( 'A seleção do aluno é obrigatória para esta listagem.' );
			return;
		}			
								
		const instance = this;	
		sistema.ajax( "GET", '/api/matricula/lista/'+alunoId, {			
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "matriculaTela.deleteConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].alunoNome );
					tdados[ i ].push( dados[ i ].numero );
					tdados[ i ].push( dados[ i ].turma.descricao );
					tdados[ i ].push( dados[ i ].turma.serie.descricao );
					tdados[ i ].push( dados[ i ].turma.serie.curso.descricao );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );
			},
			erro : function( msg ) {
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
				instance.lista();
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