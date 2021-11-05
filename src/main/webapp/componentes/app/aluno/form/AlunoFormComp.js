
import {sistema} from '../../../../sistema/Sistema.js';

import PessoaFormComp from '../../pessoa/PessoaFormComp.js';
import UsuarioFormComp from '../../usuario/UsuarioFormComp.js';

import FormComp from '../../../../sistema/comp/FormComp.js';
import ResumoPaiOuMaeFormComp from './paioumae/ResumoPaiOuMaeFormComp.js';

export default class AlunoFormComp extends FormComp {
										
	constructor() {
		super( '', '', '', 'mensagem_el' );
		
		this.pessoaFormComp = new PessoaFormComp( 'aluno_' );
		this.usuarioFormComp = new UsuarioFormComp( 'aluno_' );
		this.resumoPaiFormComp = new ResumoPaiOuMaeFormComp( 'pai_' );
		this.resumoMaeFormComp = new ResumoPaiOuMaeFormComp( 'mae_' );
		
		super.addFilho( this.pessoaFormComp );
		super.addFilho( this.usuarioFormComp );
		super.addFilho( this.resumoPaiFormComp );
		super.addFilho( this.resumoMaeFormComp );
		
		this.pessoaFormComp.verificaCpf = (cpf) => this.verificaCpfConflito( cpf );

		super.carregarConteudoHTML = false;		
		super.carregouHTMLCompleto = this.carregouTudo;
	}			
			
	carregouTudo() {
		super.limpaTudo();
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/aluno/get/"+this.globalParams.alunoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		}
	}
						
	verificaCpfConflito( cpf ) {				
		const instance = this;	
			
		sistema.ajax( "GET", "/api/pessoa/cpf/disponivel/"+cpf, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			sucesso : function( resposta ) {
				instance.pessoaFormComp.mostraValidacaoInfo( "Ok! Ninguém registrado com o cpf informado." );							
			},
			erro : function( msg ) {
				instance.pessoaFormComp.mostraValidacaoErro( msg );	
			}
		} );
	}	
		
	getJSON() {
		return {
			pessoa : this.pessoaFormComp.getJSON(),
			usuario : this.usuarioFormComp.getJSON(),
			pai : this.resumoPaiFormComp.getJSON(),
			mae : this.resumoMaeFormComp.getJSON() 
		}
	}	
		
	carregaJSON( dados ) {
		this.pessoaFormComp.carregaJSON( dados.pessoa );
		this.usuarioFormComp.carregaJSON( dados.usuario );
		this.resumoPaiFormComp.carregaJSON( dados.pai );
		this.resumoMaeFormComp.carregaJSON( dados.mae );	
	}	
				
}
