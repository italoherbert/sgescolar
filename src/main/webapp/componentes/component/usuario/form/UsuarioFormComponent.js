
import Component from '../../Component.js';
import FormComponent from '../../FormComponent.js';

export default class UsuarioFormComponent extends FormComponent {
							
	carregaPerfis = () => {};
	endUsuarioFormComponent = null;
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'usuario-form', compELIDSufixo, 'usuario_mensagem_el' );				
	}					
	
	onHTMLCarregado() {				
		this.carregaPerfis( super.getELID( 'perfil_select' ) );
	}
	
	configuraEndFormComponent( prefixo, compId ) {
		this.endUsuarioFormComponent = new Component( prefixo, compId, 'end_form_el', 'usuario_mensagem_el' );		
		super.addFilho( this.endUsuarioFormComponent );
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
		
		let select_el = super.getEL( 'perfil_select' );
		if ( select_el.options.length > 0 )
			select_el.options[0].selected = true;	
	}
			
}