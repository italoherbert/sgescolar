
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

export default class CarregaSelectsUtil {
		
	carregaEscolasSelect( params ) {
		sistema.ajax( "GET", "/api/escola/lista", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
																
				let textos = [];
				let valores = [];
				for( let i = 0; i < dados.length; i++ ) {
					textos.push( dados[ i ].nome );
					valores.push( dados[ i ].id );
				}
						
				let select_el = document.getElementById( params.elid );
				select_el.onchange = params.onchange; 
																
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : valores,
					textos : textos, 
					defaultOption : { texto : 'Selecione a escola', valor : '0' } 
				} );													
			}
		} );
	}
	
	carregaAnosLetivosSelect( escolaId, params ) {		
		sistema.ajax( "GET", '/api/anoletivo/lista/'+escolaId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let textos = [];
				let valores = [];
				for( let i = 0; i < dados.length; i++ ) {
					textos.push( dados[ i ].ano );
					valores.push( dados[ i ].id );
				}
						
				let select_el = document.getElementById( params.elid );
				select_el.onchange = params.onchange; 
																
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : valores,
					textos : textos, 
					defaultOption : { texto : 'Selecione o ano', valor : '0' } 
				} );
			}
		} );	
	}
		
}
export const carregaSelectsUtil = new CarregaSelectsUtil();