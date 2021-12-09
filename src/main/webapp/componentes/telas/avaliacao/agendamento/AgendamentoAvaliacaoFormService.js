
import {sistema} from '../../../../sistema/Sistema.js';

import AgendamentoAvaliacaoFormComponent from './AgendamentoAvaliacaoFormComponent.js';

export default class AgendamentoAvaliacaoFormService {
										
	constructor() {
		this.component = new AgendamentoAvaliacaoFormComponent(); 
	}					
																
	onCarregado() {			
		this.component.configura( {} );		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		this.component.limpaMensagem();
				
		let turmaDisciplinaId = this.component.getFieldValue( 'turma_disciplina' );

		const instance = this;
		sistema.ajax( 'POST', '/api/avaliacao/salva/agendamento/'+turmaDisciplinaId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Agendamento de avaliação realizado com êxito.' );																
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
export const agendamentoAvaliacaoForm = new AgendamentoAvaliacaoFormService();