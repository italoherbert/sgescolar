
import {sistema} from '../../../../sistema/Sistema.js';

import {conversor} from '../../../../sistema/util/Conversor.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class AvaliacaoDetalhesComponent extends RootDetalhesComponent {
	
	disciplina = null;
	turma = null;
	
	constructor() {
		super( 'mensagem_el' );				
		
		this.avaliacaoTabelaComponent = new TabelaComponent( '', 'notas-tabela-el', [ 'Aluno', 'Resultado' ] );
		this.avaliacaoTabelaComponent.tabelaClasses = 'tabela-v2';
		
		super.addFilho( this.avaliacaoTabelaComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/avaliacao/get/"+this.globalParams.avaliacaoId, {		
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
		this.turma = dados.turmaDisciplina.turmaDescricaoDetalhada;
		this.disciplina = dados.turmaDisciplina.disciplinaDescricao;
		
		let resultadoDisponivel = (dados.resultadoDisponivel === 'true' ? 'Sim' : 'Não' );
		
		super.setHTMLCampoValor( 'data_agendamento', 'Data da avaliação:', dados.dataAgendamento );
		super.setHTMLCampoValor( 'peso', "Peso: ", conversor.formataFloat( dados.peso ) );
		super.setHTMLCampoValor( 'resultado_disponivel', "Resultado disponível:", resultadoDisponivel );
		super.setHTMLCampoValor( 'disciplina', 'Disciplina:', dados.turmaDisciplina.disciplinaDescricao );
		super.setHTMLCampoValor( 'turma', 'Turma:', dados.turmaDisciplina.turmaDescricaoDetalhada );
		super.setHTMLCampoValor( 'periodo', 'Período:', dados.periodo.descricao );
		
		let avMetodo = dados.avaliacaoMetodo.name;
		
		let tdados = [];
		for( let i = 0; i < dados.resultados.length; i++ ) {
			let resultado = dados.resultados[ i ];
			
			tdados[ i ] = new Array();
			tdados[ i ].push( resultado.matricula.alunoNome );
						
			if ( dados.resultadoDisponivel === 'true' ) {														
				switch( avMetodo ) {
					case 'NUMERICA':
						tdados[ i ].push( conversor.formataFloat( resultado.nota ) );					
						break;
					case 'CONCEITUAL':
						tdados[ i ].push( resultado.conceito.label );				
						break;			
					case 'DESCRITIVA':
						tdados[ i ].push( resultado.descricao );
						break;
				}
			} else {
				tdados[ i ].push( '<span class="text-primary">Não disponível</span>' );
			}
			
		}						
		
		this.avaliacaoTabelaComponent.carregaTBody( tdados );
	}
	
	
	
}