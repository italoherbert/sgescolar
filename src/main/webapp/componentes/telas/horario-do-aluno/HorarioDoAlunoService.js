
import {sistema} from '../../../sistema/Sistema.js';

import HorarioDoAlunoComponent from './HorarioDoAlunoComponent.js';

export default class HorarioDoAlunoService {			
		
	constructor() {
		this.component = new HorarioDoAlunoComponent();
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
export const horarioDoAluno = new HorarioDoAlunoService(); 