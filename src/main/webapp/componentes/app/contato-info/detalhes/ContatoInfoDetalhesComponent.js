
import {sistema} from '../../../../sistema/Sistema.js';

import Component from '../../../component/Component.js';

export default class ContatoInfoDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'contato-info-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {				
		sistema.carregaComponente( 'field', super.getELID( 'telefone_residencial' ), { 
			rotulo : "Telefone Residencial:", valor : dados.telefoneResidencial 
		} );
		sistema.carregaComponente( 'field', super.getELID( 'telefone_celular' ), { 
			rotulo : "Telefone celular:", valor : dados.telefoneCelular 
		} );
		sistema.carregaComponente( 'field', super.getELID( 'email' ), { 
			rotulo : "E-Mail:", valor : dados.email 
		} );
	}	
	
}