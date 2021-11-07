
export default class ModeloManager {
	
	criaLinkDetalhesHTML( onaction, classes ) {
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
	
	criaLinkRemoverHTML( onaction, classes ) {
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
	
	criarButtonRegistroHTML( onaction, classes ) {
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
	
}
export const modeloManager = new ModeloManager();