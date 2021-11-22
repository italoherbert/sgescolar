
import FormComponent from '../FormComponent.js';

import {selectService} from '../../service/SelectService.js';

export default class AnoLetivoSelectFormComponent extends FormComponent {
			
	onChangeAnoLetivo = () => {};		
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'anoletivo-select-form', compELIDSufixo, 'anoletivo_select_mensagem_el' );
	}
				
	onHTMLCarregado() {
		selectService.carregaEscolasSelect( 'escolas_select', { onchange : (e) => this.onChangeEscola( e ) } );		
	}
	
	onChangeEscola( e ) {
		let escolaId = super.getFieldValue( 'escola' );
		selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', { onchange : (e) => this.onChangeAnoLetivo( e ) } );				
	}
	
	getEscolaID() {
		return super.getFieldValue( 'escola' );
	}
	
	getAnoLetivoID() {
		return super.getFieldValue( 'anoletivo' );
	}
	
}