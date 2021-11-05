
import {sistema} from '../../../../sistema/Sistema.js';

import AlunoFormContent from './AlunoFormContent.js';

export default class AlunoForm {
										
	constructor() {
		this.content = new AlunoFormContent();
	}					
																
	onCarregado() {	
		this.content.configura( document.aluno_form, {
			alunoId : this.params.alunoId,
			op : this.params.op,
			titulo : "",
			dados_completos_btn_rotulo : 'Dados completos'
		} );
		
		this.content.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/aluno/atualiza/"+this.params.alunoId;
		} else {
			metodo = "POST";
			url = "/api/aluno/registra";
		}
		
		this.content.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.content.getJSON() ),
			sucesso : function( resposta ) {	
				instance.content.mostraInfo( 'Pessoa salva com êxito.' );																
				instance.content.limpaTudo();
			},
			erro : function( msg ) {
				instance.content.mostraErro( msg );	
			}
		} );
	}
			
}
export const alunoForm = new AlunoForm();