
import {sistema} from '../../../../sistema/Sistema.js';

import SerieFormComponent from './SerieFormComponent.js';

export default class SerieFormService {
										
	constructor() {
		this.component = new SerieFormComponent( 'serie_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			serieId : this.params.serieId,
			op : this.params.op			
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
						
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/serie/atualiza/"+this.params.serieId;
		} else {
			let cursoId = document.serie_form.curso.value;
			
			metodo = "POST";
			url = "/api/serie/registra/"+cursoId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Serie salva com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraSeriesTela() {
		sistema.carregaPagina( 'serie-tela' );
	}
			
}
export const serieForm = new SerieFormService();