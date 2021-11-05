
import * as elutil from '../../../../../sistema/util/elutil.js';

import {sistema} from '../../../../../sistema/Sistema.js';

import FormComp from '../../../../../sistema/comp/FormComp.js';
import PaiOuMaeFormComp from './PaiOuMaeFormComp.js';

export default class ModalPaiOuMaeFormComp extends FormComp {
				
	validadoOk = () => {};			
				
	constructor( prefixo ) {
		super( prefixo, 'modal-pai-ou-mae-form-comp', 'modal_el', 'modal_mensagem_el' );
		
		this.paiOuMaeFormComp = new PaiOuMaeFormComp( prefixo );
		
		super.addFilho( this.paiOuMaeFormComp );		
	}	
	
	onFormConfigurado() {
		this.params.titulo = super.getGlobalParam( 'modal_titulo' );
	}
						
	onHTMLCarregado() {
		const instance = this;
		super.getEL( 'fechar_btn' ).onclick = (e) => instance.mostraEscondeModal( e );		
		super.getEL( 'fechar2_btn' ).onclick = (e) => instance.mostraEscondeModal( e );		
		super.getEL( 'finalizar_btn' ).onclick = (e) => instance.validaModalForm( e );		
	}	
	
	getJSON() {
		return this.paiOuMaeFormComp.getJSON()
	}
		
	carregaJSON( dados ) {				
		this.paiOuMaeFormComp.carregaJSON( dados );
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
				let cpf = instance.paiOuMaeFormComp.getFieldValue( 'cpf' );
				let nome = instance.paiOuMaeFormComp.getFieldValue( 'nome' );
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