
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';
import HorarioComponent from '../../../component/horario/HorarioComponent.js';

export default class TurmaDetalhesComponent extends RootDetalhesComponent {
	
	disciplinasVinculadasTabelaCampos = [ 'SIGLA', 'Disciplina' ];
	horarioTabelaCampos = [ 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta' ];
	
	constructor() {
		super( 'mensagem_el' );	
		
		this.disciplinasVinculadasTabelaComponent = new TabelaComponent( '', 'disciplinas-vinculadas-tabela-el', this.disciplinasVinculadasTabelaCampos )
		this.horarioComponent = new HorarioComponent( '', 'horario-tabela-el', this.horarioTabelaCampos );
		
		super.addFilho( this.disciplinasVinculadasTabelaComponent );
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
		let disciplinasVinculadas = dados.disciplinasVinculadas;
		for( let i = 0; i < disciplinasVinculadas.length; i++ ) {
			tdados[ i ] = new Array();												
			tdados[ i ].push( disciplinasVinculadas[ i ].disciplinaSigla );			
			tdados[ i ].push( disciplinasVinculadas[ i ].disciplinaDescricao );			
		}
		
		this.disciplinasVinculadasTabelaComponent.carregaTBody( tdados );
		
		this.horarioComponent.carregaJSON( disciplinasVinculadas );				
	}
	
}