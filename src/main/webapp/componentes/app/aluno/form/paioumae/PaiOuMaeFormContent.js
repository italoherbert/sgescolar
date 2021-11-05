
import {sistema} from '../../../../../sistema/Sistema.js';

import PessoaFormContent from '../../../pessoa/PessoaFormContent.js';
import FormContent from '../../../../ext/FormContent.js';

export default class PaiOuMaeFormContent extends FormContent {
				
	constructor( prefixo ) {
		super( prefixo, 'pai-ou-mae-form-content', 'modal_form_el', 'modal_mensagem_el' );

		this.pessoaFormContent = new PessoaFormContent( prefixo );		
		this.pessoaFormContent.verificaCpf = this.verificaCpfConflito;
		
		super.addFilho( this.pessoaFormContent );	
	}	
				
	getJSON() {
		return {
			falecido : super.getFieldChecked( 'falecido' ),			
		
			pessoa : this.pessoaFormContent.getJSON()
		};
	}
	
	carregaJSON( dados ) {		
		this.setFieldChecked( 'falecido', dados.falecido == 'true' ? true : false );
				
		this.pessoaFormContent.carregaJSON( dados.pessoa );
	}
			
	limpaForm() {
		this.setFieldChecked( 'falecido', false );
	}		
			
	verificaCpfConflito() {				
		let cpf = super.getFieldValue( 'cpf' );
				
		if ( this.pessoaFormContent.validaVerificaCpf() === false ) 
			return; 				
						
		const instance = this;
		sistema.ajax( "GET", "/api/paioumae/busca/cpf/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				if ( dados.pessoaEncontrada == 'true' || dados.pessoaPaiOuMaeEncontrado == 'true' ) {
					if ( dados.pessoaEncontrada == 'true' ) {
						instance.carregaPessoaPai( dados.pessoa );
					} else {
						instance.carregaPai( dados.pessoaPaiOuMae );
					}	
				} else {
					instance.pessoaFormContent.mostraValidacaoInfo( "Ok! Ninguém registrado com o cpf informado." );
				}
											
			},
			erro : function( msg ) {
				instance.pessoaFormContent.mostraValidacaoErro( msg );	
			}
		} );
	}
	
}