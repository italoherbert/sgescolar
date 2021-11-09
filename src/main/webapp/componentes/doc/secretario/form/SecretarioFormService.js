
import {sistema} from '../../../../sistema/Sistema.js';

import SecretarioFormComponent from './SecretarioFormComponent.js';

export default class SecretarioFormService {
										
	constructor() {
		this.component = new SecretarioFormComponent( 'secretario_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			secretarioId : this.params.secretarioId,
			op : this.params.op,						
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/secretario/atualiza/"+this.params.secretarioId;
		} else {
			metodo = "POST";
			url = "/api/secretario/registra";
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Secretario salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraSecretariosTela() {
		sistema.carregaPagina( 'secretario-tela' );
	}
			
}
export const secretarioForm = new SecretarioFormService();