
import {sistema} from '../../../../sistema/Sistema.js';

import {loginForm} from '../../../doc/login/form/LoginFormService.js';

export default class MenuNavService {
	
	sair() {
		loginForm.logoff();
	}
	
	paraEditarPerfil() {
		let perfil = sistema.globalVars.perfil.perfil;
		let entidadeId = sistema.globalVars.perfil.entidadeId;
		
		if ( perfil === 'ADMIN' ) {
			
		} else if ( perfil === 'DIRETOR' || perfil === 'SECRETARIO' ) {
			sistema.carregaPagina( "secretario-form", { secretarioId : entidadeId, op : "editar", titulo : "Edição de perfil"} )
		} else if ( perfil === 'PROFESSOR' ) {
			sistema.carregaPagina( "professor-form", { professorId : entidadeId, op : "editar", titulo : "Edição de perfil"} )			
		} else if ( perfil === 'ALUNO' ) {
			sistema.carregaPagina( "aluno-form", { alunoId : entidadeId, op : "editar", titulo : "Edição de perfil"} )			
		}		
	}
	
	paraInicial() {
		sistema.carregaPagina( 'inicial', { nome : sistema.globalVars.usuarioLogado.username } );
	}
	
}
export const menuNav = new MenuNavService();