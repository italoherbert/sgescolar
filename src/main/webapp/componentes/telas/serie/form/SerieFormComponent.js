
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class SerieFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/serie/get/"+this.globalParams.serieId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		} else {
			selectService.carregaEscolasSelect( 'escolas_select', { 
				onchange : () => {
					let escolaId = instance.getFieldValue( 'escola' );
					selectService.carregaCursosSelect( escolaId, 'cursos_select' );
				}
			} );	
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			grau : super.getFieldValue( 'grau' ),
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;
		selectService.carregaEscolasSelect( 'escolas_select', {
			onload : () => { 
				instance.setFieldValue( 'escola', dados.escolaId );				
				selectService.carregaCursosSelect( dados.escolaId, 'cursos_select', { 
					onload : () => {
						instance.setFieldValue( 'curso', dados.cursoId );		
					} 
				} );
			}
		} );
		
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'grau', dados.grau );
	}	
		
	limpaForm() {
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'descricao', "" );		
		super.setFieldValue( 'grau', "" );		
	}		
}
