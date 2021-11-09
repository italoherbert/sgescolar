
import {sistema} from '../../../sistema/Sistema.js';

export default class AppLayoutService {
		
	onCarregado() {						
		sistema.carregaComponente( 'menu-lateral', 'menu-lateral' );
		sistema.carregaComponente( 'menu-nav', 'menu-nav' );
		
		this.paraInicial();					    
	}					

	paraInicial() {
		sistema.carregaPagina( 'inicial', { nome : sistema.globalVars.usuarioLogado.username } );
	}

}
export const appLayout = new AppLayoutService();