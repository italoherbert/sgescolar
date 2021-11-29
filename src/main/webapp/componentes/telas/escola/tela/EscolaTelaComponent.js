
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

export default class EscolaTelaComponent extends RootFormComponent {
	
	onChangeEscola = () => {};
	
	constructor() {
		super( 'escola_filtro_form', 'mensagem-el' );
	}
		
	carregouHTMLCompleto() {
		selectService.carregaInstituicoesSelect( 'instituicoes_select' );
	}	
	
	getJSON() {
		return {				
			nomeIni : super.getFieldValue( 'nomeini' )
		}
	}
		
}