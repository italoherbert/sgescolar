
import {sistema} from '../../../../sistema/Sistema.js';

import Component from '../../Component.js';

export default class UsuarioDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'usuario-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {				
		sistema.carregaComponente( 'field', super.getELID( 'username' ), { rotulo : "Nome de usuário:", valor : dados.username } );
		sistema.carregaComponente( 'field', super.getELID( 'perfil' ), { rotulo : "Perfil:", valor : dados.grupo.perfil } );
	}	
	
}