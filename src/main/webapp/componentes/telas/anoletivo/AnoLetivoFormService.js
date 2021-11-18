
import CalendarioComponent from '../../component/calendario/CalendarioComponent.js';

export default class AnoLetivoFormService {
	
	constructor() {
		this.calendarioComponent = new CalendarioComponent( '', 'calendario-el' );
	}
	
	onCarregado() {
		this.calendarioComponent.configura( {
			ano: 2022, mes : 1, feriados : [ 5, 8, 11, 20, 25 ], hoje : 5
		} );
		
		this.calendarioComponent.carregaHTML();
	}	
	
}
export const anoLetivoForm = new AnoLetivoFormService();