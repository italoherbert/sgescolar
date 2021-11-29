
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class SecretarioTelaComponent extends RootFormComponent {
			
	onChangeTurma = () => {};	
										
	constructor() {
		super( 'secretario_filtro_form', 'mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'instituicao' );
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select' );						
			}
		} );							
	}
			
	getJSON() {
		return {
			nomeIni : super.getFieldValue( 'nomeini' )
		}
	}		
			
	limpaForm() {
		super.setFieldValue( 'instituicao', '0' );
		super.setFieldValue( 'escola', "0" );
		super.setFieldValue( 'nomeini', '' );		
	}		
}
