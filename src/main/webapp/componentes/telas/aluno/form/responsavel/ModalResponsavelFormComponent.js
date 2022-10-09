
import * as elutil from '../../../../../sistema/util/elutil.js';

import {sistema} from '../../../../../sistema/Sistema.js';

import FormComponent from '../../../../component/FormComponent.js';
import ResponsavelFormComponent from './ResponsavelFormComponent.js';

export default class ModalResponsavelFormComponent extends FormComponent {
				
	finalizouComValidacaoOk = () => {};			
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'modal-responsavel-form', compELIDSufixo, 'modal_mensagem_el' );
		
		this.responsavelFormComponent = new ResponsavelFormComponent( formNome, prefixo, 'modal_form_el' );
		
		super.addFilho( this.responsavelFormComponent );		
	}	
	
	onConfigurado() {
		this.params.titulo = super.getGlobalParam( 'modal_titulo' );
	}
						
	onHTMLCarregado() {
		const instance = this;
		super.getEL( 'fechar_btn' ).onclick = (e) => instance.mostraEscondeModal( e );		
		super.getEL( 'fechar2_btn' ).onclick = (e) => instance.mostraEscondeModal( e );		
		super.getEL( 'finalizar_btn' ).onclick = (e) => instance.finalizarBTNClicado( e );		
	}	
	
	getJSON() {
		return this.responsavelFormComponent.getJSON()
	}
		
	carregaJSON( dados ) {				
		this.responsavelFormComponent.carregaJSON( dados );
	}		
	
	finalizarBTNClicado( e ) {		
		super.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "POST", "/api/responsavel/valida", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.getJSON() ),
			sucesso : function( resposta ) {	
				let cpf = instance.responsavelFormComponent.pessoaFormComponent.getFieldValue( "cpf" );							
				let nome = instance.responsavelFormComponent.pessoaFormComponent.getFieldValue( "nome" );
				instance.finalizouComValidacaoOk( cpf, nome );					
				instance.mostraEscondeModal();							
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );			
	}
	
	mostraEscondeModal() {
		elutil.showHide( super.getELID( 'modal' ) );
	}
	
}