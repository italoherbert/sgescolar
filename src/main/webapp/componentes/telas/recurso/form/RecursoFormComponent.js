
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class RecursoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/recurso/get/"+this.globalParams.recursoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		}
	}
		
	getJSON() {
		return {
			nome : super.getFieldValue( 'nome' )
		}
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'nome', dados.nome );
	}	
	
	
	limpaForm() {
		super.setFieldValue( 'nome', "" );		
	}	
				
}
