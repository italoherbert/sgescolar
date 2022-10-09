
import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

export default class EscolaTelaComponent extends RootFormComponent {
	
	onChangeEscola = () => {};
	
	constructor() {
		super( 'escola_filtro_form', 'mensagem-el' );
	}
			
	carregouHTMLCompleto() {		
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onload : () => {
				instance.setFieldValue( 'instituicao', perfilService.getInstituicaoID() );
			}
		} ); 
	}		
			
	getJSON() {
		return {				
			nomeIni : super.getFieldValue( 'nomeini' )
		}
	}
		
}