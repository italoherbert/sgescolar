
import * as elutil from '../../../../sistema/util/elutil.js';

import {sistema} from '../../../../sistema/Sistema.js';

export default class MenuLateralService {
	
	onCarregado() {				
		let itensParaEsconder;
		
		let perfil = sistema.globalVars.perfil.perfil;
		if ( perfil === 'ADMIN' ) {
			itensParaEsconder = [ 'aluno-tela', 'professor-tela' ];			
		} else {
			itensParaEsconder = [ 'usuario-grupo-tela', 'recurso-tela', 'configuracoes-tela' ];
		}
		
		if ( perfil === 'PROFESSOR' || perfil === 'ALUNO' ) {
			itensParaEsconder.push( 'secretario-tela', 'professor-tela' );			
		}
		
		if ( perfil === 'ALUNO' ) {
			itensParaEsconder.push( 'escola-tela', 'pessoa-tela' );
		}
		
		for( let i = 0; i < itensParaEsconder.length; i++ )
			elutil.hide( itensParaEsconder[ i ] + '-mi-el' );				
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
		
		this.configuraEfeitoOpcaoAtiva( 'pessoa-submenu-el' );
	}
	
	paraPagina( compId ) {
		sistema.carregaPagina( compId );						
		
		this.configuraEfeitoOpcaoAtiva( compId + "-mi-el" );
		
		appLayout.mostraOuEscondePainelLateral();	
	}	
	
	configuraEfeitoOpcaoAtiva( elid ) {
		let menuPainel = document.getElementById( 'menu-lateral-painel' );
		let opcoes = menuPainel.querySelectorAll( "a, ul" );
		
		for( let i = 0; i < opcoes.length; i++ ) {
			opcoes[ i ].classList.remove( 'menu-lateral-opcao-ativa' );
			
			let li = opcoes[ i ].querySelector( 'li' );
			if ( li !== undefined && li !== null )
				li.classList.remove( 'menu-lateral-opcao-ativa' );
				
			if ( elid === opcoes[ i ].id ) {				
				if ( li !== undefined && li !== null ) {
					li.classList.add( 'menu-lateral-opcao-ativa' );
				} else {
					opcoes[ i ].classList.add( 'menu-lateral-opcao-ativa' );				
				}
			}						
		}			
	}
	
	paraTelaUsuarios() {
		sistema.carregaPagina( 'usuario-tela' );
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