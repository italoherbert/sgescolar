
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class TurmaTelaComponent extends RootFormComponent {
			
	onChangeAnoLetivo = () => {};
	onChangeSerie = () => {}	
										
	constructor() {
		super( 'turma_filtro_form', 'mensagem-el' );				
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
						selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', { 
							onchange : instance.onChangeAnoLetivo
						} );				
						selectService.carregaCursosSelect( escolaId, 'cursos_select', { 
							onchange : () => {
								let cursoId = super.getFieldValue( 'curso' );
								selectService.carregaSeriesSelect( cursoId, 'series_select', { 
									onchange : () => {
										instance.onChangeSerie();
									} 
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
