
import {sistema} from '../../../sistema/Sistema.js';
import {loginForm} from '../../login/form/LoginForm.js';

export default class AppLayout {
		
	onCarregado() {				
		this.configuraMenu();
		
		let grupo = sistema.globalVars.usuarioLogado.grupo.perfil;
		if ( grupo === 'ADMIN' ) {
			sistema.carregaComponente( 'admin-menu', 'menu-lateral', { pessoa : { nome : 'Italo', telefones : [ '000', '111' ], endereco : { bairro : 'Centro' } } } );
		} else if ( grupo === 'SECRETARIO' ) {
			sistema.carregaComponente( 'secretario-menu', 'menu-lateral' );
		} else if ( grupo === 'PROFESSOR' ) {
			sistema.carregaComponente( 'professor-menu', 'menu-lateral' );
		} else if ( grupo === 'ALUNO' ) {
			sistema.carregaComponente( 'aluno-menu', 'menu-lateral' );
		}
		
		sistema.carregaComponente( 'navbar-menu', 'navbar-menu' );
		
		sistema.carregaPagina( 'aluno-tela' );						    
	}
	
	paraInicial() {
		sistema.carregaPagina( 'aluno-tela' );
	}
	
	sair() {
		loginForm.logoff();
	}
	
	configuraMenu() {				
		const sidebarToggle = document.body.querySelector('#sidebarToggle');
		sidebarToggle.addEventListener('click', event => {
	        event.preventDefault();
	        document.body.classList.toggle('sb-sidenav-toggled');
	        
	        const sidebarToggleIcon = document.getElementById('sidebar-toggle-icon');	
	        sidebarToggleIcon.classList.toggle("bi-chevron-double-left");	
			sidebarToggleIcon.classList.toggle("bi-chevron-double-right");	
	    });
	}

}
export const appLayout = new AppLayout();