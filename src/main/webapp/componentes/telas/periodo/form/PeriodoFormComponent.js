
import {conversor} from '../../../../sistema/util/Conversor.js';

import {selectService} from '../../../service/SelectService.js';

import FormComponent from '../../../component/FormComponent.js';

export default class PeriodoFormComponent extends FormComponent {
				
	constructor() {
		super( 'periodo_form', '', 'periodo-form', 'periodo-form-el', 'form_mensagem_el' );
	}
				
	onHTMLCarregado() {			
		selectService.carregaPeriodosSelect( 'tipos_select' );					
	}			
						
	getJSON() {
		return {
			tipo : super.getFieldValue( 'tipo' ),
			dataInicio : conversor.formataData( super.getFieldValue( 'dataini' ) ),
			dataFim : conversor.formataData( super.getFieldValue( 'datafim' ) ),
			lancamentoDataInicio : conversor.formataData( super.getFieldValue( 'lancamento_dataini' ) ),
			lancamentoDataFim : conversor.formataData( super.getFieldValue( 'lancamento_datafim' ) )								
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'tipo', dados.tipo );
		super.setFieldValue( 'dataini', conversor.valorData( dados.dataInicio ) );
		super.setFieldValue( 'datafim', conversor.valorData( dados.dataFim ) );
		super.setFieldValue( 'lancamento_dataini', conversor.valorData( dados.lancamentoDataInicio ) );		
		super.setFieldValue( 'lancamento_datafim', conversor.valorData( dados.lancamentoDataFim ) );		
	}
	
	limpaForm() {
		super.setFieldValue( 'tipo', "0" );
		super.setFieldValue( 'dataini', "" );
		super.setFieldValue( 'datafim', "" );
		super.setFieldValue( 'lancamento_dataini', "" );		
		super.setFieldValue( 'lancamento_datafim', "" );		
	}
	
}