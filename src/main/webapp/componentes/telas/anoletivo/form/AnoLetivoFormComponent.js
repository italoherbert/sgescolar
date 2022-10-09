
import {sistema} from '../../../../sistema/Sistema.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class AnoLetivoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem-el' );						
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
		}			
	}
		
	getJSON() {
		return {
			ano : super.getFieldValue( 'ano' )
		}
	}	
		
	carregaJSON( dados ) {		
		perfilService.setInstituicaoID( dados.instituicaoId );
		perfilService.setEscolaID( dados.escolaId );
		
		super.setFieldValue( 'ano', dados.ano );		
	}	
		
}
