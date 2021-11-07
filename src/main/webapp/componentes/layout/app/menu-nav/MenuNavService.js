
import {loginForm} from '../../../doc/login/form/LoginFormService.js';

export default class MenuNavService {
	
	sair() {
		loginForm.logoff();
	}
	
	paraInicial() {
		sistema.carregaPagina( 'inicial', { nome : sistema.globalVars.usuarioLogado.username } );
	}
	
}
export const menuNav = new MenuNavService();