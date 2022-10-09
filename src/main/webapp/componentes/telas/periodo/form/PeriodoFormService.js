import {sistema} from "../../../../sistema/Sistema.js";

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import PeriodoFormComponent from './PeriodoFormComponent.js';

export default class PeriodoFormService {

	constructor() {
		this.formComponent = new PeriodoFormComponent();
	}

	onCarregado() {		
		this.formComponent.configura( {
			periodoId : this.params.periodoId,
			op : this.params.op
		} );
		this.formComponent.carregaHTML();			
	}
			
	registra() {
		this.formComponent.limpaMensagem();
		
		let anoLetivoId = perfilService.getAnoLetivoID();
		
		if ( isNaN( parseInt( anoLetivoId ) ) === true ) {
			this.formComponent.mostraErro( 'È necessário selecionar o ano letivo antes de salvar o período.' );
			return;
		}
		let url;
		let metodo;
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = '/api/periodo/atualiza/'+this.params.periodoId;
		} else {
			metodo = 'POST';
			url = '/api/periodo/registra'
		}
								
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( this.formComponent.getJSON() ),
			sucesso : ( resposta ) => {
				instance.formComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Periodo salvo com sucesso!' );
			},
			erro : ( msg ) => {
				instance.formComponent.mostraErro( msg );
			}
		} );
	}
	
	paraTela() {
		sistema.carregaPagina( 'periodo-tela' );
	}

}
export const periodoForm = new PeriodoFormService();