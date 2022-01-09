
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';
import HorarioComponent from '../../../component/horario/HorarioComponent.js';

export default class TurmaDetalhesComponent extends RootDetalhesComponent {
	
	turmaDisciplinasTabelaCampos = [ 'SIGLA', 'Disciplina' ];
	horarioTabelaCampos = [ 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta' ];
	
	constructor() {
		super( 'mensagem_el' );	
		
		this.turmaDisciplinasTabelaComponent = new TabelaComponent( '', 'disciplinas-vinculadas-tabela-el', this.turmaDisciplinasTabelaCampos )
		this.horarioComponent = new HorarioComponent( '', 'horario-tabela-el', this.horarioTabelaCampos );
		
		super.addFilho( this.turmaDisciplinasTabelaComponent );
		super.addFilho( this.horarioComponent );			
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/turma/get/"+this.globalParams.turmaId, {		
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
		super.setHTMLCampoValor( 'descricao', 'Turma:', dados.descricao );
		super.setHTMLCampoValor( 'turno', 'Turno:', dados.turno.label );
		super.setHTMLCampoValor( 'escola', 'Escola:', dados.serie.curso.escolaNome );
		super.setHTMLCampoValor( 'anoletivo', 'Ano letivo:', dados.anoLetivoAno );
		super.setHTMLCampoValor( 'serie', 'Série:', dados.serie.descricao );
		super.setHTMLCampoValor( 'curso', 'Curso:', dados.serie.curso.descricao );
				
		let tdados = [];
		let turmaDisciplinas = dados.turmaDisciplinas;
		for( let i = 0; i < turmaDisciplinas.length; i++ ) {
			tdados[ i ] = new Array();												
			tdados[ i ].push( turmaDisciplinas[ i ].disciplinaSigla );			
			tdados[ i ].push( turmaDisciplinas[ i ].disciplinaDescricao );			
		}
		
		this.turmaDisciplinasTabelaComponent.carregaTBody( tdados );
		
		let quantidade_aulas_dia = parseInt( dados.serie.curso.quantidadeAulasDia );
								
		this.horarioComponent.carregaPorTurmaDisciplinasJSON( turmaDisciplinas, quantidade_aulas_dia );
	}
	
}