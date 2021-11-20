
import * as elutil from '../../../../../sistema/util/elutil.js';

import {sistema} from '../../../../../sistema/Sistema.js';

import FormComponent from '../../../../component/FormComponent.js';
import PaiOuMaeFormComponent from './PaiOuMaeFormComponent.js';

export default class ModalPaiOuMaeFormComponent extends FormComponent {
				
	finalizouComValidacaoOk = () => {};			
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'modal-pai-ou-mae-form', compELIDSufixo, 'modal_mensagem_el' );
		
		this.paiOuMaeFormComponent = new PaiOuMaeFormComponent( formNome, prefixo, 'modal_form_el' );
		
		super.addFilho( this.paiOuMaeFormComponent );		
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
		return this.paiOuMaeFormComponent.getJSON()
	}
		
	carregaJSON( dados ) {				
		this.paiOuMaeFormComponent.carregaJSON( dados );
	}		
	
	finalizarBTNClicado( e ) {		
		super.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "POST", "/api/paioumae/valida", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.getJSON() ),
			sucesso : function( resposta ) {	
				let cpf = instance.paiOuMaeFormComponent.pessoaFormComponent.getFieldValue( "cpf" );							
				let nome = instance.paiOuMaeFormComponent.pessoaFormComponent.getFieldValue( "nome" );
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