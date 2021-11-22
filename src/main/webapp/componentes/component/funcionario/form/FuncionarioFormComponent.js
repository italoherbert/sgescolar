
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import FormComponent from '../../FormComponent.js';

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
		selectService.carregaEscolaridadesSelect( super.getELID( 'escolaridade_select' ) );
		selectService.carregaFuncionarioFuncoesSelect( super.getELID( 'funcao_select' ) );														
	}	
		
	getJSON() {
		return {
			codigoInep : super.getFieldValue( 'codigo_inep' ),
			escolaFunc : super.getFieldChecked( 'escola_func' ),
			cargaHoraria : super.getFieldValue( 'carga_horaria' ),
			escolaridade : super.getFieldValue( 'escolaridade' ),
			funcao : super.getFieldValue( 'funcao' ),			
		
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
		super.setFieldValue( 'funcao', dados.funcao );
				
		this.pessoaFormComponent.carregaJSON( dados.pessoa );
		this.usuarioFormComponent.carregaJSON( dados.usuario );
	}		
	
	limpaForm() {
		super.setFieldValue( 'codigo_inep', "" );
		super.setFieldChecked( 'escola_func', true );
		super.setFieldValue( 'carga_horaria', "" );
		super.setFieldValue( 'escolaridade', "0" );		
		super.setFieldValue( 'funcao', '0' );		
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
