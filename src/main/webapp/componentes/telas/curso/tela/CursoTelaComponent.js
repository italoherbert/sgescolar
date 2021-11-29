
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

export default class CursoTelaComponent extends RootFormComponent {
	
	onChangeEscola = () => {};
	
	constructor() {
		super( 'curso_filtro_form', 'mensagem-el' );
	}
		
	carregouHTMLCompleto() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'instituicao' );
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select' );
			}
		} );
		
		selectService.carregaCursoModalidadesSelect( 'modalidades_select' );
	}	
	
	getJSON() {
		return {				
			descricaoIni : super.getFieldValue( 'descricaoini' ),
			modalidade : super.getFieldValue( 'modalidade' )
		}
	}
		
}