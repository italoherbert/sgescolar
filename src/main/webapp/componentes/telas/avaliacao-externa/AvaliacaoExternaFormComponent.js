
import {conversor} from '../../../sistema/util/Conversor.js';

import RootFormComponent from '../../component/RootFormComponent.js';

export default class AvaliacaoExternaFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'avaliacao_externa_form', 'mensagem-el' );		
	}			
						
	getJSON() {
		return {
			media : conversor.valorFloat( super.getFieldValue( 'media' ) ),
			peso : conversor.valorFloat( super.getFieldValue( 'peso' ) ),
		}
	}	
		
		
	limpaForm() {
		super.setFieldValue( 'media', '' );
		super.setFieldValue( 'peso', '' );
	}
										
}
