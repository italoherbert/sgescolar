
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import PessoaDetalhesComponent from '../../../component/pessoa/detalhes/PessoaDetalhesComponent.js';
import UsuarioDetalhesComponent from '../../../component/usuario/detalhes/UsuarioDetalhesComponent.js';
import ResponsavelDetalhesComponent from './responsavel/ResponsavelDetalhesComponent.js'; 

export default class AlunoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( 'aluno_', 'pessoa_detalhes_el' );
		this.usuarioDetalhesComponent = new UsuarioDetalhesComponent( 'aluno_', 'usuario_detalhes_el' );
		this.paiDetalhesComponent = new ResponsavelDetalhesComponent( 'pai_', 'detalhes_el' );
		this.maeDetalhesComponent = new ResponsavelDetalhesComponent( 'mae_', 'detalhes_el' );
		this.responsavelDetalhesComponent = new ResponsavelDetalhesComponent( 'responsavel_', 'detalhes_el' );
		
		super.addFilho( this.pessoaDetalhesComponent );
		super.addFilho( this.usuarioDetalhesComponent );
		super.addFilho( this.paiDetalhesComponent );
		super.addFilho( this.maeDetalhesComponent );
		super.addFilho( this.responsavelDetalhesComponent );
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
		this.responsavelDetalhesComponent.carrega( dados.responsavel );
	}
}