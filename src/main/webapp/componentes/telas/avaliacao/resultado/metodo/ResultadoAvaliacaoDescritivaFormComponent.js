
import RootFormComponent from '../../../../component/RootFormComponent.js';

import TabelaComponent from '../../../../component/tabela/TabelaComponent.js';

export default class ResultadoAvaliacaoConceitualFormComponent extends RootFormComponent {

	descricoes = [];
										
	constructor( formNome, elid, mensagemEL ) {
		super( formNome, mensagemEL );							
	}			
				
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let dados = this.globalParams.dados;
								
		let resultados = dados.resultados;
																	
		let html = "";
		for( let i = 0; i < resultados.length; i++ ) {
			let resultado = resultados[ i ];
			
			let descricao = resultado.descricao;			
														
			html += "<div class=\"mb-2\">";
			html += "<h6>"+resultado.matricula.alunoNome+"</h6>";
			html += "<textarea id=\"descricao_"+i+"\" name=\"descricao_"+i+"\" class=\"form-control\">";
			html += descricao;			
			html += "</textarea>";
			html += "</div>";						
		}
		
		this.descricoes = resultados;
		super.setHTML( 'resultado-el', html );
	}
		
	getJSON() {		
		let descricoesJson = [];
		for( let i = 0; i < this.descricoes.length; i++ ) {
			let matriculaId = this.descricoes[ i ].matricula.id;
			let descricao = super.getFieldValue( 'descricao_'+i );										
						
			descricoesJson.push( { matriculaId : matriculaId, resultado : descricao, avaliacaoMetodo : 'DESCRITIVA' } )
		}
				
		return {
			resultados : descricoesJson
		};
	}	
		
										
}
