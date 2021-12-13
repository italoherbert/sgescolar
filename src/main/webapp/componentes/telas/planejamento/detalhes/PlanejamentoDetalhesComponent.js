
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';


export default class PlanejamentoDetalhesComponent extends RootDetalhesComponent {
			
	clickAnexoHTMLLink = null;		
			
	constructor() {
		super( 'mensagem-el' );
		
		this.objetivosTabelaComponent = new TabelaComponent( 'objetivos_', 'tabela_el', [] );
		this.conteudosTabelaComponent = new TabelaComponent( 'conteudos_', 'tabela_el', [] );
		this.anexosTabelaComponent = new TabelaComponent( 'anexos_', 'tabela_el', [] );
		
		this.objetivosTabelaComponent.tabelaClasses = "list-item-tabela-v1";
		this.conteudosTabelaComponent.tabelaClasses = "list-item-tabela-v1";
		this.anexosTabelaComponent.tabelaClasses = "list-item-tabela-v2";
		
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
				instance.mostraErro( msg );	
			}
		} );		
	}
	
	downloadAnexo( id ) {
		const instance = this;				
		sistema.ajax( "GET", "/api/planejamento/anexo/download/"+id, {
			sucesso : function( resposta ) {		
				let file = new Blob( [resposta], {type: 'application/octet-stream'});
				
				let a = document.createElement( 'a' );
				a.download = "arquivo.xls";
				a.href = window.URL.createObjectURL( file );
				a.click();
													
				instance.mostraInfo( 'Download realizado com sucesso' );																
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} )
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
		
		tdados = [];
		for( let i = 0; i < dados.anexos.length; i++ ) {
			let anexo = dados.anexos[ i ];
			
			tdados[ i ] = new Array();
			tdados[ i ].push( '<a href="/api/planejamento/anexo/download/'+anexo.id+'/'+sistema.globalVars.token+'" class="link-primary">'+anexo.arquivoNome+'</a>' ); 
		}
		this.anexosTabelaComponent.carregaTBody( tdados );
		
		
	}
	
}