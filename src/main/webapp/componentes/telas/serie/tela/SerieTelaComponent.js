
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class SerieTelaComponent extends RootFormComponent {
			
	onChangeCurso = () => {};	
										
	constructor() {
		super( 'serie_filtro_form', 'mensagem-el' );				
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
			onchange : instance.onChangeCurso
		} );				
	}
			
	getJSON() {
		return {
			descricaoIni : super.getFieldValue( 'descricaoini' )
		}
	}		
				
}
