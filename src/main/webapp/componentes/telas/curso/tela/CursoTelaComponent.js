
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

export default class CursoTelaComponent extends RootFormComponent {
	
	onChangeEscola = () => {};
	
	constructor() {
		super( 'curso_filtro_form', 'mensagem-el' );
	}
		
	carregouHTMLCompleto() {		
		selectService.carregaCursoModalidadesSelect( 'modalidades_select' );
	}	
	
	getJSON() {
		return {				
			descricaoIni : super.getFieldValue( 'descricaoini' ),
			modalidade : super.getFieldValue( 'modalidade' )
		}
	}
		
}