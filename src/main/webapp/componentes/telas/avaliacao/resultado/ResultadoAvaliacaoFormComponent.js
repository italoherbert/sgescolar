
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import ResultadoAvaliacaoNumericaFormComponent from './metodo/ResultadoAvaliacaoNumericaFormComponent.js';
import ResultadoAvaliacaoConceitualFormComponent from './metodo/ResultadoAvaliacaoConceitualFormComponent.js';
import ResultadoAvaliacaoDescritivaFormComponent from './metodo/ResultadoAvaliacaoDescritivaFormComponent.js';

export default class ResultadoAvaliacaoFormComponent extends RootFormComponent {
	
	avMetodo = null;
										
	constructor() {
		super( 'resultado_avaliacao_form', 'mensagem-el' );					
		
		this.avNumericaFormComponent = new ResultadoAvaliacaoNumericaFormComponent( 'resultado_avaliacao_form', 'resultado-el', 'mensagem-el' );
		this.avConceitualFormComponent = new ResultadoAvaliacaoConceitualFormComponent( 'resultado_avaliacao_form', 'resultado-el', 'mensagem-el' );
		this.avDescritivaFormComponent = new ResultadoAvaliacaoDescritivaFormComponent( 'resultado_avaliacao_form', 'resultado-el', 'mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		this.avNumericaFormComponent.configura( {} );
		this.avConceitualFormComponent.configura( {} );
		this.avDescritivaFormComponent.configura( {} );
		
		this.carrega( this.globalParams.avaliacaoId );		
	}
	
	carrega( avaliacaoId ) {
		const instance = this;
		sistema.ajax( 'GET', '/api/avaliacao/get/'+avaliacaoId, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );												
				instance.carregaJSON( dados );
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}			
		} );
	}
	
	sincronizaMatriculas( avaliacaoId ) {
		const instance = this;
		sistema.ajax( 'POST', '/api/avaliacao/sincroniza/matriculas/'+avaliacaoId, {
			sucesso : ( resposta ) => {
				instance.carrega( avaliacaoId );
				instance.mostraInfo( 'Sincronização realizada com êxito' );
			},
			erro : ( msg ) => {
				instance.mostraErro( msg );
			}			
		} );
	}
	
	carregaJSON( dados ) {
		let avMetodo = dados.avaliacaoMetodo.name;
					
		this.avMetodo = avMetodo;			
																			
		switch( avMetodo ) {
			case 'NUMERICA':
				this.avNumericaFormComponent.configura( { dados : dados } );
				this.avNumericaFormComponent.carregaHTML();
				break;
			case 'CONCEITUAL':
				this.avConceitualFormComponent.configura( { dados : dados } );
				this.avConceitualFormComponent.carregaHTML();
				break;
			case 'DESCRITIVA':
				this.avDescritivaFormComponent.configura( { dados : dados } );
				this.avDescritivaFormComponent.carregaHTML();
				break;
		}			
		
		super.setHTML( 'turma-el', dados.turmaDisciplina.turmaDescricaoDetalhada );
		super.setHTML( 'disciplina-el', dados.turmaDisciplina.disciplinaDescricao );
		super.setHTML( 'periodo-el', dados.periodo.descricao );
		super.setHTML( 'data-agendamento-el', dados.dataAgendamento );
		super.setHTML( 'av-peso-el', dados.peso );
	}		
		
	getJSON() {				
		switch( this.avMetodo ) {
			case 'NUMERICA':
				return this.avNumericaFormComponent.getJSON();
			case 'CONCEITUAL':
				return this.avConceitualFormComponent.getJSON();				
			case 'DESCRITIVA':
				return this.avDescritivaFormComponent.getJSON();
		}
		return {};
	}	
		
										
}
