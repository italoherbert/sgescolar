
import * as elutil from '../../../../../sistema/util/elutil.js';

import FormComponent from '../../../../component/FormComponent.js';
import ModalResponsavelFormComponent from './ModalResponsavelFormComponent.js';

export default class ResumoResponsavelFormComponent extends FormComponent {
						
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'resumo-responsavel-form', compELIDSufixo, 'resumo_mensagem_el' );
				
		this.modal = new ModalResponsavelFormComponent( formNome, prefixo, 'modal_el' );
		this.modal.finalizouComValidacaoOk = this.finalizouComValidacaoOk;
		
		super.addFilho( this.modal );			
	}	
					
	onConfigurado() {				
		this.params.titulo = super.getGlobalParam( "resumo_titulo" );
		this.params.dados_completos_btn_rotulo = super.getGlobalParam( "resumo_dados_completos_btn_rotulo" );
	}				
					
	onHTMLCarregado() {
		const instance = this;
		super.getEL( 'resumo_registrar_cbox' ).onchange = (e) => instance.mostraEscondeFiliacaoPainel();		
		super.getEL( 'dados_completos_btn' ).onclick = (e) => instance.modal.mostraEscondeModal( e ); 					
	}	
				
	getJSON() {
		let json = Object.assign( {}, 
			{ registrar : super.getFieldChecked( 'resumo_registrar' ) }, 
			this.modal.getJSON()
		);
				
		return json;
	}
	
	carregaJSON( dados ) {
		super.setFieldChecked( 'resumo_registrar', dados.registrar == 'true' ? true : false );
		super.setHTML( "resumo_cpf", dados.pessoa.cpf );
		super.setHTML( "resumo_nome", dados.pessoa.nome );
		
		if ( dados.registrar === 'true' )
			this.mostraEscondeFiliacaoPainel();
		
		this.modal.carregaJSON( dados );
	}
	
	limpaForm() {
		if ( super.getFieldChecked( "resumo_registrar" ) == false ) {
			super.setFieldChecked( 'resumo_registrar', true );
			super.getField( 'resumo_registrar' ).onchange();
		}		
		
		super.setHTML( 'resumo_cpf', '' );
		super.setHTML( 'resumo_nome', '' );
	}
	
	finalizouComValidacaoOk( cpf, nome ) {
		super.setHTML( "resumo_cpf", cpf );
		super.setHTML( "resumo_nome", nome );
	}
	
	mostraEscondeFiliacaoPainel() {
		elutil.showHide( super.getELID( 'filiacao_painel_el' ) );
	}
			
}