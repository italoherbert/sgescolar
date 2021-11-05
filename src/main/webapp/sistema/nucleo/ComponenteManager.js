
import * as ajax from '../util/ajax.js';

export default class ComponenteManager {
	
	componentes = {}	
	
	inicializa( componentes ) {
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
					
		ajax.ajaxCarregaHTML( elid, pagina, Object.assign( params2, {
			sucesso : function( xmlhttp ) {				
				if ( jsObj !== undefined && jsObj !== null )										
					if ( typeof( jsObj.onCarregado ) === "function" )
						jsObj.onCarregado.call( jsObj );						
				
				if ( params !== null && params !== undefined )
					if ( typeof( params.carregado ) === 'function' )
						params.carregado.call( this, xmlhttp );				
			},
			erro : function( xmlhttp ) {
				let funcErroEncontrada = false;
				if ( params !== null && params !== undefined ) {
					if ( typeof( params.houveErro ) == 'function' ) {
						params.houveErro.call( this, "Pagina não encontrada: "+pagina, xmlhttp );
						funcErroEncontrada = true;
					}
				}
				
				if ( funcErroEncontrada === false )
					throw "Pagina não encontrada: "+pagina;				
			}
		} ) );
	}
			
}