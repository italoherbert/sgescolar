
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';


export default class PlanejamentoDetalhesComponent extends RootDetalhesComponent {
			
	constructor() {
		super( 'mensagem-el' );
		
		this.objetivosTabelaComponent = new TabelaComponent( 'objetivos_', 'tabela_el', [] );
		this.conteudosTabelaComponent = new TabelaComponent( 'conteudos_', 'tabela_el', [] );
		this.anexosTabelaComponent = new TabelaComponent( 'anexos_', 'tabela_el', [] );
		
		this.objetivosTabelaComponent.tabelaClasses = "tabela-plano-obj-con";
		this.conteudosTabelaComponent.tabelaClasses = "tabela-plano-obj-con";
		this.anexosTabelaComponent.tabelaClasses = "tabela-plano-obj-con";
		
		super.addFilho( this.objetivosTabelaComponent );
		super.addFilho( this.conteudosTabelaComponent );
		super.addFilho( this.anexosTabelaComponent );
	}
		
	carregouHTMLCompleto() {
		const instance = this;				
		sistema.ajax( "GET", "/api/planejamento/get/"+this.globalParams.planejamentoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );	
				instance.carrega( dados );																
			},
			erro : function( msg ) {
				instance.mostraInfo( 'Dados de instituição não informados.' );	
			}
		} );		
	}
	
	carrega( dados ) {	
		let turmaDesc = dados.professorAlocacao.turmaDisciplina.turmaDescricaoDetalhada;		
		let turmaDisciplinaDesc = dados.professorAlocacao.turmaDisciplina.disciplinaDescricao;		
		let professorNome = dados.professorAlocacao.professorNome;			
				
		super.setHTMLCampoValor( 'turma', 'Turma:', turmaDesc );
		super.setHTMLCampoValor( 'disciplina', 'Disciplina:', turmaDisciplinaDesc );
		super.setHTMLCampoValor( 'professor', 'Professor:', professorNome );
		
		super.setHTMLCampoValor( 'descricao', 'Descrição:', dados.descricao );
		super.setHTMLCampoValor( 'metodologia', 'Metodologia:', dados.metodologia );
		super.setHTMLCampoValor( 'metodos_avaliacao', 'Metodos de avaliação:', dados.metodosAvaliacao );
		super.setHTMLCampoValor( 'recursos', 'Recursos:', dados.recursos );
		super.setHTMLCampoValor( 'referencias', 'Referências:', dados.referencias );
		
		let tdados = [];
		for( let i = 0; i < dados.objetivos.length; i++ ) {
			tdados[ i ] = new Array();
			tdados[ i ].push( dados.objetivos[ i ].objetivo );	
		}				
		this.objetivosTabelaComponent.carregaTBody( tdados );
		
		tdados = [];
		for( let i = 0; i < dados.conteudos.length; i++ ) {
			tdados[ i ] = new Array();
			tdados[ i ].push( dados.conteudos[ i ].conteudo );
		}
		this.conteudosTabelaComponent.carregaTBody( tdados );
	}
	
}