
import {sistema} from '../../../../sistema/Sistema.js';

import Component from '../../Component.js';

export default class UsuarioDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'usuario-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {				
		sistema.carregaComponente( 'campo', super.getELID( 'username' ), { rotulo : "Nome de usuário:", valor : dados.username } );
		sistema.carregaComponente( 'campo', super.getELID( 'perfil' ), { rotulo : "Perfil:", valor : dados.perfil } );
		
		let html = "";
		for( let i = 0; i < dados.grupos.length; i++ )
			html += dados.grupos[ i ].nome + ( i < dados.grupos.length-1 ? ", " : "" );	
		
		sistema.carregaComponente( 'campo', super.getELID( 'grupos' ), { rotulo : "Grupos de usuário:", valor : html } );
	}	
	
}