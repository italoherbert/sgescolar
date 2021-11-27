
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class TurmaDisciplinaListagemFormComponent extends RootFormComponent {
			
	onChangeTurma = () => {};	
										
	constructor() {
		super( 'listagem_turma_disciplina_form', 'lista-mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		const instance = this;
		selectService.carregaEscolasSelect( 'escolas_select', { 
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
										if ( typeof( instance.onChangeTurma ) === 'function' )
											instance.onChangeTurma( this );	
									}
								} );								
							}
						} );
					}
				} );
			}
		} );						
	}
			
	limpaForm() {
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'serie', "0" );		
		super.setFieldValue( 'turma', "0" );		
	}		
}
