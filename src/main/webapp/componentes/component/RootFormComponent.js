
import FormComponent from './FormComponent.js';

export default class RootFormComponent extends FormComponent {
				
	constructor( formNome, msgELIDSufixo ) {
		super( formNome, '', '', '', msgELIDSufixo );
		super.carregarConteudoHTML = false;
	}
	
}
