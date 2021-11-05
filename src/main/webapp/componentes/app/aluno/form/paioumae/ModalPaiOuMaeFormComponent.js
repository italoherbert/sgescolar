
import * as elutil from '../../../../../sistema/util/elutil.js';

import {sistema} from '../../../../../sistema/Sistema.js';

import FormComponent from '../../../../component/FormComponent.js';
import PaiOuMaeFormComponent from './PaiOuMaeFormComponent.js';

export default class ModalPaiOuMaeFormComponent extends FormComponent {
				
	validadoOk = () => {};			
				
	constructor( formNome, prefixo ) {
		super( formNome, prefixo, 'modal-pai-ou-mae-form', 'modal_el', 'modal_mensagem_el' );
		
		this.paiOuMaeFormComponent = new PaiOuMaeFormComponent( formNome, prefixo );
		
		super.addFilho( this.paiOuMaeFormComponent );		
	}	
	
	onConfigurado() {
		this.params.titulo = super.getGlobalParam( 'modal_titulo' );
	}
						
	onHTMLCarregado() {
		const instance = this;
		super.getEL( 'fechar_btn' ).onclick = (e) => instance.mostraEscondeModal( e );		
		super.getEL( 'fechar2_btn' ).onclick = (e) => instance.mostraEscondeModal( e );		
		super.getEL( 'finalizar_btn' ).onclick = (e) => instance.validaModalForm( e );		
	}	
	
	getJSON() {
		return this.paiOuMaeFormComponent.getJSON()
	}
		
	carregaJSON( dados ) {				
		this.paiOuMaeFormComponent.carregaJSON( dados );
	}		
	
	validaModalForm() {		
		super.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "POST", "/api/paioumae/valida", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.getJSON() ),
			sucesso : function( resposta ) {
				let cpf = instance.paiOuMaeFormComponent.getFieldValue( 'cpf' );
				let nome = instance.paiOuMaeFormComponent.getFieldValue( 'nome' );
				instance.validadoOk( cpf, nome );				
				instance.mostraEscondeModal();							
			},
			erro : function( msg ) {
				instance.mostraMensagemErro( msg );	
			}
		} );			
	}
	
	mostraEscondeModal() {
		elutil.showHide( super.getELID( 'modal' ) );
	}
	
}