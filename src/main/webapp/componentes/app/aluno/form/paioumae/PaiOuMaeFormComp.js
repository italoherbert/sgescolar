
import {sistema} from '../../../../../sistema/Sistema.js';

import FormComp from '../../../../../sistema/comp/FormComp.js';
import PessoaFormComp from '../../../pessoa/PessoaFormComp.js';

export default class PaiOuMaeFormComp extends FormComp {
				
	constructor( prefixo ) {
		super( prefixo, 'pai-ou-mae-form-comp', 'modal_form_el', 'modal_mensagem_el' );

		this.pessoaFormComp = new PessoaFormComp( prefixo );		
		this.pessoaFormComp.verificaCpf = (cpf) => this.carregaPorCpf( cpf );
		
		super.addFilho( this.pessoaFormComp );	
	}	
				
	getJSON() {
		return {
			falecido : super.getFieldChecked( 'falecido' ),			
		
			pessoa : this.pessoaFormComp.getJSON()
		};
	}
	
	carregaJSON( dados ) {		
		this.setFieldChecked( 'falecido', dados.falecido == 'true' ? true : false );
				
		this.pessoaFormComp.carregaJSON( dados.pessoa );
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
						instance.pessoaFormComp.carregaJSON( dados.pessoa );
					} else {
						instance.carregaJSON( dados.pessoaPaiOuMae );
					}	
				} else {
					instance.pessoaFormComp.mostraValidacaoInfo( "Ok! Ninguém registrado com o cpf informado." );
				}
											
			},
			erro : function( msg ) {
				instance.pessoaFormComp.mostraValidacaoErro( msg );	
			}
		} );
	}
	
}