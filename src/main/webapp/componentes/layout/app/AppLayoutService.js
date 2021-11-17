
import {sistema} from '../../../sistema/Sistema.js';

export default class AppLayoutService {
		
	onCarregado() {						
		sistema.carregaComponente( 'menu-lateral', 'menu-lateral' );
		sistema.carregaComponente( 'menu-nav', 'menu-nav' );
		
		this.paraInicial();					    
	}	
	
	mostraOuEscondePainelLateral( event ) {
		if ( event !== undefined && event !== null )
			event.preventDefault();
		
        document.body.querySelector( "#painel-lateral" ).classList.toggle( 'esconder-painel-lateral' );        
	}				

	paraInicial() {
		sistema.carregaPagina( 'anoletivo-form' );
		//sistema.carregaPagina( 'inicial', { nome : sistema.globalVars.usuario.username } );
	}

}
export const appLayout = new AppLayoutService();