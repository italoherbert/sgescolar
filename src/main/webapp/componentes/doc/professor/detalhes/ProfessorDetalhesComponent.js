
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';
import FuncionarioDetalhesComponent from '../../funcionario/detalhes/FuncionarioDetalhesComponent.js';

export default class ProfessorDetalhesComponent extends RootComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.funcionarioDetalhesComponent = new FuncionarioDetalhesComponent( '', 'funcionario_el' );
		
		super.addFilho( this.funcionarioDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/professor/get/"+this.globalParams.professorId, {		
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