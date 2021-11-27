import {sistema} from "../../../../sistema/Sistema.js";

import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';
import ProfessorAutoCompleteFormComponent from '../ProfessorAutoCompleteFormComponent.js';

export default class ProfessorAlocacaoTelaService {

	colunas = [ 'Professor', 'Turma', 'Disciplina', 'Serie', 'Curso', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.professorAutoCompleteFormComponent = new ProfessorAutoCompleteFormComponent( 'lst_professor_alocacao_form', 'form-professor-el' );
		
		this.professorAutoCompleteFormComponent.onItemSelecionado = ( id, value ) => this.lista();
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();				
		
		this.professorAutoCompleteFormComponent.configura( {} );
		this.professorAutoCompleteFormComponent.carregaHTML();
	}
	
	lista() {	
		this.tabelaComponent.limpaMensagem();
		
		let professorId = this.professorAutoCompleteFormComponent.selectedId;				
					
		if ( isNaN( parseInt( professorId ) ) === true ) {
			this.tabelaComponent.mostraErro( 'A seleção do professor é obrigatória para esta listagem.' );
			return;
		}			
								
		const instance = this;	
		sistema.ajax( "GET", '/api/professor-alocacao/lista/'+professorId, {			
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "professorAlocacaoTela.deleteConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].professorNome );
					tdados[ i ].push( dados[ i ].turmaDisciplina.turma.descricao );
					tdados[ i ].push( dados[ i ].turmaDisciplina.disciplina.descricao );
					tdados[ i ].push( dados[ i ].turmaDisciplina.turma.serie.descricao );
					tdados[ i ].push( dados[ i ].turmaDisciplina.turma.serie.curso.descricao );
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
		sistema.ajax( "DELETE", '/api/professor-alocacao/deleta/'+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				instance.tabelaComponent.mostraInfo( 'Vínculo de alocação de professor deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );		
	}
	
	paraProfessorAlocacaoForm() {
		sistema.carregaPagina( 'professor-alocacao-form' );
	}

}
export const professorAlocacaoTela = new ProfessorAlocacaoTelaService();