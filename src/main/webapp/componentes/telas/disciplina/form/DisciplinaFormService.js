
import {sistema} from '../../../../sistema/Sistema.js';

import DisciplinaFormComponent from './DisciplinaFormComponent.js';

export default class DisciplinaFormService {
										
	constructor() {
		this.component = new DisciplinaFormComponent( 'disciplina_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			disciplinaId : this.params.disciplinaId,
			op : this.params.op			
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
						
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/disciplina/atualiza/"+this.params.disciplinaId;
		} else {
			let serieId = this.component.getFieldValue( 'serie' );
			
			metodo = "POST";
			url = "/api/disciplina/registra/"+serieId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Disciplina salva com êxito.' );
				instance.component.setFieldValue( 'descricao', '' );	
				instance.component.setFieldValue( 'sigla', '' );	
																			
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraTela() {
		sistema.carregaPagina( 'disciplina-tela' );
	}
			
}
export const disciplinaForm = new DisciplinaFormService();