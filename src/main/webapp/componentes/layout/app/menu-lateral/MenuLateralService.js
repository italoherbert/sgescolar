
import * as elutil from '../../../../sistema/util/elutil.js';

import {sistema} from '../../../../sistema/Sistema.js';

export default class MenuLateralService {
	
	permissoesMap = {
		'instituicao-tela-mi-el' : [ 'instituicaoREAD' ],
		'escola-tela-mi-el' : [ 'escolaREAD' ],
		'curso-tela-mi-el' : [ 'cursoREAD' ],
		'serie-tela-mi-el' : [ 'serieREAD' ],
		'disciplina-tela-mi-el' : [ 'disciplinaREAD' ],
		'lista-frequencia-tela-mi-el' : [ 'listaFrequenciaREAD' ],
		'turma-tela-mi-el' : [ 'turmaREAD' ],
		'horario-tela-mi-el' : [ 'horarioREAD' ],
				
		'aluno-tela-mi-el' : [ 'alunoREAD', 'pessoaREAD' ],
		'professor-tela-mi-el' : [ 'professorREAD', 'pessoaREAD' ],
		'secretario-tela-mi-el' : [ 'secretarioREAD', 'pessoaREAD' ],
		'administrador-tela-mi-el' : [ 'administradorREAD', 'pessoaREAD' ],
				
		'feriado-tela-mi-el' : [ 'feriadoREAD', 'anoLetivoREAD' ],
		'periodo-tela-mi-el' : [ 'periodoREAD', 'anoLetivoREAD' ],
		
		'usuario-tela-mi-el' : [ 'usuarioREAD' ],
		'usuario-grupo-tela-mi-el' : [ 'usuarioGrupoREAD' ],
		'recurso-tela-mi-el' : [ 'recursoREAD' ],
		'configuracoes-tela-mi-el' : [ 'configuracoesREAD' ],
		'anoletivo-tela-mi-el' : [ 'anoLetivoREAD' ],
		
		'pessoa_submenu_op' : [ 'pessoaREAD' ],	
		'turma_submenu_op' : [ 'turmaREAD' ],
		'vinculo_submenu_op' : [ 'vinculoREAD' ],
		'anoletivo_submenu_op' : [ 'anoLetivoREAD' ],	
	};
	
	onCarregado() {		
		Object.keys( this.permissoesMap ).forEach( (elid) => {
			if ( sistema.verificaSeTemPermissao( this.permissoesMap[ elid ] ) === false )
				elutil.hide( elid );				
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