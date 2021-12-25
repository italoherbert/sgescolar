
import {sistema} from '../../../sistema/Sistema.js';

import AlunoAutoCompleteFormComponent from '../../autocomplete/AlunoAutoCompleteFormComponent.js';

import AvaliacaoExternaFormComponent from './AvaliacaoExternaFormComponent.js';

export default class AvaliacaoExternaFormService {
										
	constructor() {
		this.component = new AvaliacaoExternaFormComponent();
		
		this.alunoAutoCompleteFormComponent = new AlunoAutoCompleteFormComponent( 'avaliacao_externa_form', 'aluno-autocomplete-el' );						 
	}					
																
	onCarregado() {			
		this.component.configura( {} );		
		this.component.carregaHTML();
		
		this.alunoAutoCompleteFormComponent.configura( {} );
		this.alunoAutoCompleteFormComponent.carregaHTML();		
	}
					
	salva() {						
		this.component.limpaMensagem();
				
		let alunoId = this.alunoAutoCompleteFormComponent.selectedId;
		let turmaDisciplinaId = this.component.getFieldValue( 'turma_disciplina' );

		const instance = this;
		sistema.ajax( 'POST', '/api/avaliacao/externa/registra/'+alunoId+"/"+turmaDisciplinaId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Registro de avaliação externa realizado com êxito.' );																
				instance.component.limpaTudo();
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	removeConfirm() {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de avaliação externa",
			msg :  "Digite abaixo o nome <span class='text-danger'>remova</span> para confirmar a remoção",			
			confirm : {
				texto : 'remova',
				bt : {
					rotulo : "Remover",
					onclick : {
						func : function( pars ) {
							this.remove();	
						},
						thisref : this,
					}
				}
			}			
		} );
	}

	remove() {		
		this.component.limpaMensagem();		
		
		let alunoId = this.alunoAutoCompleteFormComponent.selectedId;
		let turmaDisciplinaId = this.component.getFieldValue( 'turma_disciplina' );
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/avaliacao/externa/deleta/"+alunoId+"/"+turmaDisciplinaId, {
			sucesso : function( resposta ) {						
				instance.removeConfirm()
				instance.component.mostraInfo( 'Avaliação externa deletada com êxito.' );
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );		
	}
	
	paraTela() {
		sistema.carregaPagina( 'avaliacao-tela' );
	}
			
}
export const avaliacaoExternaForm = new AvaliacaoExternaFormService();