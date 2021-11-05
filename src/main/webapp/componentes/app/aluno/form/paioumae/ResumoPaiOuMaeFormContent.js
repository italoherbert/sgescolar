
import * as elutil from '../../../../../sistema/util/elutil.js';

import PaiOuMaeModal from './PaiOuMaeModal.js';
import FormContent from '../../../../ext/FormContent.js';

export default class ResumoPaiOuMaeFormContent extends FormContent {
						
	constructor( prefixo ) {
		super( prefixo, 'resumo-pai-ou-mae-form-content', 'resumo_form_el', 'resumo_mensagem_el' );
				
		this.modal = new PaiOuMaeModal( prefixo );		
		this.modal.validadoOk = this.validadoOk;
		
		super.addFilho( this.modal );			
	}	
					
	onHTMLCarregado() {
		const instance = this;
		super.getEL( 'resumo_desconhecido_cbox' ).onchange = (e) => instance.mostraEscondeFiliacaoPainel();		
		super.getEL( 'dados_completos_btn' ).onclick = (e) => instance.modal.mostraEscondeModal( e ); 					
	}	
				
	getJSON() {
		let json = Object.assign( {}, 
			{ desconhecido : super.getFieldChecked( 'resumo_desconhecido' ) }, 
			this.modal.getJSON()
		);
		
		super.setFieldValue( 'resumo_cpf', super.getFieldValue( 'cpf' ) );
		super.setFieldValue( 'resumo_nome', super.getFieldValue( 'nome' ) );
		
		return json;
	}
	
	carregaJSON( dados ) {
		super.setFieldChecked( 'resumo_desconhecido', dados.desconhecido == 'true' ? true : false );
		super.setFieldValue( 'resumo_cpf', dados.pessoa.cpf );
		super.setFieldValue( 'resumo_nome', dados.pessoa.nome );
		
		if ( dados.desconhecido === 'true' )
			this.mostraEscondeFiliacaoPainel();
		
		this.modal.carregaJSON( dados );
	}
	
	mostraEscondeFiliacaoPainel() {
		elutil.showHide( super.getELID( 'filiacao_painel_el' ) );
	}
		
	validadoOk( cpf, nome ) {
		super.setFieldValue( 'resumo_cpf', cpf );
		super.setFieldValue( 'resumo_nome', nome );
		super.getField( 'resumo_cpf' ).disabled = true;
		super.getField( 'resumo_nome' ).disabled = true;
	}
	
}