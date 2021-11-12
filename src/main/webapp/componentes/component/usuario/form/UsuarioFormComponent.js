
import {sistema} from '../../../../sistema/Sistema.js';

import FormComponent from '../../FormComponent.js';

export default class UsuarioFormComponent extends FormComponent {
				
	end = { elidSufixo : "form_end_el", comp : null };	
				
	carregaPerfis = () => {};				
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'usuario-form', compELIDSufixo, 'usuario_mensagem_el' );
	}					
	
	onHTMLCarregado() {
		if ( this.end.comp !== null ) {
			let end_elid = this.prefixo + this.end.elidSufixo;
			sistema.carregaComponente( this.end.comp, end_elid );
		}
		
		this.carregaPerfis( super.getELID( 'perfil_select' ) );
	}
				
	getJSON() {
		return {
			username : super.getFieldValue( 'username' ),
			password : super.getFieldValue( 'password' ),
			perfil : super.getFieldValue( 'perfil' )
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'username', dados.username );
		super.setFieldValue( 'perfil', dados.perfil );
	}
	
	limpaForm() {
		super.setFieldValue( 'username', "" );
		super.setFieldValue( 'password', "" );
		super.setFieldValue( 'password2', "" );
		super.setFieldValue( 'perfil', "0" );		
	}
			
}