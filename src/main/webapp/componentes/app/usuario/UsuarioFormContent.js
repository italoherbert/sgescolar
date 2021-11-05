
import FormContent from '../../ext/FormContent.js';

export default class UsuarioFormContent extends FormContent {
				
	constructor( prefixo ) {
		super( prefixo, 'usuario-form-content', 'usuario_form_el', 'usuario_mensagem_el' );
	}					
				
	getJSON() {
		return {
			username : super.getFieldValue( 'username' ),
			password : super.getFieldValue( 'password' )
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'username', dados.username );
		super.setFieldValue( 'password', dados.password );
		super.setFieldValue( 'password2', dados.password );
	}
	
	limpaForm() {
		super.setFieldValue( 'username', "" );
		super.setFieldValue( 'password', "" );
		super.setFieldValue( 'password2', "" );
	}
	
}