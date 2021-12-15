
import {sistema} from '../../../../sistema/Sistema.js';

import ResultadoAvaliacaoFormComponent from './ResultadoAvaliacaoFormComponent.js';

export default class ResultadoAvaliacaoFormService {
										
	constructor() {
		this.component = new ResultadoAvaliacaoFormComponent(); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			avaliacaoId : this.params.avaliacaoId 
		} );		
		this.component.carregaHTML();																	
	}
				
	recarrega() {
		this.component.carrega( this.params.avaliacaoId );
	}			
					
	salva() {						
		this.component.limpaMensagem();
				
		let avaliacaoId = this.params.avaliacaoId;

		const instance = this;
		sistema.ajax( 'POST', '/api/avaliacao/salva/resultado/'+avaliacaoId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Resultado de avaliação salvo com êxito.' );																
				instance.component.limpaTudo();
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
export const resultadoAvaliacaoForm = new ResultadoAvaliacaoFormService();