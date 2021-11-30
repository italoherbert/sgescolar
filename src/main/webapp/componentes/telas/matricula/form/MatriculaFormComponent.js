
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class MatriculaFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'matricula_form', 'form-mensagem-el' );						
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
										selectService.carregaTurmasPorSerieSelect( serieId, 'turmas_select' );	
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
		super.setFieldValue( 'instituicao', "0" );
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'curso', "0" );		
		super.setFieldValue( 'serie', "0" );		
		super.setFieldValue( 'turma', "0" );	
	}		
		
}
