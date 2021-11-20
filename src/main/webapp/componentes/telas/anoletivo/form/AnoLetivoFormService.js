
import {conversor} from '../../../../sistema/util/Conversor.js';

import CalendarioComponent from '../../../component/calendario/CalendarioComponent.js';

import AnoLetivoFormComponent from './AnoLetivoComponent.js';

export default class AnoLetivoFormService {
	
	constructor() {
		this.anoLetivoFormComponent = new AnoLetivoFormComponent( 'anoletivo_form' );
		this.calendarioComponent = new CalendarioComponent( '', 'calendario-el' );
	}
	
	onCarregado() {
		this.anoLetivoFormComponent.configura( {} );
		
		this.calendarioComponent.configura( {
			ano : 2022,
			feriados : [
				conversor.toDate( '01/01/2022' ),
				conversor.toDate( '12/02/2022' ),
				conversor.toDate( '13/02/2022' ),
				conversor.toDate( '14/02/2022' ),
				conversor.toDate( '15/02/2022' ),
				conversor.toDate( '16/02/2022' )
			]
		} );
				
		this.anoLetivoFormComponent.carregaHTML();
		this.calendarioComponent.carregaHTML();
	}	
	
}
export const anoLetivoForm = new AnoLetivoFormService();