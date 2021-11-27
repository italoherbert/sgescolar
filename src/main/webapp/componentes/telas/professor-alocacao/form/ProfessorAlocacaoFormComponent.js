
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class ProfessorAlocacaoFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'professor_alocacao_form', 'form-professor-el' );						
	}			
						
	carregouHTMLCompleto() {
		super.limpaTudo();
								
		const instance = this;
		selectService.carregaEscolasSelect( super.getELID( 'escolas_select' ), { 
			onchange : () => {
				let escolaId = instance.getFieldValue( 'escola' );
				selectService.carregaCursosSelect( escolaId, super.getELID( 'cursos_select' ), {
					onchange : () => {
						let cursoId = instance.getFieldValue( 'curso' );
						selectService.carregaSeriesSelect( cursoId, super.getELID( 'series_select' ), {
							onchange : () => {
								let serieId = instance.getFieldValue( 'serie' );
								selectService.carregaDisciplinasSelect( serieId, super.getELID( 'disciplinas_select' ) );
								selectService.carregaTurmasPorSerieSelect( serieId, super.getELID( 'turmas_select' ) );	
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
