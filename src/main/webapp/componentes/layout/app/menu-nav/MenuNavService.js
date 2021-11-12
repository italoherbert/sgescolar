
import * as elutil from '../../../../sistema/util/elutil.js';
import {sistema} from '../../../../sistema/Sistema.js';

import {loginForm} from '../../../telas/login/form/LoginFormService.js';

export default class MenuNavService {
	
	onCarregado() {
		let perfil = sistema.globalVars.perfil.perfil;
		if ( perfil === 'ADMIN' ) {
			elutil.hide( 'perfil-bmi-el' );
			elutil.hide( 'bmi-divider-el' );
		}
	}
	
	sair() {
		loginForm.logoff();
	}
	
	paraEditarPerfil() {
		let perfil = sistema.globalVars.perfil.perfil;
		let entidadeId = sistema.globalVars.perfil.entidadeId;
		
		if ( perfil === 'DIRETOR' || perfil === 'SECRETARIO' ) {
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