
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class DisciplinaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/disciplina/get/"+this.globalParams.disciplinaId, {
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
					selectService.carregaCursosSelect( escolaId, 'cursos_select', {
						onchange : () => {
							let cursoId = instance.getFieldValue( 'curso' );
							selectService.carregaSeriesSelect( cursoId, 'series_select' );
						}
					} );
				}
			} );	
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;
		selectService.carregaEscolasSelect( 'escolas_select', {
			onload : () => { 
				instance.setFieldValue( 'escola', dados.serie.curso.escolaId );				
				selectService.carregaCursosSelect( dados.serie.curso.escolaId, 'cursos_select', { 
					onload : () => {
						instance.setFieldValue( 'curso', dados.serie.curso.id );
						selectService.carregaSeriesSelect( dados.serie.curso.id, 'series_select', {
							onload : () => {
								instance.setFieldValue( 'serie', dados.serie.id );
							}
						} )		
					} 
				} );
			}
		} );
		
		super.setFieldValue( 'descricao', dados.descricao );
	}	
		
	limpaForm() {
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'serie', "0" );		
		super.setFieldValue( 'descricao', "" );		
	}		
}
