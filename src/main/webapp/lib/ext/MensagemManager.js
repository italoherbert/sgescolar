
class MensagemManager {
		
	mostraMensagem( id, tipo, msg ) {		
		let className = this.getTipoClassName( tipo );
		let el = document.querySelector( "#"+id );
		if ( el === undefined || el === null )
			throw "Elemento de mensagem não encontrado pelo ID. ID="+id;
		
		el.innerHTML = "<div class='alert "+className+"'>"+msg+"</div>";		
		el.scrollIntoView();
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