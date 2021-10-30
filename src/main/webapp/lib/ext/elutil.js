			
function scrollTo( elid ) {
	let el = document.querySelector( "#"+elid );
	if ( el === undefined || el === null )
		throw "Elemento HTML não encontrado pelo ID. ID="+elid;

	el.scrollIntoView();			
}
	
function showHide( elid ) {
	let el = document.getElementById( elid );
	if ( el === undefined || el === null )
		throw "Elemento HTML não encontrado pelo ID. ID="+elid;
	
	if ( !( el.classList.contains( "visible" ) && el.classList.contains( "d-block" ) ) &&
		 	!( el.classList.contains( "hidden" ) && el.classList.contains( "d-none" ) ) ) {
		el.classList.add( "d-block" );
		el.classList.add( "visible" );
	}		
	
	el.classList.toggle( "d-block" );
	el.classList.toggle( "d-none" );
	el.classList.toggle( "visible" );
	el.classList.toggle( "hidden" );
}
	