
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

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
			const instance = this;
			selectService.carregaInstituicoesSelect( 'instituicoes_select', {
				onchange : () => {
					let instituicaoId = instance.getFieldValue( 'instituicao' ); 
					selectService.carregaEscolasSelect( instituicaoId, 'escolas_select' );
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
		
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.instituicaoId ); 
				selectService.carregaEscolasSelect( dados.instituicaoId, 'escolas_select', {
					onload : () => {
						instance.setFieldValue( 'escola', dados.escolaId );
					}
				} );
			} 
		} );
	}	
		
}
