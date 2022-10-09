
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import FuncionarioDetalhesComponent from '../../../component/funcionario/detalhes/FuncionarioDetalhesComponent.js';

export default class SecretarioDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.funcionarioDetalhesComponent = new FuncionarioDetalhesComponent( '', 'funcionario_el' );
		
		super.addFilho( this.funcionarioDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/secretario/get/"+this.globalParams.secretarioId, {		
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
		this.funcionarioDetalhesComponent.carrega( dados.funcionario );
	}
	
}