
import RootFormComponent from '../../component/RootFormComponent.js';

import {selectService} from '../../service/SelectService.js';

import {perfilService} from '../../layout/app/perfil/PerfilService.js';

export default class FeriadoTelaComponent extends RootFormComponent {
			
	onChangeAnoLetivo = () => {};		
				
	constructor() {
		super( 'feriado_anoletivo_select_form', 'anoletivo-select-mensagem-el' );
	}
				
	carregouHTMLCompleto() {
		const instance = this;
		
		let escolaId = perfilService.getEscolaID();
		if ( escolaId === '-1' ) {
			this.mostraErro( 'Escola não selecionada.' );
			return;	
		}
				
		selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', {
			onload : () => {
				instance.setSelectFieldValue( 'anoletivo', perfilService.getAnoLetivoID() );	
			},
			onchange : ( e ) => instance.onChangeAnoLetivo( e )
		} );
	}
	
}