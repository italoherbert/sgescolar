
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import PlanejamentoFormComponent from './PlanejamentoFormComponent.js';

export default class PlanejamentoFormService {
						
	constructor() {
		this.component = new PlanejamentoFormComponent();
		this.component.removeObjetivoHTMLLink = this.removeObjetivoHTMLLink;
		this.component.removeConteudoHTMLLink = this.removeConteudoHTMLLink; 		
	}					
																
	onCarregado() {			
		this.component.configura( {
			planejamentoId : this.params.planejamentoId,
			op : this.params.op
		} );		
		this.component.carregaHTML();				
	}
		
	addObjetivo() {
		this.component.addObjetivo();
	}	
	
	addConteudo() {
		this.component.addConteudo();
	}
	
	removeObjetivo( i ) {
		this.component.removeObjetivo( i );	
	}
	
	removeConteudo( i ) {
		this.component.removeConteudo( i );
	}
	
	removeObjetivoHTMLLink( i ) {
		return htmlBuilder.novoLinkRemoverHTML( "planejamentoForm.removeObjetivo( " + i + " )" );
	}
	
	removeConteudoHTMLLink( i ) {
		return htmlBuilder.novoLinkRemoverHTML( "planejamentoForm.removeConteudo( " + i + " )" );
	}
	
	copiaPlanoEnsinoConteudo() {
		this.component.copiaPlanoEnsinoConteudo();
	}
										
	salva() {								
		this.component.limpaMensagem();
				
		let url;
		let metodo;
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = '/api/planejamento/atualiza/'+this.params.planejamentoId;
		} else {
			let professorAlocacaoId = this.component.getFieldValue( 'professor_alocacao' );
			
			metodo = 'POST';
			url = '/api/planejamento/registra/'+professorAlocacaoId;
		}
								
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {					
				instance.component.limpaForm();
				instance.component.mostraInfo( 'Planejamento salvo com êxito.' );																
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraTela() {
		sistema.carregaPagina( 'planejamento-tela' );
	}
			
}
export const planejamentoForm = new PlanejamentoFormService();