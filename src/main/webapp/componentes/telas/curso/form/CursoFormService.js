
import {sistema} from '../../../../sistema/Sistema.js';

import CursoFormComponent from './CursoFormComponent.js';

export default class CursoFormService {
										
	constructor() {
		this.component = new CursoFormComponent( 'curso_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			cursoId : this.params.cursoId,
			op : this.params.op			
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
						
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/curso/atualiza/"+this.params.cursoId;
		} else {
			let escolaId = this.component.getFieldValue( 'escola' );
			
			metodo = "POST";
			url = "/api/curso/registra/"+escolaId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Curso salvo com êxito.' );																
				instance.component.setFieldValue( 'descricao', '' );
				instance.component.setFieldValue( 'carga_horaria', '' );
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraCursosTela() {
		sistema.carregaPagina( 'curso-tela' );
	}
			
}
export const cursoForm = new CursoFormService();