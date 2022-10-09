
import {sistema} from '../../sistema/Sistema.js';

import Component from './Component.js';

export default class DetalhesComponent extends Component {
				
	constructor( prefixo, compId, compELIDSufixo, msgELIDSufixo ) {
		super( prefixo, compId, compELIDSufixo, msgELIDSufixo );
	}			
			
	setHTMLCampoValor( campo, rotulo, valor ) {
		let v = ( valor === undefined || valor === null || valor === '' ? '' : valor );
		sistema.carregaComponente( 'campo', super.getELID( campo ), { rotulo : rotulo, valor : v } );		
	}
			
}