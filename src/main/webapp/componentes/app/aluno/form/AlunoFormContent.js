
import {sistema} from '../../../../sistema/Sistema.js';

import PessoaFormContent from '../../pessoa/PessoaFormContent.js';
import UsuarioFormContent from '../../usuario/UsuarioFormContent.js';

import ResumoPaiOuMaeFormContent from './paioumae/ResumoPaiOuMaeFormContent.js';
import FormContent from '../../../ext/FormContent.js';

export default class AlunoFormContent extends FormContent {
										
	constructor() {
		super( '', '', '', 'mensagem_el' );
		
		this.pessoaFormContent = new PessoaFormContent( 'aluno_' );
		this.usuarioFormContent = new UsuarioFormContent( 'aluno_' );
		this.resumoPaiFormContent = new ResumoPaiOuMaeFormContent( 'pai_' );
		this.resumoMaeFormContent = new ResumoPaiOuMaeFormContent( 'mae_' );
		
		super.addFilho( this.pessoaFormContent );
		super.addFilho( this.usuarioFormContent );
		super.addFilho( this.resumoPaiFormContent );
		super.addFilho( this.resumoMaeFormContent );
		
		this.pessoaFormContent.verificaCpf = this.verificaCpf;

		super.carregarConteudoHTML = false;		
		super.carregouHTMLCompleto = this.carregouTudo;
	}			
	
	carregouTudo() {
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/aluno/get/"+this.globalParams.alunoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );
						
					instance.mostraInfo( 'Pessoa carregada com êxito.' );																
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		}
	}
						
	verificaCpf() {
		let cpf = document.aluno_form.aluno_cpf.value;
		if ( cpf.trim() === '' ) {
			this.pessoaFormContent.mostraErro( "Preencha o campo CPF." );
			return;	
		}
		
		if ( this.params.op === 'editar' && cpf === this.params.editar_op_aluno_cpf ) {
			this.pessoaFormContent.mostraInfo( "O campo CPF está como carregado." );
			return;
		}
		
		const instance = this;	
			
		sistema.ajax( "GET", "/api/pessoa/cpf/disponivel/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				instance.pessoaFormContent.mostraInfo( "Ok! Ninguém registrado com o cpf informado." );							
			},
			erro : function( msg ) {
				instance.pessoaFormContent.mostraErro( msg );	
			}
		} );
	}	
		
	getJSON() {
		return {
			pessoa : this.pessoaFormContent.getJSON(),
			usuario : this.usuarioFormContent.getJSON(),
			pai : this.resumoPaiFormContent.getJSON(),
			mae : this.resumoMaeFormContent.getJSON() 
		}
	}	
		
	carregaJSON( dados ) {
		this.pessoaFormContent.carregaJSON( dados.pessoa );
		this.usuarioFormContent.carregaJSON( dados.usuario );
		this.resumoPaiFormContent.carregaJSON( dados.pai );
		this.resumoMaeFormContent.carregaJSON( dados.mae );	
	}	
			
}
