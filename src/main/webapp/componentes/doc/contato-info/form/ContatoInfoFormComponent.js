
import FormComponent from '../../../component/FormComponent.js';

export default class ContatoinfoFormComponent extends FormComponent {
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'contato-info-form', compELIDSufixo, 'contato_info_mensagem_el' );
	}
				
	getJSON() {
		return {
			telefoneResidencial : super.getFieldValue( 'telefone_fixo' ),
			telefoneCelular : super.getFieldValue( 'telefone_celular' ),
			email : super.getFieldValue( 'email' )					
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'telefone_fixo', dados.telefoneFixo );
		super.setFieldValue( 'telefone_celular', dados.telefoneCelular );
		super.setFieldValue( 'email', dados.email );		
	}
	
	limpaForm() {
		super.setFieldValue( 'telefone_fixo', "" );
		super.setFieldValue( 'telefone_celular', "" );
		super.setFieldValue( 'email', "" );		
	}
	
}