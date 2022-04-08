
import {sistema} from '../../../../sistema/Sistema.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import EnderecoDetalhesComponent from '../../../component/endereco/detalhes/EnderecoDetalhesComponent.js';
import ContatoInfoDetalhesComponent from '../../../component/contato-info/detalhes/ContatoInfoDetalhesComponent.js';

export default class EscolaDetalhesComponent extends RootDetalhesComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.enderecoDetalhesComponent = new EnderecoDetalhesComponent( '', 'endereco_detalhes_el' );
		this.contatoInfoDetalhesComponent = new ContatoInfoDetalhesComponent( '', 'contato_info_detalhes_el' );
		
		super.addFilho( this.enderecoDetalhesComponent );
		super.addFilho( this.contatoInfoDetalhesComponent );
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/escola/get/"+this.globalParams.escolaId, {		
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
		super.setHTMLCampoValor( 'nome', 'Nome da escola:', dados.nome );
		
		let municipio = dados.instituicao.endereco.municipio;
		let uf = dados.instituicao.endereco.uf;
		
		this.enderecoDetalhesComponent.carrega( dados.endereco, municipio, uf );
		this.contatoInfoDetalhesComponent.carrega( dados.contatoInfo );
	}
	
}