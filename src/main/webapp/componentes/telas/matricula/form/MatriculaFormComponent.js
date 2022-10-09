
import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class MatriculaFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'matricula_form', 'form-mensagem-el' );						
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
						selectService.carregaTurmasPorSerieSelect( serieId, 'turmas_select' );	
					}
				} );
			}
		} );		
	}
			
	limpaForm() {	
		super.setFieldValue( 'curso', "-1" );		
		super.setFieldValue( 'serie', "-1" );		
		super.setFieldValue( 'turma', "-1" );	
	}
	
}
