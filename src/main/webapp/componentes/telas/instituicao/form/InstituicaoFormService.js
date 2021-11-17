
import {sistema} from '../../../../sistema/Sistema.js';

import InstituicaoFormComponent from './InstituicaoFormComponent.js';

export default class InstituicaoFormService {
										
	constructor() {
		this.component = new InstituicaoFormComponent( 'instituicao_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {} );		
		this.component.carregaHTML();																	
	}
					
	salva() {								
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( 'POST', '/api/instituicao/salva', {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Instituicao salva com êxito.' );																
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraInstituicaoDetalhes() {
		sistema.carregaPagina( 'instituicao-detalhes' );
	}
			
}
export const instituicaoForm = new InstituicaoFormService();