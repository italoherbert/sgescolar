
export default class HTMLBuilder {
	
	novoConteudoTHeadHTML( campos ) {
		let html = "<tr>";
		for( let i = 0; i < campos.length; i++ )
			html += "<th>" + campos[i] + "</th>";
		html += "</tr>";
		
		return html;
	}
	
	novoConteudoTBodyHTML( dados ) {
		let html = "";
		for( let i = 0; i < dados.length; i++ ) {
			html += "<tr>";
			for( let j = 0; j < dados[ i ].length; j++ ) {
				html += "<td>" + dados[ i ][ j ] + "</td>";
			}
			html += "</tr>";
		}
		
		return html;
	}
	
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
	
	novoLinkEditarHTML( onaction, classes ) {
		let cls = "icone-bt link-primary";
		if ( classes !== undefined && classes !== null )
			cls = classes +" "+ cls;
			
		return (
			"<a href=\"#!\" class=\""+cls+"\" onclick=\""+onaction+"\">" + 
				"<i class=\"bi bi-pencil icone\"></i>" +
				"editar" +
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