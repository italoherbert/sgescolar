
import {sistema} from '../../../sistema/Sistema.js';
import {conversor} from '../../../sistema/util/Conversor.js';

import FormComponent from '../../component/FormComponent.js';

import EnderecoFormComponent from '../endereco/EnderecoFormComponent.js';
import ContatoinfoFormComponent from '../contatoinfo/ContatoinfoFormComponent.js';

export default class PessoaFormComponent extends FormComponent {
	
	carregado_cpf = null;
	
	verificaCpf = () => {}; 					
						
	constructor( formNome, prefixo ) {
		super( formNome, prefixo, 'pessoa-form', 'pessoa_form_el', 'pessoa_mensagem_el' );
		
		this.enderecoFormComponent = new EnderecoFormComponent( formNome, prefixo );
		this.contatoinfoFormComponent = new ContatoinfoFormComponent( formNome, prefixo );
		
		super.addFilho( this.enderecoFormComponent );
		super.addFilho( this.contatoinfoFormComponent );		
	}	
			
	onHTMLCarregado() {
		const instance = this;								
		super.getEL( 'verificar_btn' ).onclick = (e) => {			
			instance.verificarCpfBTNOnclick( e );
		};			
		
		sistema.ajax( "GET", "/api/tipos/todos", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
								
				let sexo_select_options = sistema.selectOptionsHTML( dados.sexos, "<option value=\"0\">Selecione o sexo</option>" );
				let estado_civil_select_options = sistema.selectOptionsHTML( dados.estadosCivis, "<option value=\"0\">Selecione o estado civil</option>" );
				let nacionalidade_select_options = sistema.selectOptionsHTML( dados.nacionalidades, "<option value=\"0\">Selecione a nacionalidade</option>" );
				let raca_select_options = sistema.selectOptionsHTML( dados.racas, "<option value=\"0\">Selecione a raça</option>" );
				let religiao_select_options = sistema.selectOptionsHTML( dados.religioes, "<option value=\"0\">Selecione a religião</option>" );
				
				super.getEL( "sexo_select_id" ).innerHTML = sexo_select_options;
				super.getEL( "estado_civil_select_id" ).innerHTML = estado_civil_select_options;
				super.getEL( "nacionalidade_select_id" ).innerHTML = nacionalidade_select_options;
				super.getEL( "raca_select_id" ).innerHTML = raca_select_options;
				super.getEL( "religiao_select_id" ).innerHTML = religiao_select_options;		
			}
		} );													
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
			contatoInfo : this.contatoinfoFormComponent.getJSON()
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
		this.contatoinfoFormComponent.carregaJSON( dados.contatoInfo );
	}		
	
	limpaForm() {
		super.setFieldValue( 'cpf', "" );
		super.setFieldValue( 'rg', "" );
		super.setFieldValue( 'nome', "" );
		super.setFieldValue( 'nome_social', "" );
		super.setFieldValue( 'data_nascimento', "" );
		
		super.setFieldValue( "sexo_select_id", "0" );		
		super.setFieldValue( "estado_civil_select_id", "0" );		
		super.setFieldValue( "nacionalidade_select_id", "0" );		
		super.setFieldValue( "raca_select_id", "0" );
		super.setFieldValue( "religiao_select_id", "0" );;	
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
