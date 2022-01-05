
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class PeriodoFormComponent extends RootFormComponent {
				
	constructor() {
		super( 'periodo_form', 'form_mensagem_el' );
	}
				
	carregouHTMLCompleto() {
		super.limpaTudo();				
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/periodo/get/"+this.globalParams.periodoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					selectService.carregaPeriodoTiposSelect( 'tipos_select', {
						onload : () => {
							instance.carregaJSON( dados );															
						}
					} );					
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		}
	}	
						
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			tipo : super.getFieldValue( 'tipo' ),
			dataInicio : conversor.formataData( super.getFieldValue( 'dataini' ) ),
			dataFim : conversor.formataData( super.getFieldValue( 'datafim' ) ),
			lancamentoDataInicio : conversor.formataData( super.getFieldValue( 'lancamento_dataini' ) ),
			lancamentoDataFim : conversor.formataData( super.getFieldValue( 'lancamento_datafim' ) )								
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'tipo', dados.tipo.name );
		super.setFieldValue( 'dataini', conversor.valorData( dados.dataInicio ) );
		super.setFieldValue( 'datafim', conversor.valorData( dados.dataFim ) );
		super.setFieldValue( 'lancamento_dataini', conversor.valorData( dados.lancamentoDataInicio ) );		
		super.setFieldValue( 'lancamento_datafim', conversor.valorData( dados.lancamentoDataFim ) );		
	}
	
	limpaForm() {
		super.setFieldValue( 'dataini', "" );
		super.setFieldValue( 'datafim', "" );
		super.setFieldValue( 'lancamento_dataini', "" );		
		super.setFieldValue( 'lancamento_datafim', "" );		
	}
	
}