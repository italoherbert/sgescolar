
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import EnderecoFormComponent from '../../../component/endereco/form/EnderecoFormComponent.js';
import ContatoInfoFormComponent from '../../../component/contato-info/form/ContatoInfoFormComponent.js';

export default class InstituicaoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.enderecoFormComponent = new EnderecoFormComponent( formNome, '', 'endereco_form_el' );
		this.contatoInfoFormComponent = new ContatoInfoFormComponent( formNome, '', 'contato_info_form_el' );
		
		super.addFilho( this.enderecoFormComponent );
		super.addFilho( this.contatoInfoFormComponent );		
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let instance = this;
		sistema.ajax( "GET", "/api/instituicao/get", {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaJSON( dados );						
			}
		} );
		
	}
		
	getJSON() {
		return {
			cnpj : super.getFieldValue( 'cnpj' ),
			razaoSocial : super.getFieldValue( 'razao_social' ),
			endereco : this.enderecoFormComponent.getJSON(),
			contatoInfo : this.contatoInfoFormComponent.getJSON()
		}
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'cnpj', dados.cnpj );
		super.setFieldValue( 'razao_social', dados.razaoSocial );
		this.enderecoFormComponent.carregaJSON( dados.endereco );
		this.contatoInfoFormComponent.carregaJSON( dados.contatoInfo );
	}	
		
	limpaForm() {
		super.setFieldValue( 'cnpj', "" );		
		super.setFieldValue( 'razao_social', "" );		
	}		
}
