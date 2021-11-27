
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class DisciplinaDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );		
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/disciplina/get/"+this.globalParams.disciplinaId, {		
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
		super.setHTMLCampoValor( 'descricao', 'Descrição da disciplina:', dados.descricao );
		super.setHTMLCampoValor( 'escola', 'Escola:', dados.serie.curso.escolaNome );
		super.setHTMLCampoValor( 'curso', 'Curso:', dados.serie.curso.descricao );
		super.setHTMLCampoValor( 'serie', 'Serie:', dados.serie.descricao );
	}
	
}