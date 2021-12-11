
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class PlanejamentoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem-el' );
		
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
		let turmaDisciplinaDesc = dados.professorAlocacao.turmaDisciplina.descricao;		
		let professorNome = dados.professorAlocacao.professorNome;			
				
		super.setHTMLCampoValor( 'turma', 'Turma:', turmaDesc );
		super.setHTMLCampoValor( 'disciplina', 'Disciplina:', turmaDisciplinaDesc );
		super.setHTMLCampoValor( 'professor', 'Professor:', professorNome );
		
		super.setHTMLCampoValor( 'descricao', 'Descrição:', dados.descricao );
		super.setHTMLCampoValor( 'metodologia', 'Metodologia:', dados.metodologia );
		super.setHTMLCampoValor( 'metodos_avaliacao', 'Metodos de avaliação:', dados.metodosAvaliacao );
		super.setHTMLCampoValor( 'recursos', 'Recursos:', dados.recursos );
		super.setHTMLCampoValor( 'referencias', 'Referências:', dados.referencias );				
	}
	
}