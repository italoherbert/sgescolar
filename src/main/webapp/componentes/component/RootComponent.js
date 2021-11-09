
import Component from './Component.js';

export default class RootComponent extends Component {
				
	constructor( msgELIDSufixo ) {
		super( '', '', '', msgELIDSufixo );
		super.carregarConteudoHTML = false;
	}
	
}