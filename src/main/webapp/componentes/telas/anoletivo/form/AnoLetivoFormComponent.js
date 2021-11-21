
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class AnoLetivoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem-el' );						
	}			
	
	onConfigurado() {
		
	}
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		const instance = this;
		
		if ( this.globalParams.op === 'editar' ) {
			sistema.ajax( "GET", "/api/anoletivo/get/"+this.globalParams.anoLetivoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		} else {			
			sistema.ajax( "GET", "/api/escola/lista", {
				sucesso : ( resposta ) => {
					let dados = JSON.parse( resposta );
																	
					let textos = [];
					let valores = [];
					for( let i = 0; i < dados.length; i++ ) {
						textos.push( dados[ i ].nome );
						valores.push( dados[ i ].id );
					}
													
					document.getElementById( "escolas_select" ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
						valores : valores,
						textos : textos, 
						defaultOption : { texto : 'Selecione a escola', valor : '0' } 
					} );													
				}
			} );	
		}			
	}
		
	getJSON() {
		return {
			ano : super.getFieldValue( 'ano' )
		}
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'ano', dados.ano );
		
		document.getElementById( "escolas_select" ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
			valores : [ dados.escolaId ],
			textos : [ dados.escolaNome ], 
		} );
	}	
		
}
