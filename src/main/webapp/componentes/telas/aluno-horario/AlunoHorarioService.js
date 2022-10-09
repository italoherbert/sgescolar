
import {sistema} from '../../../sistema/Sistema.js';

import AlunoHorarioComponent from './AlunoHorarioComponent.js';

export default class AlunoHorarioService {			
		
	constructor() {
		this.component = new AlunoHorarioComponent();
	}	
		
	onCarregado() {
		this.component.configura( {} );			
		this.component.carregaHTML();	
		
		let alunoId = sistema.globalVars.entidadeId;		
		
		const instance = this;
		sistema.ajax( 'GET', '/api/horario/lista/pormat/atual/'+alunoId, {
			sucesso : (resposta) => {
				let dados = JSON.parse( resposta );		
				instance.component.carregaJSON( dados );
			},
			erro : ( msg ) => {
				instance.component.mostraErro( msg );
			}
		} )			
	}
		
}
export const alunoHorario = new AlunoHorarioService(); 