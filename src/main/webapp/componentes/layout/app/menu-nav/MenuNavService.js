
import * as elutil from '../../../../sistema/util/elutil.js';
import {sistema} from '../../../../sistema/Sistema.js';

export default class MenuNavService {
			
	onCarregado() {
		if ( sistema.globalVars.perfil.name === 'RAIZ' ) {			
			elutil.hide( 'perfil-bmi-el' );
		}
	}
	
	sair() {
		sistema.globalVars['logado'] = false;
		sistema.carregaLayout( 'login-layout' );
	}
	
	paraEditarPerfil() {
		let perfil = sistema.globalVars.perfil.name;
		let entidadeId = sistema.globalVars.entidadeId;
				
		if ( perfil === 'ADMIN' ) {
			sistema.carregaPagina( "administrador-form", { administradorId : entidadeId, op : "editar", titulo : "Edição de perfil"} )			
		} else if ( perfil === 'SECRETARIO' ) {
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