
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class TurmaDisciplinaFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'turma_disciplina_form', 'form-mensagem-el' );				
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
								selectService.carregaDisciplinasSelect( serieId, 'disciplinas_select' );
								selectService.carregaTurmasPorSerieSelect( serieId, 'turmas_select' );	
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
		super.setFieldValue( 'disciplina', "0" );		
	}		
}