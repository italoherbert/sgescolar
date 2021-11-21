
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import FormComponent from '../../../component/FormComponent.js';

export default class PeriodoFormComponent extends FormComponent {
				
	constructor() {
		super( 'periodo_form', '', 'periodo-form', 'periodo-form-el', 'form_mensagem_el' );
	}
				
	onHTMLCarregado() {				
		const instance = this;
		sistema.ajax( 'GET', '/api/tipos/periodos', {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				instance.getEL( 'tipos_select' ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : dados,
					defaultOption : { texto : 'Selecione o tipo de período', valor : '0' }					
				} );	
			}
		} );
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