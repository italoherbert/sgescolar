
import {htmlBuilder} from '../../../../../sistema/util/HTMLBuilder.js';

import RootFormComponent from '../../../../component/RootFormComponent.js';

import TabelaComponent from '../../../../component/tabela/TabelaComponent.js';

export default class ResultadoAvaliacaoConceitualFormComponent extends RootFormComponent {

	conceitos = [];
										
	constructor( formNome, elid, mensagemEL ) {
		super( formNome, mensagemEL );					
		
		this.resultadoTabelaComponent = new TabelaComponent( '', elid, [ 'Aluno', 'Conceito' ] );
		this.resultadoTabelaComponent.tabelaClasses = 'tabela-v2';
		
		super.addFilho( this.resultadoTabelaComponent );	
	}			
				
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let dados = this.globalParams.dados;
								
		let resultados = dados.resultados;
		
		let conceitoTipos = dados.conceitoTipos;
								
		let tdados = [];
		for( let i = 0; i < resultados.length; i++ ) {
			let resultado = resultados[ i ];
			
			let valor = resultado.conceito.name;			
														
			let htmlInput = "<div class=\"col-sm-4\">";
			htmlInput += "<select id=\"conceito_"+i+"\" name=\"conceito_"+i+"\" class=\"form-select\">";
			htmlInput += htmlBuilder.novoSelectOptionsHTML( {
				valores : conceitoTipos.names,
				textos : conceitoTipos.labels,
				checkedValor : valor
			} );
			htmlInput += "</select>";
			htmlInput += "</div>";
			
			tdados[ i ] = new Array();
			tdados[ i ].push( resultado.matricula.alunoNome );
			tdados[ i ].push( htmlInput );
		}
		
		this.conceitos = resultados;
		this.resultadoTabelaComponent.carregaTBody( tdados );
	}
		
	getJSON() {		
		let conceitosJson = [];
		for( let i = 0; i < this.conceitos.length; i++ ) {
			let matriculaId = this.conceitos[ i ].matricula.id;
			let conceito = super.getFieldValue( 'conceito_'+i );										
						
			conceitosJson.push( { matriculaId : matriculaId, resultado : conceito, avaliacaoMetodo : 'CONCEITUAL' } )
		}
				
		return {
			resultados : conceitosJson
		};
	}	
		
										
}
