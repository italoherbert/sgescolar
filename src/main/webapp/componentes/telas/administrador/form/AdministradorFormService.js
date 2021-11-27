
import {sistema} from '../../../../sistema/Sistema.js';

import AdministradorFormComponent from './AdministradorFormComponent.js';

export default class AdministradorFormService {
										
	constructor() {
		this.component = new AdministradorFormComponent( 'administrador_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			administradorId : this.params.administradorId,
			op : this.params.op,						
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/administrador/atualiza/"+this.params.administradorId;
		} else {
			let instituicaoId = this.component.getFieldValue( 'instituicao' );
			metodo = "POST";
			url = "/api/administrador/registra/"+instituicaoId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Administrador salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraAdministradoresTela() {
		sistema.carregaPagina( 'administrador-tela' );
	}
			
}
export const administradorForm = new AdministradorFormService();