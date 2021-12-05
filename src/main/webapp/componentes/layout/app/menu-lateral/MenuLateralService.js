
import * as elutil from '../../../../sistema/util/elutil.js';

import * as ajax from '../../../../sistema/util/ajax.js';

import {sistema} from '../../../../sistema/Sistema.js';

export default class MenuLateralService {
			
	onCarregado() {		
		let basedir = '/componentes/layout/app/menu-lateral/config';
		
		let menuConfigFile = '/menu.json';				
		switch ( sistema.globalVars.perfil.name ) {
			case 'RAIZ':
				menuConfigFile = '/raiz-menu.json';		
				break;
			case 'ADMIN':
				menuConfigFile = '/admin-menu.json';		
				break;
			case 'SECRETARIO':
				menuConfigFile = '/secretario-menu.json';		
				break;
			case 'PROFESSOR':
				menuConfigFile = '/professor-menu.json';		
				break;
			case 'ALUNO':
				menuConfigFile = '/aluno-menu.json';		
				break;
		}
		
		ajax.ajaxGetRecurso( basedir + "/menu.json", {
			sucesso : ( xmlhttp ) => {
				let dados = JSON.parse( xmlhttp.responseText );									
				
				ajax.ajaxGetRecurso( basedir + menuConfigFile, {
					sucesso : ( xmlhttp2 ) => {
						let dados2 = JSON.parse( xmlhttp2.responseText );
						for( let i = 0; i < dados.length; i++ ) {
							let achou = false;
							for( let j = 0; achou === false && j < dados2.length; j++ )
								if ( dados[ i ] === dados2[ j ] )
									achou = true;

							if ( achou === false )
								elutil.hide( dados[ i ] );
						}
					}
				} );
				
			}
		} );																
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
						
		this.configuraEfeitoOpcaoAtiva( prefixo + 'submenu_op' );
	}
	
}
export const menuLateral = new MenuLateralService();