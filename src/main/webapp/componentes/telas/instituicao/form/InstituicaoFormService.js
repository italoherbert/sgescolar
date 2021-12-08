
import {sistema} from '../../../../sistema/Sistema.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import InstituicaoFormComponent from './InstituicaoFormComponent.js';

export default class InstituicaoFormService {
										
	constructor() {
		this.component = new InstituicaoFormComponent( 'instituicao_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			instituicaoId : this.params.instituicaoId,
			op : this.params.op
		} );		
		this.component.carregaHTML();																	
	}
					
	salva() {								
		this.component.limpaMensagem();
		
		let url;
		let metodo;
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = '/api/instituicao/atualiza/'+this.params.instituicaoId;
		} else {
			metodo = 'POST';
			url = '/api/instituicao/registra'
		}
								
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {					
				perfilService.recarregaComponente();
				instance.component.limpaForm();
				instance.component.mostraInfo( 'Instituicao salva com êxito.' );																
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraTela() {
		sistema.carregaPagina( 'instituicao-tela' );
	}
			
}
export const instituicaoForm = new InstituicaoFormService();