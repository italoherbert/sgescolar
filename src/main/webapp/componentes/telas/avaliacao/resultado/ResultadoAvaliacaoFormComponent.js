
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class ResultadoAvaliacaoFormComponent extends RootFormComponent {

	notas = [];
										
	constructor() {
		super( 'resultado_avaliacao_form', 'mensagem-el' );					
		
		this.resultadoTabelaComponent = new TabelaComponent( '', 'resultado-tabela-el', [ 'Aluno', 'Nota' ] );
		this.resultadoTabelaComponent.tabelaClasses = 'tabela-v2';
		
		super.addFilho( this.resultadoTabelaComponent );	
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		this.carrega( this.globalParams.avaliacaoId );		
	}
	
	carrega( id ) {
		const instance = this;
		sistema.ajax( 'GET', '/api/avaliacao/get/'+id, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				instance.carregaJSON( dados );
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}			
		} );
	}
	
	carregaJSON( dados ) {		
		let notas = dados.notas;
				
		let tdados = [];
		for( let i = 0; i < notas.length; i++ ) {
			let nota = notas[ i ];
			
			let valor = conversor.valorFloat( nota.nota );
					
			let htmlInput = "<div class=\"col-sm-4\">";
			htmlInput += "<input type=\"number\" name=\"nota_"+i+"\" value=\""+valor+"\" class=\"form-control\" />"
			htmlInput += "</div>";
			
			tdados[ i ] = new Array();
			tdados[ i ].push( nota.matricula.alunoNome );
			tdados[ i ].push( htmlInput );
		}
		
		this.notas = notas;
		this.resultadoTabelaComponent.carregaTBody( tdados );
	}
		
	getJSON() {		
		let notasJson = [];
		for( let i = 0; i < this.notas.length; i++ ) {
			let matriculaId = this.notas[ i ].matricula.id;
			let nota = conversor.valorFloat( super.getFieldValue( 'nota_'+i ) );
						
			notasJson.push( { matriculaId, matriculaId, nota : nota } )
		}
		
		return {
			notas : notasJson
		};
	}	
		
										
}
