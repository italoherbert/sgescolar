
import {sistema} from '../../../../../sistema/Sistema.js';

import FormComponent from '../../../../component/FormComponent.js';
import PessoaFormComponent from '../../../../component/pessoa/form/PessoaFormComponent.js';

export default class ResponsavelFormComponent extends FormComponent {
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'responsavel-form', compELIDSufixo, 'modal_mensagem_el' );

		this.pessoaFormComponent = new PessoaFormComponent( formNome, prefixo, "pessoa_form_el" );		
		this.pessoaFormComponent.verificaCpf = (cpf) => this.carregaPorCpf( cpf );
		
		super.addFilho( this.pessoaFormComponent );	
	}	
						
	getJSON() {
		return {
			falecido : super.getFieldChecked( 'falecido' ),			
		
			pessoa : this.pessoaFormComponent.getJSON()
		};
	}
	
	carregaJSON( dados ) {
		this.setFieldChecked( 'falecido', dados.falecido == 'true' ? true : false );
				
		this.pessoaFormComponent.carregaJSON( dados.pessoa );
	}
			
	limpaForm() {
		this.setFieldChecked( 'falecido', false );
	}		
			
	carregaPorCpf( cpf ) {										
		const instance = this;
		sistema.ajax( "GET", "/api/responsavel/busca/cpf/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				if ( dados.pessoaEncontrada == 'true' || dados.pessoaResponsavelEncontrado == 'true' ) {
					if ( dados.pessoaEncontrada == 'true' ) {
						instance.pessoaFormComponent.carregaJSON( dados.pessoa );
					} else {
						instance.carregaJSON( dados.pessoaResponsavel );
					}	
				} else {
					instance.pessoaFormComponent.mostraValidacaoInfo( "Ok! Ninguém registrado com o cpf informado." );
				}
											
			},
			erro : function( msg ) {
				instance.pessoaFormComponent.mostraValidacaoErro( msg );	
			}
		} );
	}
	
}