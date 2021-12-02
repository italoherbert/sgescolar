
import {sistema} from '../../../../sistema/Sistema.js';

import TurmaFormComponent from './TurmaFormComponent.js';

export default class TurmaFormService {
										
	constructor() {
		this.component = new TurmaFormComponent( 'turma_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			turmaId : this.params.turmaId,
			op : this.params.op			
		} );
		
		this.component.carregaHTML();																	
	}
					
	salva() {						
		let url;
		let metodo;
						
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/turma/atualiza/"+this.params.turmaId;
		} else {
			let serieId = this.component.getFieldValue( 'serie' );
			let anoLetivoId = this.component.getFieldValue( 'anoletivo' );
			
			metodo = "POST";
			url = "/api/turma/registra/"+serieId+"/"+anoLetivoId;
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Turma salva com êxito.' );																
				instance.component.setFieldValue( 'descricao', '' );
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraTurmasTela() {
		sistema.carregaPagina( 'turma-tela' );
	}
			
}
export const turmaForm = new TurmaFormService();