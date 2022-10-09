
import {sistema} from '../../../../sistema/Sistema.js';
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'
import {wsCEP} from '../../../../sistema/WSCEP.js'

import {mascaraUtil} from '../../../../sistema/util/MascaraUtil.js';

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

		const instance = this;						
		super.getEL( 'consultar_cep_btn' ).onclick = () => {
			let cep = super.getFieldValue( 'cep' );
			instance.carregaEnderecoPorCep( cep );		
		};		
		
		super.getEL( 'cep' ).addEventListener( 'input', ( e ) => mascaraUtil.oninputCEP( e ) );
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
				
	carregaEnderecoPorCep( cep ) {
		super.limpaMensagem();
		
		cep = cep.replace( '-', '' );				
														
		const instance = this;				
		wsCEP.consultaCEP( cep , {
			houveSucesso : ( dados ) => {	
				instance.setFieldValue( 'logradouro', dados.logradouro );
				instance.setFieldValue( 'complemento', dados.complemento );
				instance.setFieldValue( 'bairro', dados.bairro );
				
				let uf_el = super.getELID( 'uf_select' );
				let municipio_el = super.getELID( 'municipio_select' );
						
				wsLocalidades.carregaEstados( uf_el, municipio_el, {
					estadosDefaultOption : { texto : 'Selecione o estado', valor : '-1' },
					municipiosDefaultOption : { texto : 'Selecione o município', valor : '-1' },
					estadosCarregados : ( respDados ) => {
						instance.setSelectFieldByText( 'uf' , dados.uf );
						instance.getField( 'uf' ).onchange();
					},
					municipiosCarregados : ( respDados ) => {
						instance.setSelectFieldByText( 'municipio', dados.localidade );
					}
				} );
							
				sistema.mostraMensagemInfo( this.prefixo + 'endereco_mensagem_el', 'CEP consultado com sucesso.', false ); 
			},
			houveErro : ( msg ) => {
				instance.mostraErro( msg );				
			}
		} );	
	}		
		
}
