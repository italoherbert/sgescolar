
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
			selectService.carregaInstituicoesSelect( 'instituicoes_select', {
				onchange : () => {
					let instituicaoId = instance.getFieldValue( 'instituicao' );
					selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', { 
						onchange : () => {
							let escolaId = instance.getFieldValue( 'escola' );
							selectService.carregaCursosSelect( escolaId, 'cursos_select' );
						}
					} );	
				}
			} );			
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' )
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.curso.instituicaoId );
				selectService.carregaEscolasSelect( dados.curso.instituicaoId, 'escolas_select', {
					onload : () => { 
						instance.setFieldValue( 'escola', dados.curso.escolaId );				
						selectService.carregaCursosSelect( dados.curso.escolaId, 'cursos_select', { 
							onload : () => {
								instance.setFieldValue( 'curso', dados.curso.id );		
							} 
						} );
					}
				} );	
			}
		} );		
		
		super.setFieldValue( 'descricao', dados.descricao );
	}	
		
	limpaForm() {
		super.setFieldValue( 'instituicao', '0' );
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'descricao', "" );		
	}		
}
