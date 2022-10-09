
import RootFormComponent from '../../../component/RootFormComponent.js';

export default class SecretarioTelaComponent extends RootFormComponent {
			
	onChangeTurma = () => {};	
										
	constructor() {
		super( 'secretario_filtro_form', 'mensagem-el' );				
	}			
					
	getJSON() {
		return {
			nomeIni : super.getFieldValue( 'nomeini' )
		}
	}		
			
	limpaForm() {
		super.setFieldValue( 'nomeini', '' );		
	}		
}
