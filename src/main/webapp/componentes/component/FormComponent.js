
import Component from './Component.js';

export default class FormComponent extends Component {
	
	formNome = null; 
			
	constructor( formNome, prefixo, compId, compElementoSufixo, msgElementoSufixo ) {
		super( prefixo, compId, compElementoSufixo, msgElementoSufixo );
		this.formNome = formNome;
	}			
				
	getField( field ) {		
		return document[ this.formNome ][ this.prefixo + field ];
	}
	
	getFieldValue( field ) {
		return document[this.formNome ][ this.prefixo + field ].value;
	}
	
	getFieldChecked( field ) {
		return document[ this.formNome ][ this.prefixo + field ].checked;		
	}
	
	setFieldValue( field, value ) {
		document[ this.formNome ][ this.prefixo + field ].value = value;	
	}
	
	setFieldChecked( field, checked ) {
		document[ this.formNome ][ this.prefixo + field ].checked = checked;
	}
		
}