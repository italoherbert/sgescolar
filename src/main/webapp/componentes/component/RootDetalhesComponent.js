
import DetalhesComponent from './DetalhesComponent.js';

export default class RootDetalhesComponent extends DetalhesComponent {
				
	constructor( msgELIDSufixo ) {
		super( '', '', '', msgELIDSufixo );
		super.carregarConteudoHTML = false;
	}
	
}
