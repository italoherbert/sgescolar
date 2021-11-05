
import {sistema} from '../../../../../sistema/Sistema.js';

import FormComponent from '../../../../component/FormComponent.js';
import PessoaFormComponent from '../../../pessoa/PessoaFormComponent.js';

export default class PaiOuMaeFormComponent extends FormComponent {
				
	constructor( formNome, prefixo ) {
		super( formNome, prefixo, 'pai-ou-mae-form', 'modal_form_el', 'modal_mensagem_el' );

		this.pessoaFormComponent = new PessoaFormComponent( formNome, prefixo );		
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
		sistema.ajax( "GET", "/api/paioumae/busca/cpf/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				if ( dados.pessoaEncontrada == 'true' || dados.pessoaPaiOuMaeEncontrado == 'true' ) {
					if ( dados.pessoaEncontrada == 'true' ) {
						instance.pessoaFormComponent.carregaJSON( dados.pessoa );
					} else {
						instance.carregaJSON( dados.pessoaPaiOuMae );
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