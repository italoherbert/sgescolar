
import {sistema} from '../../../../sistema/Sistema.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';
import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class AnoLetivoDetalhesComponent extends RootDetalhesComponent {
			
	feriadosTabelaCampos = [ 'Descrição', 'Início', 'Fim' ];
	periodosTabelaCampos = [ 'Tipo', 'Início', 'Fim', 'Início lançamento', 'Fim lançamento' ];		
			
	constructor() {
		super( 'mensagem_el' );		
		
		this.periodosTabelaComponent = new TabelaComponent( 'periodos_', 'tabela_el', this.periodosTabelaCampos );
		this.feriadosTabelaComponent = new TabelaComponent( 'feriados_', 'tabela_el', this.feriadosTabelaCampos );
		
		super.addFilho( this.periodosTabelaComponent );
		super.addFilho( this.feriadosTabelaComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/anoletivo/get/"+this.globalParams.anoLetivoId, {		
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
		super.setHTMLCampoValor( 'ano', 'Ano:', dados.ano );
		
		let feriados = dados.feriados;
		let periodos = dados.periodos;
										
		let tdados = [];				
		for( let i = 0; i < periodos.length; i++ ) {
			tdados[ i ] = new Array();
			tdados[ i ].push( periodos[ i ].tipo );	
			tdados[ i ].push( periodos[ i ].dataInicio );	
			tdados[ i ].push( periodos[ i ].dataFim );	
			tdados[ i ].push( periodos[ i ].lancamentoDataInicio );	
			tdados[ i ].push( periodos[ i ].lancamentoDataFim );	
		} 
		this.periodosTabelaComponent.carregaTBody( tdados );
		
		tdados.splice( 0, tdados.length );

		for( let i = 0; i < feriados.length; i++ ) {
			tdados[ i ] = new Array();
			tdados[ i ].push( feriados[ i ].descricao );	
			tdados[ i ].push( feriados[ i ].dataInicio );	
			tdados[ i ].push( feriados[ i ].dataFim );	
		} 
		this.feriadosTabelaComponent.carregaTBody( tdados );		
	}
	
}