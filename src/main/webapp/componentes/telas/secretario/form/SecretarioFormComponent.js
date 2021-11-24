
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
			
		selectService.carregaEscolasSelect( 'escolas_select', { onload : () => this.tudoCarregado() } );		
	}
	
	tudoCarregado() {
		if ( this.globalParams.op === 'editar' ) {
			const instance = this;
			sistema.ajax( "GET", "/api/secretario/get/"+this.globalParams.secretarioId, {
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
		
	carregaUsuarioPerfis( select_elid, onparams ) {
		selectService.carregaSecretarioPerfisSelect( select_elid, onparams );			
	}
			
	getJSON() {
		return {
			funcionario : this.funcionarioFormComponent.getJSON(),
		}
	}	
		
	carregaJSON( dados ) {
		this.setFieldValue( 'escola', dados.escolaId ),		
		this.funcionarioFormComponent.carregaJSON( dados.funcionario );
	}	
	
	
	limpaForm() {
		super.setFieldValue( 'escola', '0' );		
	}	
								
}
