
import {sistema} from '../../../../sistema/Sistema.js';

export default class MenuLateralService {
	
	onCarregado() {				
		const btn_el = document.body.querySelector( '#mostra-ou-esconde-painel-lateral-btn' );
		btn_el.addEventListener('click', event => {
	        event.preventDefault();
	        document.body.querySelector( "#painel-lateral" ).classList.toggle( 'esconder-painel-lateral' );
	        
	        const icon_el = document.getElementById( 'sidebar-toggle-icon' );	
	        icon_el.classList.toggle( "bi-chevron-double-left" );	
			icon_el.classList.toggle( "bi-chevron-double-right" );	
	    });
	}
	
	mostraOuEscondeSubmenu( prefixo ) {
		let mi_up_el = document.getElementById( prefixo + 'mi_up' );
		mi_up_el.classList.toggle( "d-inline-block" );
		mi_up_el.classList.toggle( "visible" );
		mi_up_el.classList.toggle( "d-none" );
		mi_up_el.classList.toggle( "hidden" );

		let mi_down_el = document.getElementById( prefixo + 'mi_down' );
		mi_down_el.classList.toggle( "d-inline-block" );
		mi_down_el.classList.toggle( "visible" );
		mi_down_el.classList.toggle( "d-none" );
		mi_down_el.classList.toggle( "hidden" );
		
		let submenu_el = document.getElementById( prefixo+"submenu" );
		submenu_el.classList.toggle( "d-block" );
		submenu_el.classList.toggle( "visible" );
		submenu_el.classList.toggle( "d-none" );
		submenu_el.classList.toggle( "hidden" );
	}
	
		
	paraTelaEscolas() {
		sistema.carregaPagina( 'escola-tela' );
	}
	
	paraTelaAlunos() {
		sistema.carregaPagina( 'aluno-tela' );
	}
	
	paraTelaProfessores() {
		sistema.carregaPagina( 'professor-tela' );
	}
	
	paraTelaSecretarios() {
		sistema.carregaPagina( 'secretario-tela' );
	}
	
}
export const menuLateral = new MenuLateralService();