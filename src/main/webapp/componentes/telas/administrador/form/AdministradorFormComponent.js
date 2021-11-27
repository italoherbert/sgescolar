
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

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
			selectService.carregaInstituicoesSelect( super.getELID( 'instituicoes_select' ) );
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
		const instance = this;
		selectService.carregaInstituicoesSelect( super.getELID( 'instituicoes_select' ), {
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.instituicao.id );
			}
		} );
		
		this.funcionarioFormComponent.carregaJSON( dados.funcionario );
	}	
										
}
