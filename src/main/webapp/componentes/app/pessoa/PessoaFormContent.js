
import {sistema} from '../../../sistema/Sistema.js';
import {conversor} from '../../../sistema/util/Conversor.js';

import FormContent from '../../ext/FormContent.js';

import EnderecoFormContent from '../endereco/EnderecoFormContent.js';
import ContatoinfoFormContent from '../contatoinfo/ContatoinfoFormContent.js';

export default class PessoaFormContent extends FormContent {
	
	carregado_cpf = null;
	
	verificaCpf = () => {}; 					
						
	constructor( prefixo ) {
		super( prefixo, 'pessoa-form-content', 'pessoa_form_el', 'pessoa_mensagem_el' );
		
		this.enderecoFormContent = new EnderecoFormContent( prefixo );
		this.contatoinfoFormContent = new ContatoinfoFormContent( prefixo );
		
		super.addFilho( this.enderecoFormContent );
		super.addFilho( this.contatoinfoFormContent );		
	}	
			
	onHTMLCarregado() {								
		super.getEL( 'verificar_btn' ).onclick = this.verificarCpfBTNOnclick;				
		
		sistema.ajax( "GET", "/api/tipos/todos", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				super.getEL( "sexo_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.sexos, "Selecione o sexo" );
				super.getEL( "estado_civil_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.estadosCivis, "Selecione o estado civil" );
				super.getEL( "nacionalidade_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.nacionalidades, "Selecione a nacionalidade" );
				super.getEL( "raca_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.racas, "Selecione a raça" );
				super.getEL( "religiao_select_id" ).innerHTML = sistema.selectOptionsHTML( dados.religioes, "Selecione a religião" );		
			}
		} );													
	}	
		
	getJSON() {
		return {
			cpf : super.getFieldValue( 'cpf' ),
			rg : super.getFieldValue( 'rg' ),
			nome : super.getFieldValue( 'nome' ),
			nomeSocial : super.getFieldValue( 'nome_social' ),
			dataNascimento : conversor.formataData( super.getFieldValue( 'dataNascimento' ) ),
			sexo : super.getFieldValue( 'sexo' ),
			estadoCivil : super.getFieldValue( 'estado_civil' ),
			nacionalidade : super.getFieldValue( 'nacionalidade' ),
			raca : super.getFieldValue( 'raca' ),
			religiao : super.getFieldValue( 'religiao' ),
		
			endereco : this.enderecoFormContent.getJSON(),
			contatoinfo : this.contatoinfoFormContent.getJSON()
		};
	}
	
	carregaJSON( dados ) {
		this.carregado_cpf = dados.cpf;
				
		super.setFieldValue( 'cpf', dados.cpf );
		super.setFieldValue( 'rg', dados.rg );
		super.setFieldValue( 'nome', dados.nome );
		super.setFieldValue( 'nome_social', dados.nomeSocial );
		super.setFieldValue( 'data_nascimento', conversor.valorData( dados.dataNascimento ) );
		super.setFieldValue( 'sexo', dados.sexo );
		super.setFieldValue( 'estado_civil', dados.estadoCivil );
		super.setFieldValue( 'nacionalidade', dados.nacionalidade );
		super.setFieldValue( 'raca', dados.raca );
		super.setFieldValue( 'religiao', dados.religiao );
		
		this.enderecoFormContent.carregaJSON( dados.endereco );
		this.contatoinfoFormContent.carregaJSON( dados.contatoinfo );
	}		
	
	limpaForm() {
		super.setFieldValue( 'cpf', "" );
		super.setFieldValue( 'rg', "" );
		super.setFieldValue( 'nome', "" );
		super.setFieldValue( 'nome_social', "" );
		super.setFieldValue( 'data_nascimento', "" );
		super.setFieldValue( 'sexo', "" );
		super.setFieldValue( 'estado_civil', "" );
		super.setFieldValue( 'nacionalidade', "" );
		super.setFieldValue( 'raca', "" );
		super.setFieldValue( 'religiao', "" );		
	}
	
	verificarCpfBTNOnclick() {
		let cpf = super.getFieldValue( 'cpf' );
		
		if ( cpf.trim() === '' ) {
			this.mostraValidacaoErro( "Preencha o campo CPF." );
			return;	
		}
				
		if ( source.params.op === 'editar' && cpf == this.pessoaFormContent.carregado_cpf ) {
			this.mostraValidacaoInfo( "O campo CPF está como carregado." );
			return;
		}
				
		this.verificaCpf( cpf );
	}
	
	mostraValidacaoInfo( info ) {
		sistema.mostraMensagemInfo( super.prefixo + "validacao_cpf_mensagem_el", info );			
	}
	
	mostraValidacaoErro( erro ) {
		sistema.mostraMensagemInfo( super.prefixo + "validacao_cpf_mensagem_el", erro );	
	}
	
}
