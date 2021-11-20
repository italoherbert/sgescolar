
import Component from './Component.js';

export default class FormComponent extends Component {
	
	formNome = null; 
			
	constructor( formNome, prefixo, compId, compELIDSufixo, msgELIDSufixo ) {
		super( prefixo, compId, compELIDSufixo, msgELIDSufixo );
		this.formNome = formNome;
	}			
		
	limpaDados() {
		if ( typeof( this.limpaForm ) === 'function' )
			this.limpaForm( this );
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
		let v = ( value === undefined || value === null ? "" : value );
		
		document[ this.formNome ][ this.prefixo + field ].value = v;	
	}
	
	setFieldChecked( field, checked ) {
		let ckd = ( checked === undefined || checked === null ? false : checked );
		
		document[ this.formNome ][ this.prefixo + field ].checked = ckd;
	}
		
}