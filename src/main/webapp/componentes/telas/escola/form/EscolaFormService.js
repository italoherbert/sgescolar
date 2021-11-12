
import {sistema} from '../../../../sistema/Sistema.js';

import EscolaFormComponent from './EscolaFormComponent.js';

export default class EscolaFormService {
										
	constructor() {
		this.component = new EscolaFormComponent( 'escola_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			escolaId : this.params.escolaId,
			op : this.params.op,
			
			pai_resumo_titulo : "Resumo do pai",
			pai_resumo_dados_completos_btn_rotulo : 'Informar dados do pai',
			pai_modal_titulo : "Formulário de pai do escola",
			
			mae_resumo_titulo : "Resumo da mãe",
			mae_resumo_dados_completos_btn_rotulo : 'Informar dados da mãe',						
			mae_modal_titulo : "Formulário de mãe do escola"
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/escola/atualiza/"+this.params.escolaId;
		} else {
			metodo = "POST";
			url = "/api/escola/registra";
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Escola salva com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraEscolasTela() {
		sistema.carregaPagina( 'escola-tela' );
	}
			
}
export const escolaForm = new EscolaFormService();