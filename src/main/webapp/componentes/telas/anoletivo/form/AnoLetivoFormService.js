
import {sistema} from '../../../../sistema/Sistema.js';

//import CalendarioComponent from '../../../component/calendario/CalendarioComponent.js';

import AnoLetivoFormComponent from './AnoLetivoFormComponent.js';

export default class AnoLetivoFormService {
	
	constructor() {
		this.component = new AnoLetivoFormComponent( 'anoletivo_form' );
		//this.calendarioComponent = new CalendarioComponent( '', 'calendario-el' );
	}
	
	onCarregado() {
		this.component.configura( {
			anoLetivoId : this.params.anoLetivoId,
			op : this.params.op
		} );
		
		/*
		this.calendarioComponent.configura( {
			ano : 2022,
			feriados : [
				conversor.toDate( '01/01/2022' ),
				conversor.toDate( '12/02/2022' ),
				conversor.toDate( '13/02/2022' ),
				conversor.toDate( '14/02/2022' ),
				conversor.toDate( '15/02/2022' ),
				conversor.toDate( '16/02/2022' )
			]
		} );
		*/		
		this.component.carregaHTML();
		//this.calendarioComponent.carregaHTML();
	}	
	
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/anoletivo/atualiza/"+this.params.anoLetivoId;
		} else {
			let escolaId = this.component.getFieldValue( 'escola' ); 
			
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