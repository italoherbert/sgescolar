
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import FuncionarioFormComponent from '../../../component/funcionario/form/FuncionarioFormComponent.js';

export default class SecretarioFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.funcionarioFormComponent = new FuncionarioFormComponent( formNome, '', 'funcionario_form_el' );
		
		this.funcionarioFormComponent.usuarioFormComponent.carregaPerfis = ( sel_elid, onparams ) => this.carregaUsuarioPerfis( sel_elid, onparams );
				
		super.addFilho( this.funcionarioFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
			
		const instance = this;
		if ( this.globalParams.op === 'editar' ) {
			sistema.ajax( "GET", "/api/secretario/get/"+this.globalParams.secretarioId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		} else {			
			selectService.carregaInstituicoesSelect( 'instituicoes_select', {
				onchange : () => {
					let instituicaoId = instance.getFieldValue( 'instituicao' );
					selectService.carregaEscolasSelect( instituicaoId, 'escolas_select' );						
				}
			} );
		}
	}
			
	carregaUsuarioPerfis( select_elid, onparams ) {
		selectService.carregaSecretarioPerfisSelect( select_elid, onparams );			
	}
			
	getJSON() {
		return {
			funcionario : this.funcionarioFormComponent.getJSON(),
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.escola.instituicao.id );
				selectService.carregaEscolasSelect( dados.escola.instituicao.id, 'escolas_select', {
					onload : () => {
						instance.setFieldValue( 'escola', dados.escola.id );
					}
				} );						
			}
		} );
		this.funcionarioFormComponent.carregaJSON( dados.funcionario );
	}	
	
	
	limpaForm() {
		super.setFieldValue( 'instituicao', '0' );
		super.setFieldValue( 'escola', '0' );		
	}	
								
}
