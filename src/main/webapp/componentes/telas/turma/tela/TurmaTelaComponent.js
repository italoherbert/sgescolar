
import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

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
		let escolaId = perfilService.getEscolaID();
		if ( escolaId === '-1' ) {
			this.mostraErro( 'Escola não selecionada.' );
			return;	
		}	
		
		selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', {
			onload : () => {
				instance.setFieldValue( 'anoletivo', perfilService.getAnoLetivoID() );	
			},
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
			
	getJSON() {
		return {
			descricaoIni : super.getFieldValue( 'descricaoini' )
		}
	}		
			
}
