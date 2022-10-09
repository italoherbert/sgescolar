
export default class MensagemManager {
		
	mostraMensagem( id, tipo, msg, scroll ) {		
		let className = this.getTipoClassName( tipo );
		let el = document.querySelector( "#"+id );
		if ( el === undefined || el === null )
			throw "Elemento de mensagem não encontrado pelo ID. ID="+id;
		
		el.innerHTML = "<div class='alert "+className+"'>"+msg+"</div>";
		
		if ( scroll !== undefined && scroll !== null ) {
			if ( scroll === true )		
				el.scrollIntoView();
		} else {
			el.scrollIntoView();
		}
	}
		
	limpaMensagem( id ) {
		let el = document.querySelector( "#"+id );
		if ( el === undefined || el === null )
			throw "Elemento de mensagem não encontrado pelo ID. ID="+id;
		
		el.innerHTML = "";
	}
	
	getTipoClassName( tipo ) {
		if ( tipo === 'info' )
			return 'alert-primary';
		if ( tipo === 'erro' )
			return 'alert-danger';
		if ( tipo === 'alerta' )
			return 'alert-warning';
		return 'alert-secondary';
	}
	
}