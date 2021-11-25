
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';

export default class CursoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );		
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/curso/get/"+this.globalParams.cursoId, {		
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
		super.setHTMLCampoValor( 'nome', 'Nome da curso:', dados.nome );
		super.setHTMLCampoValor( 'escola', 'Escola:', dados.escolaNome );
		super.setHTMLCampoValor( 'modalidade', 'Modalidade:', dados.modalidade.label );
		super.setHTMLCampoValor( 'carga_horaria', "Carga horária (Mensal):", dados.cargaHoraria );		
	}
	
}