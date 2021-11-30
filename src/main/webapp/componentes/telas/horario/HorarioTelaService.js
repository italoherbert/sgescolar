
import {sistema} from '../../../../sistema/Sistema.js';

import HorarioFormComponent2 from './HorarioFormComponent2.js';

export default class HorarioTelaService {
															
	constructor() {
		this.component = new HorarioFormComponent2( 'turma_horario_form' );
	}					
																
	onCarregado() {		
		this.component.configura( {} );		
		this.component.carregaHTML();											
	}
	
	carrega() {
		this.component.carregaForm();
	}
					
	salva() {						
		this.component.limpaMensagem();
				
		let turmaId = this.component.getFieldValue( 'turma' );		
				
		const instance = this;
		sistema.ajax( 'POST', '/api/horario/salva/'+turmaId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {
				instance.component.mostraInfo( 'Horario salvo com êxito.' );																
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
				
}
export const horarioTela = new HorarioTelaService();