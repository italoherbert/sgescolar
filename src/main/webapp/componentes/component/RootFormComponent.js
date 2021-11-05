
import FormComponent from './FormComponent.js';

export default class RootFormComponent extends FormComponent {
				
	constructor( formNome ) {
		super( formNome, '', '', '', '' );
		super.carregarConteudoHTML = false;
	}
	
}
