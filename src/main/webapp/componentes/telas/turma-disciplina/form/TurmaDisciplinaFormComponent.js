
import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class TurmaDisciplinaFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'turma_disciplina_form', 'form-mensagem-el' );				
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
						selectService.carregaDisciplinasSelect( serieId, 'disciplinas_select' );
						selectService.carregaTurmasPorSerieSelect( serieId, 'turmas_select' );	
					}
				} );
			}
		} );							
	}
			
}
