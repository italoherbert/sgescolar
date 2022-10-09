
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class PeriodoDetalhesComponent extends RootDetalhesComponent {
				
	constructor() {
		super( 'mensagem-el' );
	}					
	
	carregouHTMLCompleto() {
		const instance = this;				
		sistema.ajax( "GET", "/api/periodo/get/"+this.globalParams.periodoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );	
				instance.carrega( dados );																
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );		
	}
	
	carrega( dados ) {
		super.setHTMLCampoValor( 'descricao', 'Descrição:', dados.descricao );		
		super.setHTMLCampoValor( 'quant_dias_letivos', 'Quantidade de dias letivos:', dados.diasLetivosQuant );		
		super.setHTMLCampoValor( 'tipo', 'Tipo de período:', dados.tipo );
		super.setHTMLCampoValor( 'dataini', 'Data de início:', dados.dataInicio );
		super.setHTMLCampoValor( 'datafim', 'Data de fim:', dados.dataFim );
		super.setHTMLCampoValor( 'lancamento_dataini', 'Data de início de lançamentos:', dados.lancamentoDataInicio );		
		super.setHTMLCampoValor( 'lancamento_datafim', 'Data de fim de lançamentos:', dados.lancamentoDataFim );		
	}
		
}