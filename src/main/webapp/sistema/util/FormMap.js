
class FormMap {
	
	constructor( prefixo='' ) {	
		this.prefixo = prefixo;
		this.params									
	}
	
	inicializa( paginaParams ) {
		let instance = this;
		paginaParams.forEach( (item, index) => {
			instance.params[ item ] = this.prefixo + "_" + instance.params[ item ];
		} );
	}
				
	getDados() {
		let form = document.getElementsByName( this.params.form_name )[0];
		
		let dados = {};
		Object.keys( this.params ).forEach( (key) => {
			if ( key.endsWith( '_name' ) ) {
				let name = key.replace( '_name', '' );		
				if ( form[ key ] !== undefined && form[ key ] !== null ) {
					dados[ name ] = form[ key ].value;
				}
			}			
		} );		
		
		return dados;
	}
	
	setDados( dados ) {
		let form = document.getElementsByName( this.params.form_name )[0];	
				
		Object.keys( this.params ).forEach( (key) => {
			if ( key.endsWith( '_name' ) ) {
				let name = key.replace( '_name', '' );
				form[ name ].value = dados[ name ];
			}			
		} );						
	}
	
}