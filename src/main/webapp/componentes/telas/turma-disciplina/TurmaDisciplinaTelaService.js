import {sistema} from "../../../sistema/Sistema.js";

import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';

import TabelaComponent from '../../component/tabela/TabelaComponent.js';
import TurmaDisciplinaFormComponent from './TurmaDisciplinaFormComponent.js';
import TurmaDisciplinaListagemFormComponent from './TurmaDisciplinaListagemFormComponent.js';

export default class TurmaDisciplinaTelaService {

	colunas = [ 'Disciplina', 'Turma', 'Serie', 'Curso', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.formComponent = new TurmaDisciplinaFormComponent();
		this.listagemFormComponent = new TurmaDisciplinaListagemFormComponent();
	}

	onCarregado() {				
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
				
		this.formComponent.configura( {} );
		this.formComponent.carregaHTML();				
		
		this.listagemFormComponent.configura( {} );
		this.listagemFormComponent.carregaHTML();
	}
	
	lista() {	
		this.listagemFormComponent.limpaMensagem();
		
		let turmaId = this.listagemFormComponent.getFieldValue( 'lst_turma' );				
								
		const instance = this;	
		sistema.ajax( "GET", '/api/turma-disciplina/lista/porturma/'+turmaId, {			
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "turmaDisciplinaTela.desvincula( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].disciplina.descricao );
					tdados[ i ].push( dados[ i ].turma.descricao );
					tdados[ i ].push( dados[ i ].turma.serie.descricao );
					tdados[ i ].push( dados[ i ].turma.cursoNome );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );
			},
			erro : function( msg ) {
				instance.listagemFormComponent.mostraErro( msg );	
			}
		} );	
	}
			
	vincula() {
		sistema.limpaMensagem( 'form-mensagem-el' );				
		
		let disciplinaId = this.formComponent.getFieldValue( 'disciplina' );
		let turmaId = this.formComponent.getFieldValue( 'turma' );
						
		const instance = this;	
		sistema.ajax( 'POST', '/api/turma-disciplina/registra/'+turmaId+"/"+disciplinaId, {			
			sucesso : ( resposta ) => {
				instance.formComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Disciplina e turma vinculadas com sucesso!' );
			},
			erro : ( msg ) => {
				this.formComponent.mostraErro( msg );
			}
		} );
	}
			
	desvincula( id ) {
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
		sistema.limpaMensagem( "form-mensagem-el" );
														
		const instance = this;
		sistema.ajax( "DELETE", '/api/turma-disciplina/deleta/'+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				instance.listagemFormComponent.mostraInfo( 'Vínculo de turma e disciplina deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.listagemFormComponent.mostraErro( msg );
			}
		} );		
	}

}
export const turmaDisciplinaTela = new TurmaDisciplinaTelaService();