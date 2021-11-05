
import * as elutil from '../../../../../sistema/util/elutil.js';

import {sistema} from '../../../../../sistema/Sistema.js';

import FormContent from '../../../../ext/FormContent.js';
import PaiOuMaeFormContent from './PaiOuMaeFormContent.js';

export default class PaiOuMaeModal extends FormContent {
				
	validadoOk = () => {};			
				
	constructor( prefixo ) {
		super( prefixo, 'pai-ou-mae-modal', 'modal_el', 'modal_mensagem_el' );
		
		this.paiOuMaeFormContent = new PaiOuMaeFormContent( prefixo );
		
		super.addFilho( this.paiOuMaeFormContent );		
	}	
						
	onHTMLCarregado() {
		const instance = this;
		super.getEL( 'fechar_btn' ).onclick = (e) => {
			instance.mostraEscondeModal( e );
		};
		super.getEL( 'fechar2_btn' ).onclick = (e) => {
			instance.mostraEscondeModal( e );
		};
		super.getEL( 'finalizar_btn' ).onclick = (e) => {
			instance.validaForm( e );
		};				
	}	
	
	getJSON() {
		return this.paiOuMaeFormContent.getJSON()
	}
		
	carregaJSON( dados ) {				
		this.paiOuMaeFormContent.carregaJSON( dados );
	}		
	
	validaForm() {		
		super.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "POST", "/api/paioumae/valida", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.getJSON() ),
			sucesso : function( resposta ) {
				instance.validadoOk();				
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