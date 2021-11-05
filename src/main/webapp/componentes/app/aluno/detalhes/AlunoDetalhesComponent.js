
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';
import PessoaDetalhesComponent from '../../pessoa/detalhes/PessoaDetalhesComponent.js';

export default class AlunoDetalhesComponent extends RootComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( 'aluno_' );
		
		super.addFilho( this.pessoaDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/aluno/get/"+this.globalParams.alunoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );				
				instance.pessoaDetalhesComponent.carrega( dados.pessoa );															
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );		
	}
	
}