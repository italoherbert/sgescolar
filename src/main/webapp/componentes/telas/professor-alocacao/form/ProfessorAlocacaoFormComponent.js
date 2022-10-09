
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

export default class ProfessorAlocacaoFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'professor_alocacao_form', 'form-mensagem-el' );						
	}			
						
	carregouHTMLCompleto() {
		super.limpaTudo();
								
		const instance = this;
		
		let escolaId = perfilService.getEscolaID();
		if ( escolaId === '-1' ) {
			this.mostraErro( 'Escola não selecionada.' );
			return;	
		}
		
		selectService.carregaCursosSelect( escolaId, 'cursos_select', {
			onchange : () => {
				let cursoId = instance.getFieldValue( 'curso' );
				selectService.carregaSeriesSelect( cursoId, 'series_select', {					
					onchange : () => {
						let serieId = instance.getFieldValue( 'serie' );
						selectService.carregaTurmasPorSerieSelect( serieId, 'turmas_select', {
							onchange : () => {
								let turmaId = instance.getFieldValue( 'turma' );
								selectService.carregaTurmaDisciplinasSelect( turmaId, 'turma_disciplinas_select' );
							}
						} );	
					}
				} );
			}
		} );
	}
					
}
