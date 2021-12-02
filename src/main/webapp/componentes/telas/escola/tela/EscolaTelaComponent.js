
import RootFormComponent from '../../../component/RootFormComponent.js';

export default class EscolaTelaComponent extends RootFormComponent {
	
	onChangeEscola = () => {};
	
	constructor() {
		super( 'escola_filtro_form', 'mensagem-el' );
	}
			
	getJSON() {
		return {				
			nomeIni : super.getFieldValue( 'nomeini' )
		}
	}
		
}