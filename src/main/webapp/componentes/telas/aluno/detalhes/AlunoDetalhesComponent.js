
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import PessoaDetalhesComponent from '../../../component/pessoa/detalhes/PessoaDetalhesComponent.js';
import UsuarioDetalhesComponent from '../../../component/usuario/detalhes/UsuarioDetalhesComponent.js';
import PaiOuMaeDetalhesComponent from './paioumae/PaiOuMaeDetalhesComponent.js';

export default class AlunoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( 'aluno_', 'pessoa_detalhes_el' );
		this.usuarioDetalhesComponent = new UsuarioDetalhesComponent( 'aluno_', 'usuario_detalhes_el' );
		this.paiDetalhesComponent = new PaiOuMaeDetalhesComponent( 'pai_', 'detalhes_el' );
		this.maeDetalhesComponent = new PaiOuMaeDetalhesComponent( 'mae_', 'detalhes_el' );
		
		super.addFilho( this.pessoaDetalhesComponent );
		super.addFilho( this.usuarioDetalhesComponent );
		super.addFilho( this.paiDetalhesComponent );
		super.addFilho( this.maeDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/aluno/get/"+this.globalParams.alunoId, {		
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
		this.pessoaDetalhesComponent.carrega( dados.pessoa );
		this.usuarioDetalhesComponent.carrega( dados.usuario );
		this.paiDetalhesComponent.carrega( dados.pai );
		this.maeDetalhesComponent.carrega( dados.mae );	
	}
}