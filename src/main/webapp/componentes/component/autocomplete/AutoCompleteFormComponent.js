	
import FormComponent from '../FormComponent.js';

export default class AutoCompleteFormComponent extends FormComponent {
					
	selectedValue = null;
	selectedId = null;
	
	onItemSelecionado = () => {};
													
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'autocomplete-input', compELIDSufixo, 'autocomplete_mensagem_el' );
		this.params.input_name = (new Date().getTime())+"_autocomplete_input";
	}	
		
	onHTMLCarregado() {
		super.getEL( 'input_el' ).onkeyup = (e) => this.onkeyupInput( e );
		super.getEL( 'input_el' ).onchange = (e) => this.onchangeInput( e );
	}
	
	onchangeInput( e ) {
		let datalist = super.getEL( 'list_el' );
		let option = datalist.querySelector( "option[value='" + e.target.value + "']" );
			
		if ( option !== null ) {
			this.selectedId = option.getAttribute( 'data-id' );
			this.selectedValue = e.target.value;
			
			if ( typeof( this.onItemSelecionado ) === 'function' )
				this.onItemSelecionado.call( this, this.selectedId, this.selectedValue );
		}			
	}
				
	onkeyupInput( e ) {									
		let inputValue = super.getFieldValue( this.params.input_name );
		
		let datalist = super.getEL( 'list_el' );			
		if ( datalist.options !== null ) {
			if ( datalist.options.length > 0 ) {
				this.selectedId = datalist.options[ 0 ].getAttribute( 'data-id' );				
				this.selectedValue = inputValue;
			}
		}
			
		if ( inputValue !== this.selectedValue ) {
			if ( typeof( this.onTeclaDigitada ) === 'function' ) 
				this.onTeclaDigitada.call( this, e, inputValue );
		}
	}
	
	carrega( dados ) {
		let html = "";
		for( let i = 0; i < dados.length; i++ )
			html += "<option value=\"" + dados[ i ].value + "\" data-id=\"" + dados[ i ].id + "\">";

		super.setHTML( 'list_el', html );		
	}
	
	getInputValue() {
		return super.getFieldValue( this.params.input_name );
	}
	
	limpaForm() {
		super.setFieldValue( this.params.input_name, "" );
	}
			
}