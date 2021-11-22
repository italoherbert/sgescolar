
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import {selectService} from '../../../service/SelectService.js';

import FormComponent from '../../FormComponent.js';

import EnderecoFormComponent from '../../endereco/form/EnderecoFormComponent.js';
import ContatoInfoFormComponent from '../../contato-info/form/ContatoInfoFormComponent.js';

export default class PessoaFormComponent extends FormComponent {
	
	carregado_cpf = null;
	
	verificaCpf = () => {}; 					
						
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'pessoa-form', compELIDSufixo, 'pessoa_mensagem_el' );
		
		this.enderecoFormComponent = new EnderecoFormComponent( formNome, prefixo, 'endereco_form_el' );
		this.contatoInfoFormComponent = new ContatoInfoFormComponent( formNome, prefixo, 'contato_info_form_el' );
		
		super.addFilho( this.enderecoFormComponent );
		super.addFilho( this.contatoInfoFormComponent );		
	}	
			
	onHTMLCarregado() {
		super.getEL( 'verificar_btn' ).onclick = (e) => this.verificarCpfBTNOnclick( e );			
		
		selectService.carregaSexosSelect( super.getELID( 'sexo_select' ) );
		selectService.carregaEstadosCivisSelect( super.getELID( 'estado_civil_select' ) );
		selectService.carregaNacionalidadesSelect( super.getELID( 'nacionalidade_select' ) );
		selectService.carregaRacasSelect( super.getELID( 'raca_select' ) );
		selectService.carregaReligioesSelect( super.getELID( 'religiao_select' ) );												
	}	
		
	getJSON() {
		return {
			cpf : super.getFieldValue( 'cpf' ),
			rg : super.getFieldValue( 'rg' ),
			nome : super.getFieldValue( 'nome' ),
			nomeSocial : super.getFieldValue( 'nome_social' ),
			dataNascimento : conversor.formataData( super.getFieldValue( 'data_nascimento' ) ),
			sexo : super.getFieldValue( 'sexo' ),
			estadoCivil : super.getFieldValue( 'estado_civil' ),
			nacionalidade : super.getFieldValue( 'nacionalidade' ),
			raca : super.getFieldValue( 'raca' ),
			religiao : super.getFieldValue( 'religiao' ),
		
			endereco : this.enderecoFormComponent.getJSON(),
			contatoInfo : this.contatoInfoFormComponent.getJSON()
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
		
		this.enderecoFormComponent.carregaJSON( dados.endereco );
		this.contatoInfoFormComponent.carregaJSON( dados.contatoInfo );
	}		
	
	limpaForm() {
		super.setFieldValue( 'cpf', "" );
		super.setFieldValue( 'rg', "" );
		super.setFieldValue( 'nome', "" );
		super.setFieldValue( 'nome_social', "" );
		super.setFieldValue( 'data_nascimento', "" );
		
		super.setFieldValue( "sexo", "0" );		
		super.setFieldValue( "estado_civil", "0" );		
		super.setFieldValue( "nacionalidade", "0" );		
		super.setFieldValue( "raca", "0" );
		super.setFieldValue( "religiao", "0" );;	
	}
	
	verificarCpfBTNOnclick( e ) {
		this.limpaValidacaoMensagem();
		
		let cpf = super.getFieldValue( 'cpf' );
		
		if ( cpf.trim() === '' ) {
			this.mostraValidacaoErro( "Preencha o campo CPF." );
			return;	
		}
				
		if ( this.globalParams.op === 'editar' && cpf == this.carregado_cpf ) {
			this.mostraValidacaoInfo( "O campo CPF está como carregado." );
			return;
		}
				
		this.verificaCpf( cpf );
	}
	
	mostraValidacaoInfo( info ) {
		sistema.mostraMensagemInfo( this.prefixo + "validacao_cpf_mensagem_el", info );			
	}
	
	mostraValidacaoErro( erro ) {
		sistema.mostraMensagemErro( this.prefixo + "validacao_cpf_mensagem_el", erro );	
	}
	
	limpaValidacaoMensagem() {
		sistema.limpaMensagem( this.prefixo + "validacao_cpf_mensagem_el" );					
	}
	
}
