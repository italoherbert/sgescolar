
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import FuncionarioFormComponent from '../../../component/funcionario/form/FuncionarioFormComponent.js';

export default class AdministradorFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.funcionarioFormComponent = new FuncionarioFormComponent( formNome, '', 'funcionario_form_el' );
				
		this.funcionarioFormComponent.usuarioFormComponent.carregaPerfis = ( sel_elid, onparams ) => this.carregaUsuarioPerfis( sel_elid, onparams );
				
		super.addFilho( this.funcionarioFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();				
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/administrador/get/"+this.globalParams.administradorId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		} else {
			const instance = this;
			selectService.carregaInstituicoesSelect( 'instituicoes_select', {
				onload : () => {
					instance.setFieldValue( 'instituicao', perfilService.getInstituicaoID() );					
				}
			} );
		}
	}
	
	carregaUsuarioPerfis( select_elid, onparams ) {
		selectService.carregaAdminPerfisSelect( select_elid, onparams );			
	}
		
	getJSON() {
		return {
			funcionario : this.funcionarioFormComponent.getJSON(),
		}
	}	
		
	carregaJSON( dados ) {				
		selectService.carregaUmaOptionSelect( 'instituicoes_select', dados.instituicao.id, dados.instituicao.razaoSocial );		
		
		this.funcionarioFormComponent.carregaJSON( dados.funcionario );
	}	
										
}
