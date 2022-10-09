
import {sistema} from '../../../sistema/Sistema.js';

import ListaFrequenciaComponent from './ListaFrequenciaComponent.js';

export default class ListaFrequenciaService {
										
	constructor() {
		this.component = new ListaFrequenciaComponent(); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			listaFrequenciaId : this.params.listaFrequenciaId,
			op : this.params.op			
		} );
		
		this.component.carregaHTML();																	
	}
				
	carrega() {
		this.component.limpaMensagem();
		
		this.component.buscaEOuCarrega();
	}			
					
	salva() {													
		this.component.limpaMensagem();
						
		const instance = this;
		sistema.ajax( 'POST', "/api/lista-frequencia/salva/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Lista de frequencia salva com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
				
}
export const listaFrequencia = new ListaFrequenciaService();