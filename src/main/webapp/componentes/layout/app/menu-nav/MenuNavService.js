
import * as elutil from '../../../../sistema/util/elutil.js';
import {sistema} from '../../../../sistema/Sistema.js';

import {loginForm} from '../../../telas/login/form/LoginFormService.js';

export default class MenuNavService {
		
	permissoesMap = {
		'perfil-bmi-el' : [ 'perfilWRITE' ],
	}
	
	onCarregado() {
		Object.keys( this.permissoesMap ).forEach( (elid) => {
			if ( sistema.verificaSeTemPermissao( this.permissoesMap[ elid ] ) === false )
				elutil.hide( elid );				
		} );
	}
	
	sair() {
		loginForm.logoff();
	}
	
	paraEditarPerfil() {
		let perfil = sistema.globalVars.perfil.name;
		let entidadeId = sistema.globalVars.entidadeId;
				
		if ( perfil === 'SECRETARIO' ) {
			sistema.carregaPagina( "secretario-form", { secretarioId : entidadeId, op : "editar", titulo : "Edição de perfil"} )
		} else if ( perfil === 'PROFESSOR' ) {
			sistema.carregaPagina( "professor-form", { professorId : entidadeId, op : "editar", titulo : "Edição de perfil"} )			
		} else if ( perfil === 'ALUNO' ) {
			sistema.carregaPagina( "aluno-form", { alunoId : entidadeId, op : "editar", titulo : "Edição de perfil"} )			
		}		
	}
	
	paraInicial() {
		sistema.carregaPagina( 'inicial', { nome : sistema.globalVars.usuario.username } );
	}
	
}
export const menuNav = new MenuNavService();