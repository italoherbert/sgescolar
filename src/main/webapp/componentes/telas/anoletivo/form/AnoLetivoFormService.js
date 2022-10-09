
import {sistema} from '../../../../sistema/Sistema.js';

import AnoLetivoFormComponent from './AnoLetivoFormComponent.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

export default class AnoLetivoFormService {
	
	constructor() {
		this.component = new AnoLetivoFormComponent( 'anoletivo_form' );
	}
	
	onCarregado() {
		this.component.configura( {
			anoLetivoId : this.params.anoLetivoId,
			op : this.params.op
		} );
		
		
		this.component.carregaHTML();
	}	
	
	salva() {						
		let url;
		let metodo;
		
		let escolaId = perfilService.getEscolaID();
				
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/anoletivo/atualiza/"+this.params.anoLetivoId;
		} else {						 
			metodo = "POST";
			url = "/api/anoletivo/registra/"+escolaId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Ano letivo salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraAnoLetivoTela() {
		sistema.carregaPagina( 'anoletivo-tela' );
	}
	
}
export const anoletivoForm = new AnoLetivoFormService();