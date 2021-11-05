
import FormComponent from '../../../component/FormComponent.js';

export default class ContatoinfoFormComponent extends FormComponent {
				
	constructor( formNome, prefixo ) {
		super( formNome, prefixo, 'contatoinfo-form', 'contatoinfo_form_el', 'contatoinfo_mensagem_el' );
	}
				
	getJSON() {
		return {
			telefoneResidencial : super.getFieldValue( 'telefone_residencial' ),
			telefoneCelular : super.getFieldValue( 'telefone_celular' ),
			email : super.getFieldValue( 'email' )					
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'telefone_residencial', dados.telefoneResidencial );
		super.setFieldValue( 'telefone_celular', dados.telefoneCelular );
		super.setFieldValue( 'email', dados.email );		
	}
	
	limpaForm() {
		super.setFieldValue( 'telefone_residencial', "" );
		super.setFieldValue( 'telefone_celular', "" );
		super.setFieldValue( 'email', "" );		
	}
	
}