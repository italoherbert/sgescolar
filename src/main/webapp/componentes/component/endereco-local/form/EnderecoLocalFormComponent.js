
import FormComponent from '../../FormComponent.js';

export default class EnderecoLocalFormComponent extends FormComponent {
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'endereco-local-form', compELIDSufixo, 'endereco_mensagem_el' );
	}
	
				
	getJSON() {
		return {
			logradouro : super.getFieldValue( 'logradouro' ),
			complemento : super.getFieldValue( 'complemento' ),
			bairro : super.getFieldValue( 'bairro' ),			
			cep : super.getFieldValue( 'cep' )		
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'logradouro' , dados.logradouro );
		super.setFieldValue( 'complemento' , dados.complemento );
		super.setFieldValue( 'bairro' , dados.bairro );
		super.setFieldValue( 'cep' , dados.cep );
	}
	
	limpaForm() {
		super.setFieldValue( 'logradouro' , "" );
		super.setFieldValue( 'complemento' , "" );
		super.setFieldValue( 'bairro' , "" );
		super.setFieldValue( 'cep' , "" );				
	}
		
}
