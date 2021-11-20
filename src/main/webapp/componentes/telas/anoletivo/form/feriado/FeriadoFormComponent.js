
import FormComponent from '../../../../component/FormComponent.js';

export default class BimestreFormComponent extends FormComponent {
				
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'bimestre-form', compELIDSufixo, 'bimestre_mensagem_el' );
	}
						
	getJSON() {
		return {
			dataInicio : super.getFieldValue( 'dataini' ),
			dataFim : super.getFieldValue( 'datafim' ),
			lancamentoDataInicio : super.getFieldValue( 'lancamento_dataini' ),
			lancamentoDataFim : super.getFieldValue( 'lancamento_datafim' )								
		};
	}
	
	carregaJSON( dados ) {
		super.setFieldValue( 'dataini', dados.dataInicio );
		super.setFieldValue( 'datafim', dados.dataFim );
		super.setFieldValue( 'lancamento_dataini', dados.lancamentoDataInicio );		
		super.setFieldValue( 'lancamento_datafim', dados.lancamentoDataFim );		
	}
	
	limpaForm() {
		super.setFieldValue( 'dataini', "" );
		super.setFieldValue( 'datafim', "" );
		super.setFieldValue( 'lancamento_dataini', "" );		
		super.setFieldValue( 'lancamento_datafim', "" );		
	}
	
}