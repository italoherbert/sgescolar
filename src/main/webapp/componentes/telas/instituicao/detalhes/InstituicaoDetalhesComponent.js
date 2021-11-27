
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import EnderecoDetalhesComponent from '../../../component/endereco/detalhes/EnderecoDetalhesComponent.js';
import ContatoInfoDetalhesComponent from '../../../component/contato-info/detalhes/ContatoInfoDetalhesComponent.js';

export default class InstituicaoDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.enderecoDetalhesComponent = new EnderecoDetalhesComponent( '', 'endereco_detalhes_el' );
		this.contatoInfoDetalhesComponent = new ContatoInfoDetalhesComponent( '', 'contato_info_detalhes_el' );
		
		super.addFilho( this.enderecoDetalhesComponent );
		super.addFilho( this.contatoInfoDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;				
		sistema.ajax( "GET", "/api/instituicao/get/"+this.globalParams.instituicaoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );	
				instance.carrega( dados );																
			},
			erro : function( msg ) {
				instance.mostraInfo( 'Dados de instituição não informados.' );	
			}
		} );		
	}
	
	carrega( dados ) {			
		super.setHTMLCampoValor( 'cnpj', 'CNPJ da instituição:', dados.cnpj );
		super.setHTMLCampoValor( 'razao_social', 'Razão social da instituição:', dados.razaoSocial );
				
		this.enderecoDetalhesComponent.carrega( dados.endereco );
		this.contatoInfoDetalhesComponent.carrega( dados.contatoInfo );
	}
	
}