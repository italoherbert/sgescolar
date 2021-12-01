
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class TurmaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/turma/get/"+this.globalParams.turmaId, {
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
							selectService.carregaCursosSelect( escolaId, 'cursos_select', {
								onchange : () => {
									let cursoId = instance.getFieldValue( 'curso' );
									selectService.carregaSeriesSelect( cursoId, 'series_select' );
								}
							} );
							selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select' );
						}
					} );		
				}
			} );	
			
			selectService.carregaTurnosSelect( 'turnos_select' );		
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			turno : super.getFieldValue( 'turno' )
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;	
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.serie.curso.instituicaoId );
				selectService.carregaEscolasSelect( dados.serie.curso.instituicaoId, 'escolas_select', {
					onload : () => { 
						instance.setFieldValue( 'escola', dados.serie.curso.escolaId );				
						selectService.carregaAnosLetivosSelect( dados.serie.curso.escolaId, 'anosletivos_select', { 
							onload : () => {
								instance.setFieldValue( 'anoletivo', dados.anoLetivoId );		
							} 
						} );
						selectService.carregaCursosSelect( dados.serie.curso.escolaId, 'cursos_select', { 
							onload : () => {
								instance.setFieldValue( 'curso', dados.serie.curso.id );	
								selectService.carregaSeriesSelect( dados.serie.curso.id, 'series_select', { 
									onload : () => {
										instance.setFieldValue( 'serie', dados.serie.id );
									}
								} );	
							} 
						} );
					}
				} );		
			}
		} );			
		
		selectService.carregaTurnosSelect( 'turnos_select', {
			onload : () => {
				instance.setFieldValue( 'turno', dados.turno.name );
			}
		} );
		
		super.setFieldValue( 'descricao', dados.descricao );
	}	
		
	limpaForm() {
		super.setFieldValue( 'instituicao', '0' );
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'anoletivo', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'serie', "0" );		
		super.setFieldValue( 'descricao', "" );		
	}		
}
