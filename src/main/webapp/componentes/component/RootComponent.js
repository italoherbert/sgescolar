
import Component from './Component.js';

export default class RootComponent extends Component {
				
	constructor( msgElementoSufixo ) {
		super( '', '', '', msgElementoSufixo );
		super.carregarConteudoHTML = false;
	}
	
}