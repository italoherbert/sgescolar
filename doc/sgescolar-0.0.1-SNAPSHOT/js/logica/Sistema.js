
class Sistema {
			
	componentes = {};	
	globalVars = {};
	
	constructor( componentes ) {
		this.componentes = componentes;
	}
	
	carregaPagina( compID, params ) {
		this.carregaComponentePorELID( compID, "pagina", params );
	}
	
	carregaComponentePorELID( compID, elid, params ) {
		let comp = this.componentes[ compID ];
		let pagina = comp.pagina;
		let jsObj = comp.jsObj;
																	
		this.carregaComponente( elid, pagina, jsObj, params );
	}
	
	carregaComponente( elid, pagina, jsObj, params ) {
		ajaxCarregaRecurso( elid, pagina, {
			sucesso : function() {
				if ( jsObj !== undefined && jsObj !== null )
					if ( typeof( jsObj.onCarregado ) === "function" )
						jsObj.onCarregado .call( this, jsObj, params );
			}
		} )
	}	
	
	mostraMensagem( id, tipo, msg ) {
		let className = ( tipo === 'info' ? 'alert-primary' : 'alert-danger' );
		document.getElementById( id ).innerHTML = "<div class='alert "+className+"'>"+msg+"</div>";	
	}
	
	limpaMensagem( id ) {
		document.getElementById( id ).innerHTML = "";
	}
		
}

