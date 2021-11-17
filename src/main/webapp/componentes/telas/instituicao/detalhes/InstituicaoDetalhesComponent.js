
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';
import EnderecoDetalhesComponent from '../../../component/endereco/detalhes/EnderecoDetalhesComponent.js';
import ContatoInfoDetalhesComponent from '../../../component/contato-info/detalhes/ContatoInfoDetalhesComponent.js';

export default class InstituicaoDetalhesComponent extends RootComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.enderecoDetalhesComponent = new EnderecoDetalhesComponent( '', 'endereco_detalhes_el' );
		this.contatoInfoDetalhesComponent = new ContatoInfoDetalhesComponent( '', 'contato_info_detalhes_el' );
		
		super.addFilho( this.enderecoDetalhesComponent );
		super.addFilho( this.contatoInfoDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/instituicao/get", {		
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
		sistema.carregaComponente( 'campo', super.getELID( 'cnpj' ), { rotulo : "CNPJ da instituição:", valor : dados.cnpj } );
		sistema.carregaComponente( 'campo', super.getELID( 'razao_social' ), { rotulo : "Razão social da instituição:", valor : dados.razaoSocial } );
				
		this.enderecoDetalhesComponent.carrega( dados.endereco );
		this.contatoInfoDetalhesComponent.carrega( dados.contatoInfo );
	}
	
}