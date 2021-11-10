
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import EnderecoFormComponent from '../../../component/endereco/form/EnderecoFormComponent.js';
import ContatoInfoFormComponent from '../../../component/contato-info/form/ContatoInfoFormComponent.js';

export default class EscolaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.enderecoFormComponent = new EnderecoFormComponent( formNome, '', 'endereco_form_el' );
		this.contatoInfoFormComponent = new ContatoInfoFormComponent( formNome, '', 'contato_info_form_el' );
		
		super.addFilho( this.enderecoFormComponent );
		super.addFilho( this.contatoInfoFormComponent );		
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/escola/get/"+this.globalParams.escolaId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		}
	}
		
	getJSON() {
		return {
			nome : super.getFieldValue( 'nome' ),
			endereco : this.enderecoFormComponent.getJSON(),
			contatoInfo : this.contatoInfoFormComponent.getJSON()
		}
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'nome', dados.nome );
		this.enderecoFormComponent.carregaJSON( dados.endereco );
		this.contatoInfoFormComponent.carregaJSON( dados.contatoInfo );
	}	
				
}
