
class ComponenteManager {
	
	componentes = {}	

	constructor( componentes ) {
		this.componentes = componentes;
	}
	
	carregaLayout( compID, params ) {
		this.carregaComponente( compID, "layout", params );
	}
		
	carregaPagina( compID, params ) {
		this.carregaComponente( compID, "pagina", params );
	}
						
	carregaComponente( compID, elid, params ) {
		let comp = this.componentes[ compID ];
		if ( comp === undefined || comp === null )
			throw "Componente não encontrado: "+compID;
		
		let pagina = comp.pagina;
		if ( pagina === undefined || pagina === null )
			throw "Pagina não encontrada: "+pagina;
					
		let params2 = {};	
		if ( params !== undefined && params !== null )											
			params2 = params;
			
		let jsObj = comp.jsObj;					
		if ( jsObj !== undefined && jsObj !== null ) {
			if ( jsObj.params === undefined || jsObj.params === null )
				jsObj.params = {};
				
			jsObj.params = Object.assign( jsObj.params, params2 );					
										
			jsObj.componente = {
				id : compID,
				elid : elid,
				pagina : pagina					
			};
			
			jsObj.recarregaHTML = () => {
				let _elid = jsObj.componente.elid;
				let _pagina = jsObj.componente.pagina;
				let _params = jsObj.params;	
				ajaxCarregaHTML( _elid, _pagina, _params );
			};
			
			params2 = jsObj.params;			
		}
					
		ajaxCarregaHTML( elid, pagina, Object.assign( params2, {
			sucesso : function( html, xmlhttp ) {				
				if ( jsObj !== undefined && jsObj !== null )										
					if ( typeof( jsObj.onCarregado ) === "function" )
						jsObj.onCarregado.call( jsObj );						
				
				if ( params !== null && params !== undefined )
					if ( typeof( params.sucessoCarregamento ) === 'function' )
						params.sucessoCarregamento.call( this, html, xmlhttp );				
			},
			erro : function( xmlhttp ) {
				let funcErroEncontrada = false;
				if ( params !== null && params !== undefined ) {
					if ( typeof( params.erroCarregamento ) == 'function' ) {
						params.erroCarregamento.call( this, "Pagina não encontrada: "+pagina );
						funcErroEncontrada = true;
					}
				}
				
				if ( funcErroEncontrada === false )
					throw "Pagina não encontrada: "+pagina;				
			}
		} ) );
	}
			
}