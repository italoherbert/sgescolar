
import {sistema} from '../../../../sistema/Sistema.js';

import Component from '../../Component.js';

export default class ContatoInfoDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'contato-info-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {				
		sistema.carregaComponente( 'campo', super.getELID( 'telefone_fixo' ), { 
			rotulo : "Telefone Fixo:", valor : dados.telefoneFixo 
		} );
		sistema.carregaComponente( 'campo', super.getELID( 'telefone_celular' ), { 
			rotulo : "Telefone celular:", valor : dados.telefoneCelular 
		} );
		sistema.carregaComponente( 'campo', super.getELID( 'email' ), { 
			rotulo : "E-Mail:", valor : dados.email 
		} );
	}	
	
}