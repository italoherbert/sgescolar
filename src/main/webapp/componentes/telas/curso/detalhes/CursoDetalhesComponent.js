
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
		super.setHTMLCampoValor( 'descricao', 'Descrição da curso:', dados.descricao );
		super.setHTMLCampoValor( 'quantidade_aulas_dia', 'Quant. aulas por dia:', dados.quantidadeAulasDia );
		super.setHTMLCampoValor( 'escola', 'Escola:', dados.escolaNome );
		super.setHTMLCampoValor( 'carga_horaria', "Carga horária (Mensal):", dados.cargaHoraria );		
		super.setHTMLCampoValor( 'modalidade', 'Modalidade:', dados.modalidade.label );
		super.setHTMLCampoValor( 'avaliacao_tipo', 'Tipo de avaliação:', dados.avaliacaoTipo.label );
	}
	
}