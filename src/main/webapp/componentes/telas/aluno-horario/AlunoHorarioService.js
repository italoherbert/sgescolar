
import {sistema} from '../../../sistema/Sistema.js';

import AlunoHorarioComponent from './AlunoHorarioComponent.js';

export default class AlunoHorarioService {			
		
	constructor() {
		this.component = new AlunoHorarioComponent();
	}	
		
	onCarregado() {
		this.component.configura( {} );	
		
		this.component.carregaHTML();			
	}
	
	carregaHorario() {
		let matriculaId = this.component.getFieldValue( 'matricula' );
		
		const instance = this;
		sistema.ajax( 'GET', '/api/horario/lista/pormat/'+matriculaId, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				instance.component.carregaJSON( dados );
			},
			erro : ( msg ) => {
				instance.component.mostraErro( msg );
			}
		} );
	}
			
}
export const alunoHorario = new AlunoHorarioService(); 