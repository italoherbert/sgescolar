
import Comp from './Comp.js';

export default class FormComp extends Comp {
	
	form = null;
			
	constructor( prefixo, compId, compElementoSufixo, msgElementoSufixo ) {
		super( prefixo, compId, compElementoSufixo, msgElementoSufixo );
		this.prefixo = prefixo;
		this.compId = compId;
		this.compElementoSufixo = compElementoSufixo;
		this.mensagemElementoSufixo = msgElementoSufixo;
	}
	
	formConfigura( form, globalParams ) {
		super.globalParams = globalParams;
		this.form = form;
	
		for( let i = 0; i < this.filhos.length; i++ )
			this.filhos[ i ].formConfigura( form, globalParams );
		
		if ( typeof( this.onFormConfigurado ) === 'function' )
			this.onFormConfigurado.call( this ); 		
	}		
				
	getField( field ) {		
		return this.form[ this.prefixo + field ];
	}
	
	getFieldValue( field ) {
		return this.form[ this.prefixo + field ].value;
	}
	
	getFieldChecked( field ) {
		return this.form[ this.prefixo + field ].checked;
	}
	
	setFieldValue( field, value ) {		
		this.form[ this.prefixo + field ].value = value;
	}
	
	setFieldChecked( field, checked ) {
		this.form[ this.prefixo + field ].checked = checked;
	}
		
}