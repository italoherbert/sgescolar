
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import EnderecoLocalFormComponent from '../../../component/endereco-local/form/EnderecoLocalFormComponent.js';
import ContatoInfoFormComponent from '../../../component/contato-info/form/ContatoInfoFormComponent.js';

export default class EscolaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.enderecoLocalFormComponent = new EnderecoLocalFormComponent( formNome, '', 'endereco_form_el' );
		this.contatoInfoFormComponent = new ContatoInfoFormComponent( formNome, '', 'contato_info_form_el' );
		
		super.addFilho( this.enderecoLocalFormComponent );
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
			enderecoLocal : this.enderecoLocalFormComponent.getJSON(),
			contatoInfo : this.contatoInfoFormComponent.getJSON()
		}
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'nome', dados.nome );
		
		perfilService.setInstituicaoID( dados.instituicao.id );
				
		this.enderecoLocalFormComponent.carregaJSON( dados.enderecoLocal );
		this.contatoInfoFormComponent.carregaJSON( dados.contatoInfo );
	}	
		
	limpaForm() {
		super.setFieldValue( 'nome', "" );		
	}		
}
