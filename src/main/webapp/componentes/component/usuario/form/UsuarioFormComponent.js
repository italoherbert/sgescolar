
import FormComponent from '../../FormComponent.js';

export default class UsuarioFormComponent extends FormComponent {
				
	carregaPerfis = () => {};			
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'usuario-form', compELIDSufixo, 'usuario_mensagem_el' );
	}					
	
	onHTMLCarregado() {
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
		super.setFieldValue( 'password', dados.password );
		super.setFieldValue( 'password2', dados.password );
		super.setFieldValue( 'perfil', dados.grupo.perfil );
	}
	
	limpaForm() {
		super.setFieldValue( 'username', "" );
		super.setFieldValue( 'password', "" );
		super.setFieldValue( 'password2', "" );
		super.setFieldValue( 'perfil', "0" );
		
	}
	
}