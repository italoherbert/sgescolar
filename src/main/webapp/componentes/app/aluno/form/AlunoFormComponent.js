
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import PessoaFormComponent from '../../pessoa/PessoaFormComponent.js';
import UsuarioFormComponent from '../../usuario/UsuarioFormComponent.js';
import ResumoPaiOuMaeFormComponent from './paioumae/ResumoPaiOuMaeFormComponent.js';

export default class AlunoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome );
		super.mensagemElementoSufixo = 'mensagem_el';
		
		this.pessoaFormComponent = new PessoaFormComponent( formNome, 'aluno_' );
		this.usuarioFormComponent = new UsuarioFormComponent( formNome, 'aluno_' );
		this.resumoPaiFormComponent = new ResumoPaiOuMaeFormComponent( formNome, 'pai_' );
		this.resumoMaeFormComponent = new ResumoPaiOuMaeFormComponent( formNome, 'mae_' );
		
		super.addFilho( this.pessoaFormComponent );
		super.addFilho( this.usuarioFormComponent );
		super.addFilho( this.resumoPaiFormComponent );
		super.addFilho( this.resumoMaeFormComponent );
		
		this.pessoaFormComponent.verificaCpf = (cpf) => this.verificaCpfConflito( cpf );

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
				instance.pessoaFormComponent.mostraValidacaoInfo( "Ok! Ninguém registrado com o cpf informado." );							
			},
			erro : function( msg ) {
				instance.pessoaFormComponent.mostraValidacaoErro( msg );	
			}
		} );
	}	
		
	getJSON() {
		return {
			pessoa : this.pessoaFormComponent.getJSON(),
			usuario : this.usuarioFormComponent.getJSON(),
			pai : this.resumoPaiFormComponent.getJSON(),
			mae : this.resumoMaeFormComponent.getJSON() 
		}
	}	
		
	carregaJSON( dados ) {
		this.pessoaFormComponent.carregaJSON( dados.pessoa );
		this.usuarioFormComponent.carregaJSON( dados.usuario );
		this.resumoPaiFormComponent.carregaJSON( dados.pai );
		this.resumoMaeFormComponent.carregaJSON( dados.mae );	
	}	
				
}
