
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import ProfessorFormComponent from './ProfessorFormComponent.js';

export default class ProfessorFormService {
										
	constructor() {
		this.component = new ProfessorFormComponent( 'professor_form' );
		this.component.novoHTMLLinkRemoveDiploma = this.novoHTMLLinkRemoveDiploma;		
	}					
																
	onCarregado() {			
		this.component.configura( {
			professorId : this.params.professorId,
			op : this.params.op,						
		} );
				
		this.component.carregaHTML();				
	}
	
	addDiploma() {
		this.component.addDiploma();
	}
	
	removeDiploma( id ) {
		this.component.removeDiploma( id );
	}
	
	novoHTMLLinkRemoveDiploma( id ) {
		return htmlBuilder.novoLinkRemoverHTML( 'professorForm.removeDiploma( '+id+' )' );
	}
						
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/professor/atualiza/"+this.params.professorId;
		} else {
			metodo = "POST";
			url = "/api/professor/registra";
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Professor salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}		
	
	paraTela() {
		sistema.carregaPagina( 'professor-tela' );
	}
			
}
export const professorForm = new ProfessorFormService();