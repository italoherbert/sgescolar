
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

export default class DisciplinaTelaComponent extends RootFormComponent {
	
	onChangeSerie = () => {};
	
	constructor() {
		super( 'disciplina_filtro_form', 'mensagem-el' );
	}
		
	carregouHTMLCompleto() {
		const instance = this;
		
		let escolaId = perfilService.getEscolaID();
		if ( escolaId === '-1' ) {
			this.mostraErro( 'Escola não selecionada.' );
			return;	
		}
		
		selectService.carregaCursosSelect( escolaId, 'cursos_select', { 
			onchange : () => {
				let cursoId = super.getFieldValue( 'curso' );
				selectService.carregaSeriesSelect( cursoId, 'series_select', { 
					onchange : instance.onChangeSerie
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