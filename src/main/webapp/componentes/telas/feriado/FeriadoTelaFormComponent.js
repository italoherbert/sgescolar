
import RootFormComponent from '../../component/RootFormComponent.js';

import {selectService} from '../../service/SelectService.js';

export default class FeriadoTelaFormComponent extends RootFormComponent {
			
	onChangeAnoLetivo = () => {};		
				
	constructor() {
		super( 'feriado_anoletivo_select_form', 'anoletivo-select-mensagem-el' );
	}
				
	carregouHTMLCompleto() {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'instituicao' );
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', { 
					onchange : () => {
						let escolaId = instance.getFieldValue( 'escola' );
						selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', { 
							onchange : ( e ) => instance.onChangeAnoLetivo( e )
						} );				
					}
				} );		
			}
		} );
	}
	
}