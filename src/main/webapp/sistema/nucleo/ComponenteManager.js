
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
		
		let doc = comp.doc;
		if ( doc === undefined || doc === null )
			throw "Documento HTML não encontrado: "+doc;
					
		let params2 = {};	
		if ( params !== undefined && params !== null )											
			params2 = params;
			
		let service = comp.service;					
		if ( service !== undefined && service !== null ) {
			if ( service.params === undefined || service.params === null )
				service.params = {};
				
			service.params = Object.assign( {}, service.params, params2 );					
										
			service.componente = {
				id : compID,
				elid : elid,
				doc : doc					
			};
			
			service.recarregaHTML = () => {
				let _elid = service.componente.elid;
				let _doc = service.componente.doc;
				let _params = service.params;	
				ajax.ajaxCarregaHTML( _elid, _doc, _params );
			};
			
			params2 = service.params;			
		}
		
		if ( service !== undefined && service !== null )										
			if ( typeof( service.onCarregamentoIniciado ) === "function" )
				service.onCarregamentoIniciado.call( service );	
					
		ajax.ajaxCarregaHTML( elid, doc, Object.assign( {}, params2, {
			sucesso : function( xmlhttp ) {				
				if ( service !== undefined && service !== null )										
					if ( typeof( service.onCarregado ) === "function" )
						service.onCarregado.call( service );						
				
				if ( params !== null && params !== undefined )
					if ( typeof( params.sucesso ) === 'function' )
						params.sucesso.call( this, xmlhttp );				
			},
			erro : function( xmlhttp ) {
				let funcErroEncontrada = false;
				if ( params !== null && params !== undefined ) {
					if ( typeof( params.erro ) == 'function' ) {
						params.erro.call( this, "Pagina não encontrada: "+doc, xmlhttp );
						funcErroEncontrada = true;
					}
				}
				
				if ( funcErroEncontrada === false )
					throw "Documento HTML não encontrado: "+doc;				
			}
		} ) );
	}
		
	getComp( compID ) {
		return this.componentes[ compID ];		
	}
			
}