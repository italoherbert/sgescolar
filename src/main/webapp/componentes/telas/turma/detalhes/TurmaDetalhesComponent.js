
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class TurmaDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );		
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
		super.setHTMLCampoValor( 'escola', 'Escola:', dados.serie.curso.escolaNome );
		super.setHTMLCampoValor( 'anoletivo', 'Ano letivo:', dados.anoLetivoAno );
		super.setHTMLCampoValor( 'curso', 'Curso:', dados.serie.curso.descricao );
		super.setHTMLCampoValor( 'serie', 'Série:', dados.serie.descricao );
	}
	
}