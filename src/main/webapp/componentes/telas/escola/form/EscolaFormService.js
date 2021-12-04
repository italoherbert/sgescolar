
import {sistema} from '../../../../sistema/Sistema.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import EscolaFormComponent from './EscolaFormComponent.js';

export default class EscolaFormService {
										
	constructor() {
		this.component = new EscolaFormComponent( 'escola_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			escolaId : this.params.escolaId,
			op : this.params.op			
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
		
		let instituicaoId = perfilService.getInstituicaoID();
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/escola/atualiza/"+this.params.escolaId;
		} else {			
			metodo = "POST";
			url = "/api/escola/registra/"+instituicaoId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				perfilService.recarregaComponente();
				instance.component.limpaTudo();
				instance.component.mostraInfo( 'Escola salva com êxito.' );																
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