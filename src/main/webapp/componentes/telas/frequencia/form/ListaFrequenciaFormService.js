
import {sistema} from '../../../../sistema/Sistema.js';

import ListaFrequenciaFormComponent from './ListaFrequenciaFormComponent.js';

export default class ListaFrequenciaFormService {
										
	constructor() {
		this.component = new ListaFrequenciaFormComponent( 'frequencia_form' ); 
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
		
		let turmaId = this.component.getFieldValue( 'turma' );

		if ( turmaId === undefined || turmaId === null || turmaId === '' || turmaId === '-1' ) {
			this.component.mostraErro( 'Selecione a turma primeiro.' );
			return;
		}
				
		const instance = this;
		sistema.ajax( "GET", "/api/matricula/lista/porturma/"+turmaId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.component.carregaMatriculas( dados );						
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}			
					
	salva() {													
		this.component.limpaMensagem();
		
		let turmaId = this.component.getFieldValue( 'turma' );
				
		const instance = this;
		sistema.ajax( 'POST', "/api/lista-frequencia/salva/"+turmaId, {
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
	
	paraTela() {
		sistema.carregaPagina( 'lista-frequencia-tela' );
	}
			
}
export const listaFrequenciaForm = new ListaFrequenciaFormService();