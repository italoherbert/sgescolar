
import FormComponent from '../FormComponent.js';

import {carregaSelectsUtil} from '../../util/CarregaSelectsUtil.js';

export default class AnoLetivoSelectFormComponent extends FormComponent {
			
	onChangeAnoLetivo = () => {};		
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'anoletivo-select-form', compELIDSufixo, 'anoletivo_select_mensagem_el' );
	}
				
	onHTMLCarregado() {
		carregaSelectsUtil.carregaEscolasSelect( { elid : 'escolas_select', onchange : (e) => this.onChangeEscola( e ) } );		
	}
	
	onChangeEscola( e ) {
		let escolaId = super.getFieldValue( 'escola' );
		carregaSelectsUtil.carregaAnosLetivosSelect( escolaId, { elid : 'anosletivos_select', onchange : (e) => this.onChangeAnoLetivo( e ) } );				
	}
	
	getEscolaID() {
		return super.getFieldValue( 'escola' );
	}
	
	getAnoLetivoID() {
		return super.getFieldValue( 'anoletivo' );
	}
	
}