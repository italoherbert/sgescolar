
import {sistema} from '../../../../sistema/Sistema.js';

import AlunoFormComp from './AlunoFormComp.js';

export default class AlunoForm {
										
	constructor() {
		this.comp = new AlunoFormComp();
	}					
																
	onCarregado() {			
		this.comp.formConfigura( document.aluno_form, {
			alunoId : this.params.alunoId,
			op : this.params.op,
			
			pai_resumo_titulo : "Resumo do pai",
			pai_resumo_dados_completos_btn_rotulo : 'Dados completos do pai',
			pai_modal_titulo : "Formulário de pai do aluno",
			
			mae_resumo_titulo : "Resumo da mãe",
			mae_resumo_dados_completos_btn_rotulo : 'Dados completos da mãe',						
			mae_modal_titulo : "Formulário de mãe do aluno"
		} );
		
		this.comp.carregaHTML();																	
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
		
		this.comp.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.comp.getJSON() ),
			sucesso : function( resposta ) {	
				instance.comp.mostraInfo( 'Pessoa salva com êxito.' );																
				instance.comp.limpaTudo();
			},
			erro : function( msg ) {
				instance.comp.mostraErro( msg );	
			}
		} );
	}
	
	paraAlunosTela() {
		sistema.carregaPagina( 'aluno-tela' );
	}
			
}
export const alunoForm = new AlunoForm();