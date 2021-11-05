
import {wsLocalidades} from '../../../sistema/WSLocalidades.js'

import FormComp from '../../../sistema/comp/FormComp.js';

export default class EnderecoFormComp extends FormComp {
				
	constructor( prefixo ) {
		super( prefixo, 'endereco-form-comp', 'endereco_form_el', 'endereco_mensagem_el' );
	}
	
	onHTMLCarregado() {
		let uf_el = super.getELID( 'uf_sel_el' );
		let cidade_el = super.getELID( 'cidade_sel_el' );
		
		wsLocalidades.carregaEstados( uf_el, cidade_el, {
			estadosDefaultOption : "<option value=\"0\">Selecione o estado</option>",
			municipiosDefaultOption : "<option value=\"0\">Selecione o município</option>"
		} );
	}	
				
	getJSON() {
		return {
			logradouro : super.getFieldValue( 'logradouro' ),
			complemento : super.getFieldValue( 'complemento' ),
			bairro : super.getFieldValue( 'bairro' ),
			cidade : super.getFieldValue( 'cidade' ),
			uf : super.getFieldValue( 'uf' ),	
			cep : super.getFieldValue( 'cep' )		
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'logradouro' , dados.logradouro );
		super.setFieldValue( 'complemento' , dados.complemento );
		super.setFieldValue( 'bairro' , dados.bairro );
		super.setFieldValue( 'cep' , dados.cep );
		
		const instance = this;
		
		let uf_el = super.getELID( 'uf_sel_el' );
		let cidade_el = super.getELID( 'cidade_sel_el' );
				
		wsLocalidades.carregaEstados( uf_el, cidade_el, {
			estadosDefaultOption : "<option value=\"0\">Selecione o estado</option>",
			municipiosDefaultOption : "<option value=\"0\">Selecione o município</option>",
			estadosCarregados : ( respDados ) => {
				instance.setFieldValue( 'uf' , dados.uf );
				instance.getField( 'uf' ).onchange();
			},
			municipiosCarregados : ( respDados ) => {
				instance.setFieldValue( 'cidade', dados.cidade );
			}
		} );
	}
	
	limpaForm() {
		super.setFieldValue( 'logradouro' , "" );
		super.setFieldValue( 'complemento' , "" );
		super.setFieldValue( 'bairro' , "" );
		super.setFieldValue( 'cep' , "" );
		
		super.setFieldValue( "uf_sel_el", "0" );	
		super.setFieldValue( "cidade_sel_el", "0" );		
	}
		
	/*
	carregaCidades( estadoID, municipiosCarregadosFunc ) {
		const instance = this;
		wsLocalidades.carregaMunicipios( estadoID, super.prefixo + "cidade_sel_el", {
			municipiosCarregados : municipiosCarregadosFunc,
			municipiosNaoCarregados : ( erroMsg ) => {
				instance.mostraErro( super.prefixo + " " + erroMsg );
			}
		} )
	}
		
	carregaEstados( prefixo, estadosCarregadosFunc ) {
		wsLocalidades.carregaEstados( prefixo+'uf_sel_el', prefixo+'cidade_sel_el', {
			estadosCarregados : estadosCarregadosFunc,
			estadosNaoCarregados : ( erroMsg ) => {
				sistema.mostraMensagemErro( 'mensagem_el', prefixo+" - "+erroMsg );
			}
		} );	
	}
	*/
}
