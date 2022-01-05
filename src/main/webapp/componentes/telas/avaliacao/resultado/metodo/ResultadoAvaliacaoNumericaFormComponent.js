
import {conversor} from '../../../../../sistema/util/Conversor.js';

import RootFormComponent from '../../../../component/RootFormComponent.js';

import TabelaComponent from '../../../../component/tabela/TabelaComponent.js';

export default class ResultadoAvaliacaoNumericaFormComponent extends RootFormComponent {

	notas = [];
										
	constructor( formNome, elid, mensagemEL ) {
		super( formNome, mensagemEL );					
		
		this.resultadoTabelaComponent = new TabelaComponent( '', elid, [ 'Aluno', 'Numérica' ] );
		this.resultadoTabelaComponent.tabelaClasses = 'tabela-v2';
		
		super.addFilho( this.resultadoTabelaComponent );	
	}			
				
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let dados = this.globalParams.dados;
								
		let resultados = dados.resultados;
								
		let tdados = [];
		for( let i = 0; i < resultados.length; i++ ) {
			let resultado = resultados[ i ];
			
			let valor = conversor.valorFloat( resultado.nota );
								
			let htmlInput = "<div class=\"col-sm-4\">";
			htmlInput += "<input type=\"number\" name=\"nota_"+i+"\" value=\""+valor+"\" class=\"form-control\" />"
			htmlInput += "</div>";
			
			tdados[ i ] = new Array();
			tdados[ i ].push( resultado.matricula.alunoNome );
			tdados[ i ].push( htmlInput );
		}
		
		this.notas = resultados;
		this.resultadoTabelaComponent.carregaTBody( tdados );
	}
		
	getJSON() {		
		let notasJson = [];
		for( let i = 0; i < this.notas.length; i++ ) {
			let matriculaId = this.notas[ i ].matricula.id;
			let nota = conversor.valorFloat( super.getFieldValue( 'nota_'+i ) );
						
			notasJson.push( { matriculaId : matriculaId, resultado : nota, avaliacaoMetodo : 'NUMERICA' } )
		}
				
		return {
			resultados : notasJson
		};
	}	
		
										
}
