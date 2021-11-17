
import {sistema} from '../../../../sistema/Sistema.js';

import RecursoFormComponent from './RecursoFormComponent.js';

export default class RecursoFormService {
										
	constructor() {
		this.component = new RecursoFormComponent( 'recurso_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			recursoId : this.params.recursoId,
			op : this.params.op,			
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/recurso/atualiza/"+this.params.recursoId;
		} else {
			metodo = "POST";
			url = "/api/recurso/registra";
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Recurso salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraRecursosTela() {
		sistema.carregaPagina( 'recurso-tela' );
	}
			
}
export const recursoForm = new RecursoFormService();