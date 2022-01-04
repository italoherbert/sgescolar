
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import ResultadoAvaliacaoPorNotaFormComponent from './avtipo/ResultadoAvaliacaoPorNotaFormComponent.js';
import ResultadoAvaliacaoConceitualFormComponent from './avtipo/ResultadoAvaliacaoConceitualFormComponent.js';
import ResultadoAvaliacaoDescritivaFormComponent from './avtipo/ResultadoAvaliacaoDescritivaFormComponent.js';

export default class ResultadoAvaliacaoFormComponent extends RootFormComponent {
	
	avaliacaoTipo = null;
										
	constructor() {
		super( 'resultado_avaliacao_form', 'mensagem-el' );					
		
		this.avPorNotaFormComponent = new ResultadoAvaliacaoPorNotaFormComponent( 'resultado_avaliacao_form', 'resultado-el', 'mensagem-el' );
		this.avConceitualFormComponent = new ResultadoAvaliacaoConceitualFormComponent( 'resultado_avaliacao_form', 'resultado-el', 'mensagem-el' );
		this.avDescritivaFormComponent = new ResultadoAvaliacaoDescritivaFormComponent( 'resultado_avaliacao_form', 'resultado-el', 'mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		this.avPorNotaFormComponent.configura( {} );
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
		let atipo = dados.avaliacaoTipo.name;
					
		this.avaliacaoTipo = atipo;			
																			
		switch( atipo ) {
			case 'NOTA':
				this.avPorNotaFormComponent.configura( { dados : dados } );
				this.avPorNotaFormComponent.carregaHTML();
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
	}		
		
	getJSON() {		
		switch( this.avaliacaoTipo ) {
			case 'NOTA':
				return this.avPorNotaFormComponent.getJSON();
			case 'CONCEITUAL':
				return this.avConceitualFormComponent.getJSON();				
			case 'DESCRITIVA':
				return this.avDescritivaFormComponent.getJSON();
		}
		return {};
	}	
		
										
}
