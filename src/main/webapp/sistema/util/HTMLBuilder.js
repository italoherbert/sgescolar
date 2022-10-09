
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
	
	novoLinkHTML( label, onaction, iconeClasses, classes ) {
		let cls = "icone-bt";
		if ( classes !== undefined && classes !== null )
			cls = classes +" "+ cls;
			
		return (
			"<a href=\"#!\" class=\""+cls+"\" onclick=\""+onaction+"\">" + 
				"<i class=\""+iconeClasses+" icone\"></i>" +
				label +
			"</a>"
		);
	}
	
	novoHREFLinkHTML( label, href, iconeClasses, classes ) {
		let cls = "icone-bt";
		if ( classes !== undefined && classes !== null )
			cls = classes +" "+ cls;
			
		return (
			"<a href=\""+href+"\" class=\""+cls+"\">" + 
				"<i class=\""+iconeClasses+" icone\"></i>" +
				label +
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
	
	novoCheckboxHTML( name_e_id, checked, label ) {
		let chkd = ( checked === 'true' || checked === true ? ' checked' : '' );
					
		let htmlLabel = "";
		if ( label !== undefined && label !== null )
			htmlLabel = "<label for=\"" + name_e_id + "\">" + label + "</label>";
											
		return (
			"<div class=\"form-check\">" +
				"<input type=\"checkbox\" id=\"" + name_e_id + "\" name=\"" + name_e_id + "\" class=\"form-check-control\"" + chkd + ">" +
				htmlLabel +
			"</div>"				
		);
	}
	
	novoSVGFontImageHTML( classes ) {
		return (
			"<i class=\""+classes+"\"></i>"				
		);
	}	
		
	novoSelectOptionsHTML( params ) {
		let html = "";

		let checkedValorPresente = (  params.checkedValor !== undefined && params.checkedValor !== null );
		let defaultOptionPresente = false;		
		
		if ( params.defaultOption !== undefined && params.defaultOption !== null ) {			
			let checked = "";		
			if ( checkedValorPresente === false || params.checkedValor === params.defaultOption.valor )			
				checked = " selected";			
			
			html += "<option value=\"" + params.defaultOption.valor + "\"" + checked + ">" + params.defaultOption.texto + "</option>";
			
			defaultOptionPresente = true;				
		}		
						
		for( let i = 0; i < params.valores.length; i++ ) {
			let checked = "";
			if ( checkedValorPresente === true ) {
				if ( params.checkedValor === params.valores[ i ] )			
					checked = " selected";
			} else {
				if ( i == 0 && defaultOptionPresente === false )
					checked = " selected";
			}
							
			html += 
				"<option value=\"" + params.valores[i] + "\"" + checked + ">" + 
					( params.textos !== undefined && params.textos !== null ? params.textos[i] : params.valores[ i ] ) +
				"</option>";
		}
		
		return html;			
	}
	
}
export const htmlBuilder = new HTMLBuilder();