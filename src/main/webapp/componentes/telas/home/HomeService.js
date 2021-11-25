
import AutoCompleteFormComponent from '../../component/autocomplete/AutoCompleteFormComponent.js';

export default class HomeService {
	
	dados = [ 'Italo', 'Itala', 'Ivan', 'Iris', 'Ivone' ];	
		
	constructor() {
		this.nomeAutoCompleteFormComponent = new AutoCompleteFormComponent( 'autocomplete_form', '', 'autocomplete-el' );
	}	
		
	onCarregado() {
		this.nomeAutoCompleteFormComponent.configura( {
			input_name : 'pessoa_nome',
			carregaDados : ( inputValue, callback ) => this.carregaDados( inputValue, callback )
		} );		
		this.nomeAutoCompleteFormComponent.carregaHTML();
	}		
	
	carregaDados( inputValue, callback ) {
		let dldados = [];
		for( let i = 0; i < this.dados.length; i++ ) {
			let nome1 = this.dados[ i ].toLowerCase();
			let nome2 = inputValue.toLowerCase();
			if ( nome1.startsWith( nome2 ) && nome1 !== nome2 )
				dldados.push( { value : this.dados[ i ], id : i } ); 			
		} 		
		
		callback.call( this, dldados );
	}
	
	salva() {
		
	}
	
}
export const homeTela = new HomeService();