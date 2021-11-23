
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class SerieDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );		
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/serie/get/"+this.globalParams.serieId, {		
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
		super.setHTMLCampoValor( 'descricao', 'Descrição da serie:', dados.descricao );
		super.setHTMLCampoValor( 'grau', "Grau:", dados.grau );		
		super.setHTMLCampoValor( 'escola', 'Escola:', dados.escolaNome );
		super.setHTMLCampoValor( 'curso', 'Curso:', dados.cursoNome );
	}
	
}