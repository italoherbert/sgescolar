
import {sistema} from '../../../../sistema/Sistema.js';

import AlunoFormComponent from './AlunoFormComponent.js';

export default class AlunoFormService {
										
	constructor() {
		this.component = new AlunoFormComponent( 'aluno_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			alunoId : this.params.alunoId,
			op : this.params.op,		
			
			pai_resumo_titulo : "Resumo do pai",
			pai_resumo_dados_completos_btn_rotulo : 'Informar dados do pai',
			pai_modal_titulo : "Formulário de pai do aluno",
			
			mae_resumo_titulo : "Resumo da mãe",
			mae_resumo_dados_completos_btn_rotulo : 'Informar dados da mãe',						
			mae_modal_titulo : "Formulário de mãe do aluno"	
		} );
		
		this.component.carregaHTML();																	
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
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Aluno salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraTela() {
		sistema.carregaPagina( 'aluno-tela' );
	}
			
}
export const alunoForm = new AlunoFormService();