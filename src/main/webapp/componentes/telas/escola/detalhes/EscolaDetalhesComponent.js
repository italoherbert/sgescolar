
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';
import EnderecoLocalDetalhesComponent from '../../../component/endereco-local/detalhes/EnderecoLocalDetalhesComponent.js';
import ContatoInfoDetalhesComponent from '../../../component/contato-info/detalhes/ContatoInfoDetalhesComponent.js';

export default class EscolaDetalhesComponent extends RootComponent {
	
	constructor() {
		super( 'mensagem_el' );
		
		this.enderecoLocalDetalhesComponent = new EnderecoLocalDetalhesComponent( '', 'endereco_detalhes_el' );
		this.contatoInfoDetalhesComponent = new ContatoInfoDetalhesComponent( '', 'contato_info_detalhes_el' );
		
		super.addFilho( this.enderecoLocalDetalhesComponent );
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
		sistema.carregaComponente( 'campo', super.getELID( 'nome' ), { rotulo : "Nome da escola:", valor : dados.nome } );
		
		let municipio = dados.instituicao.endereco.municipio;
		let uf = dados.instituicao.endereco.uf;
		
		this.enderecoLocalDetalhesComponent.carrega( dados.enderecoLocal, municipio, uf );
		this.contatoInfoDetalhesComponent.carrega( dados.contatoInfo );
	}
	
}