
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'

import FormComponent from '../../../component/FormComponent.js';

export default class EnderecoFormComponent extends FormComponent {
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'endereco-form', compELIDSufixo, 'endereco_mensagem_el' );
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
			municipio : super.getFieldValue( 'municipio' ),
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
		let cidade_el = super.getELID( 'municipio_sel_el' );
				
		wsLocalidades.carregaEstados( uf_el, cidade_el, {
			estadosDefaultOption : "<option value=\"0\">Selecione o estado</option>",
			municipiosDefaultOption : "<option value=\"0\">Selecione o município</option>",
			estadosCarregados : ( respDados ) => {
				instance.setFieldValue( 'uf' , dados.uf );
				instance.getField( 'uf' ).onchange();
			},
			municipiosCarregados : ( respDados ) => {
				instance.setFieldValue( 'municipio', dados.municipio );
			}
		} );
	}
	
	limpaForm() {
		super.setFieldValue( 'logradouro' , "" );
		super.setFieldValue( 'complemento' , "" );
		super.setFieldValue( 'bairro' , "" );
		super.setFieldValue( 'cep' , "" );
		
		super.setFieldValue( "uf_sel_el", "0" );	
		super.setFieldValue( "municipio_sel_el", "0" );		
	}
		
}
