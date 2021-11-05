
import * as elutil from '../../../../../sistema/util/elutil.js';

import FormComp from '../../../../../sistema/comp/FormComp.js';
import ModalPaiOuMaeFormComp from './ModalPaiOuMaeFormComp.js';

export default class ResumoPaiOuMaeFormComp extends FormComp {
						
	constructor( prefixo ) {
		super( prefixo, 'resumo-pai-ou-mae-form-comp', 'resumo_form_el', 'resumo_mensagem_el' );
				
		this.modal = new ModalPaiOuMaeFormComp( prefixo );
		this.modal.validadoOk = this.validadoOk;
		
		super.addFilho( this.modal );			
	}	
					
	onFormConfigurado() {				
		this.params.titulo = super.getGlobalParam( "resumo_titulo" );
		this.params.dados_completos_btn_rotulo = super.getGlobalParam( "resumo_dados_completos_btn_rotulo" );
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
	
	limpaForm() {
		if ( super.getFieldChecked( "resumo_desconhecido" ) == true ) {
			super.setFieldChecked( 'resumo_desconhecido', false );
			super.getField( 'resumo_desconhecido' ).onchange();
		}
		super.setFieldValue( 'resumo_cpf', '' );
		super.setFieldValue( 'resumo_nome', '' );
		
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