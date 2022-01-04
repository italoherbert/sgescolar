
import {sistema} from '../../../../sistema/Sistema.js';

import {conversor} from '../../../../sistema/util/Conversor.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class AvaliacaoDetalhesComponent extends RootDetalhesComponent {
	
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
		super.setHTMLCampoValor( 'data_agendamento', 'Data da avaliação:', dados.dataAgendamento );
		super.setHTMLCampoValor( 'peso', "Peso: ", conversor.formataFloat( dados.peso ) );
		super.setHTMLCampoValor( 'notas_disponiveis', "Notas disponíveis:", (dados.notasDisponiveis === 'true' ? 'Sim' : 'Não' ) );
		super.setHTMLCampoValor( 'disciplina', 'Disciplina:', dados.turmaDisciplina.disciplinaDescricao );
		super.setHTMLCampoValor( 'turma', 'Turma:', dados.turmaDisciplina.turmaDescricaoDetalhada );
		super.setHTMLCampoValor( 'periodo', 'Período:', dados.periodo.descricao );
		
		let atipo = dados.avaliacaoTipo.name;
		
		let tdados = [];
		for( let i = 0; i < dados.resultados.length; i++ ) {
			let resultado = dados.resultados[ i ];
			
			tdados[ i ] = new Array();
			tdados[ i ].push( resultado.matricula.alunoNome );
						
			switch( atipo ) {
				case 'NOTA':
					tdados[ i ].push( conversor.formataFloat( resultado.nota ) );
					break;
				case 'CONCEITUAL':
					tdados[ i ].push( resultado.conceito.label );				
					break;			
				case 'DESCRITIVA':
					tdados[ i ].push( resultado.descricao );
					break;
			}
			
		}						
		
		this.avaliacaoTabelaComponent.carregaTBody( tdados );
	}
	
	
	
}