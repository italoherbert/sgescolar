
export default class HTMLBuilder {
	
	novoLinkDetalhesHTML( onaction, classes ) {
		let cls = "icone-bt link-primary";
		if ( classes !== undefined && classes !== null )
			cls = classes +" "+ cls;
			
		return (
			"<a href=\"#!\" class=\""+cls+"\" onclick=\""+onaction+"\">" + 
				"<i class=\"bi bi-search icone\"></i>" +
				"detalhes" +
			"</a>"
		);
	}
	
	novoLinkRemoverHTML( onaction, classes ) {
		let cls = "icone-bt link-danger";
		if ( classes !== undefined && classes !== null )
			cls = classes +" "+ cls;
			
		return (
			"<a href=\"#!\" class=\""+cls+"\" onclick=\""+onaction+"\">" + 
				"<i class=\"bi bi-x-lg icone\"></i>" +
				"remover" +
			"</a>"
		);
	}
			
	novoButtonRegistroHTML( onaction, classes ) {
		let cls = "icone-bt btn btn-primary";
		if ( classes !== undefined && classes !== null )
			cls = classes +" "+ cls;
			
		return (
			"<button type=\"button\" class=\""+cls+"\" onclick=\""+onaction+"\">" + 
				"<i class=\"bi bi-plus-circle icone\"></i>" +
				"remover" +
			"</a>"
		);
	}
		
	novoSelectOptionsHTML( params ) {
		let html = "";		
		if ( params.defaultOption !== undefined && params.defaultOption !== null )
			html += "<option value=\"" + params.defaultOption.valor + "\" checked>" + params.defaultOption.texto + "</option>";
			
		for( let i = 0; i < params.valores.length; i++ ) {
			html += 
				"<option value=\"" + params.valores[i] + "\">" + 
					( params.textos !== undefined && params.textos !== null ? params.textos[i] : params.valores[ i ] ) +
				"</option>";
		}
		
		return html;			
	}
	
}
export const htmlBuilder = new HTMLBuilder();