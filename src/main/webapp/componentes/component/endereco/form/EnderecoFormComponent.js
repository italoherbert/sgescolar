
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'

import FormComponent from '../../FormComponent.js';

export default class EnderecoFormComponent extends FormComponent {
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'endereco-form', compELIDSufixo, 'endereco_mensagem_el' );
	}
	
	onHTMLCarregado() {
		let uf_el = super.getELID( 'uf_select' );
		let municipio_el = super.getELID( 'municipio_select' );
		
		wsLocalidades.carregaEstados( uf_el, municipio_el, {
			estadosDefaultOption : { texto : 'Selecione o estado', valor : '-1' },
			municipiosDefaultOption : { texto : 'Selecione o município', valor : '-1' }
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
		
		let uf_el = super.getELID( 'uf_select' );
		let municipio_el = super.getELID( 'municipio_select' );
				
		wsLocalidades.carregaEstados( uf_el, municipio_el, {
			estadosDefaultOption : { texto : 'Selecione o estado', valor : '-1' },
			municipiosDefaultOption : { texto : 'Selecione o município', valor : '-1' },
			estadosCarregados : ( respDados ) => {
				instance.setSelectFieldValue( 'uf' , dados.uf );
				instance.getField( 'uf' ).onchange();
			},
			municipiosCarregados : ( respDados ) => {
				instance.setSelectFieldValue( 'municipio', dados.municipio );
			}
		} );
	}
	
	limpaForm() {
		super.setFieldValue( 'logradouro' , "" );
		super.setFieldValue( 'complemento' , "" );
		super.setFieldValue( 'bairro' , "" );
		super.setFieldValue( 'cep' , "" );
		
		super.setFieldValue( "uf", "-1" );	
		super.setFieldValue( "municipio", "-1" );		
	}
		
}
