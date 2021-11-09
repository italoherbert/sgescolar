
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import FormComponent from '../../../component/FormComponent.js';

import PessoaFormComponent from '../../pessoa/form/PessoaFormComponent.js';
import UsuarioFormComponent from '../../usuario/form/UsuarioFormComponent.js';

export default class FuncionarioFormComponent extends FormComponent {
	
	carregado_cpf = null;				
						
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'funcionario-form', compELIDSufixo, 'funcionario_mensagem_el' );
		
		this.pessoaFormComponent = new PessoaFormComponent( formNome, prefixo, 'pessoa_form_el' );
		this.usuarioFormComponent = new UsuarioFormComponent( formNome, prefixo, 'usuario_form_el' );
		
		this.pessoaFormComponent.verificaCpf = (cpf) => this.verificaCpfConflito( cpf );		
		
		super.addFilho( this.pessoaFormComponent );
		super.addFilho( this.usuarioFormComponent );		
	}	
			
	onHTMLCarregado() {					
		sistema.ajax( "GET", "/api/tipos/escolaridades", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
								
				super.getEL( "escolaridade_select" ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : dados, 
					defaultOption : { texto : 'Selecione a escolaridade', valor : '0' }
				} );										
			}
		} );													
	}	
		
	getJSON() {
		return {
			codigoInep : super.getFieldValue( 'codigo_inep' ),
			escolaFunc : super.getFieldChecked( 'escola_func' ),
			cargaHoraria : super.getFieldValue( 'carga_horaria' ),
			escolaridade : super.getFieldValue( 'escolaridade' ),			
		
			pessoa : this.pessoaFormComponent.getJSON(),
			usuario : this.usuarioFormComponent.getJSON()
		};
	}
	
	carregaJSON( dados ) {
		this.carregado_cpf = dados.cpf;
						
		super.setFieldValue( 'codigo_inep', dados.codigoInep );
		super.setFieldChecked( 'escola_func', ( dados.escolaFunc === 'true' ? true : false ) );
		super.setFieldValue( 'carga_horaria', dados.cargaHoraria );
		super.setFieldValue( 'escolaridade', dados.escolaridade );		
		
		this.pessoaFormComponent.carregaJSON( dados.pessoa );
		this.usuarioFormComponent.carregaJSON( dados.usuario );
	}		
	
	limpaForm() {
		super.setFieldValue( 'codigo_inep', "" );
		super.setFieldChecked( 'escola_func', true );
		super.setFieldValue( 'carga_horaria', "" );
		super.setFieldValue( 'escolaridade', "0" );				
	}
	
	verificaCpfConflito( cpf ) {				
		const instance = this;	
			
		sistema.ajax( "GET", "/api/pessoa/cpf/disponivel/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				instance.pessoaFormComponent.mostraValidacaoInfo( "Ok! Ninguém registrado com o cpf informado." );							
			},
			erro : function( msg ) {
				instance.pessoaFormComponent.mostraValidacaoErro( msg );	
			}
		} );
	}
	
}
