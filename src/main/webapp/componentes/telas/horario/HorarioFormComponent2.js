
import {sistema} from '../../../sistema/Sistema.js';

import {selectService} from '../../service/SelectService.js';

import RootFormComponent from '../../component/RootFormComponent.js';
import HorarioFormComponent from '../../component/horario/HorarioFormComponent.js';

export default class HorarioFormComponent2 extends RootFormComponent {
										
	onChangeTurma = () => {};		
	
	disciplinasVinculadas = [];							
										
	constructor() {
		super( 'turma_horario_form', 'mensagem-el' );				
		
		this.horarioFormComponent = new HorarioFormComponent( 'horario_form', '', 'horario-el', 'mensagem-el' );
		
		super.addFilho( this.horarioFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'instituicao' );
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', { 
					onchange : () => {
						let escolaId = instance.getFieldValue( 'escola' );
						selectService.carregaCursosSelect( escolaId, 'cursos_select', {
							onchange : () => {
								let cursoId = instance.getFieldValue( 'curso' );
								selectService.carregaSeriesSelect( cursoId, 'series_select', {
									onchange : () => {
										let serieId = instance.getFieldValue( 'serie' );
										selectService.carregaTurmasPorSerieSelect( serieId, 'turmas_select', {
											onchange : () => {
												instance.carregaForm();								
											}
										} )
									}
								} );
							}
						} );
					}
				} );		
			}
		} );								
	}
	
	carregaForm() {
		let turmaId = super.getFieldValue( 'turma' );
		
		const instance = this;
		sistema.ajax( "GET", "/api/turma/get/"+turmaId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaJSON( dados );						
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );
	}
				
	getJSON() {
		return this.horarioFormComponent.getJSON();
	}	
		
	carregaJSON( dados ) {
		const instance = this;	
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.serie.curso.instituicaoId );
				selectService.carregaEscolasSelect( dados.serie.curso.instituicaoId, 'escolas_select', {
					onload : () => { 						
						instance.setFieldValue( 'escola', dados.serie.curso.escolaId );	
						selectService.carregaCursosSelect( dados.serie.curso.escolaId, 'cursos_select', { 
							onload : () => {
								instance.setFieldValue( 'curso', dados.serie.curso.id );	
								selectService.carregaSeriesSelect( dados.serie.curso.id, 'series_select', { 
									onload : () => {
										instance.setFieldValue( 'serie', dados.serie.id );
										selectService.carregaTurmasPorSerieSelect( dados.serie.id, 'turmas_select', {
											onload : () => {
												instance.setFieldValue( 'turma', dados.id );
											}
										} );
									}
								} );	
							} 
						} );
					}
				} );		
			}
		} );			
		
		let disciplinasVinculadas = dados.disciplinasVinculadas;
		this.horarioFormComponent.carregaJSON( disciplinasVinculadas );				
	}	
		
	limpaForm() {
		super.setFieldValue( 'instituicao', '0' );
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'serie', "0" );		
		super.setFieldValue( 'turma', "0" );		
		
		this.horarioFormComponent.novoTBody();
	}		
	
}
