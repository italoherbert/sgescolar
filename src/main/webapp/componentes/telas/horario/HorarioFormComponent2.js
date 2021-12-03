
import {sistema} from '../../../sistema/Sistema.js';

import {selectService} from '../../service/SelectService.js';

import {perfilService} from '../../layout/app/perfil/PerfilService.js';

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
		
		let escolaId = perfilService.getEscolaID();
		if ( escolaId === '-1' ) {
			this.mostraErro( 'Escola não selecionada.' );
			return;	
		}		
				
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId !== '-1' ) {
			selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
				onload : () => {
					instance.setFieldValue( 'turma', perfilService.getTurmaID() );			
				}
			} );
		}
				
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
								
		perfilService.setInstituicaoID( dados.serie.curso.instituicaoId );
		perfilService.setEscolaID( dados.serie.curso.escolaId );					
								
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
		
		let disciplinasVinculadas = dados.disciplinasVinculadas;
		this.horarioFormComponent.carregaJSON( disciplinasVinculadas );				
	}	
		
	limpaForm() {	
		super.setFieldValue( 'curso', "-1" );		
		super.setFieldValue( 'serie', "-1" );		
		super.setFieldValue( 'turma', "-1" );		
		
		this.horarioFormComponent.novoTBody();
	}		
	
}
