
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

export default class AnoLetivoTelaComponent extends RootFormComponent {
	
	onChangeEscola = () => {};
	
	constructor() {
		super( 'anoletivo_filtro_form', 'mensagem-el' );
	}
		
	carregouHTMLCompleto() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = super.getFieldValue( 'instituicao' );
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', {
					onchange : instance.onChangeEscola
				} );
			}
		} );
	}	
		
}