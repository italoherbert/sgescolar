	
import FormComponent from '../FormComponent.js';

export default class AutoCompleteFormComponent extends FormComponent {
					
	selectedValue = null;
	selectedId = null;
												
	constructor( formNome, prefixo, compELIDSufixo ) {
		super( formNome, prefixo, 'autocomplete-input', compELIDSufixo, 'autocomplete_mensagem_el' );
	}	
		
	onHTMLCarregado() {		
		super.getEL( 'input_el' ).onkeyup = (e) => this.onkeyupInput( e );
		super.getEL( 'input_el' ).onchange = (e) => this.onchangeInput( e );
	}
	
	onchangeInput( e ) {
		let datalist = super.getEL( 'list_el' );
		let option = datalist.querySelectorAll( 'option[value='+e.target.value+']' );
			
		this.selectedId = option[0].getAttribute( 'data-id' );
		this.selectedValue = e.target.value;
	}
				
	onkeyupInput( e ) {				
		let input_name = this.globalParams.input_name;
		let fcarregaDados = this.globalParams.carregaDados;
					
		let inputValue = super.getFieldValue( input_name );
		if ( typeof( fcarregaDados ) === 'function' )
			fcarregaDados.call( this, inputValue, ( dados ) => this.onDadosCarregados( dados ) );		
	}
	
	onDadosCarregados( dados ) {
		let html = "";
		for( let i = 0; i < dados.length; i++ )
			html += "<option value=\"" + dados[ i ].value + "\" data-id=\"" + dados[ i ].id + "\">";
		
		super.setHTML( 'list_el', html );		
	}
			
}