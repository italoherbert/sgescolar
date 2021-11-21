
import {conversor} from '../../../../sistema/util/Conversor.js';

import FormComponent from '../../../component/FormComponent.js';

export default class FeriadoFormComponent extends FormComponent {
				
	constructor() {
		super( 'feriado_form', '', 'feriado-form', 'feriado-form-el', 'form-mensagem-el' );
	}
						
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			dataInicio : conversor.formataData( super.getFieldValue( 'dataini' ) ),
			dataFim : conversor.formataData( super.getFieldValue( 'datafim' ) ),
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'descricao', dados.descricao );		
		super.setFieldValue( 'dataini', conversor.valorData( dados.dataInicio ) );
		super.setFieldValue( 'datafim', conversor.valorData( dados.dataFim ) );
	}
	
	limpaForm() {
		super.setFieldValue( 'descricao', "" );		
		super.setFieldValue( 'dataini', "" );
		super.setFieldValue( 'datafim', "" );
	}
	
}