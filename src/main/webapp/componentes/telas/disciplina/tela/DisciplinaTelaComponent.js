
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

export default class DisciplinaTelaComponent extends RootFormComponent {
	
	onChangeSerie = () => {};
	
	constructor() {
		super( 'disciplina_filtro_form', 'mensagem-el' );
	}
		
	carregouHTMLCompleto() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'instituicao' );			
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', { 
					onchange : () => {
						let escolaId = super.getFieldValue( 'escola' );
						selectService.carregaCursosSelect( escolaId, 'cursos_select', { 
							onchange : () => {
								let cursoId = super.getFieldValue( 'curso' );
								selectService.carregaSeriesSelect( cursoId, 'series_select', { 
									onchange : instance.onChangeSerie
								} );
							} 
						} );				
					}
				} );				
			}
		} );
	}	
	
	getJSON() {
		return {									
			descricaoIni : super.getFieldValue( 'descricaoini' )			
		}
	}
		
}