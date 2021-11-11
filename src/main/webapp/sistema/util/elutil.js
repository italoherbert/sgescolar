			
export function scrollTo( elid ) {
	let el = document.querySelector( "#"+elid );
	if ( el === undefined || el === null )
		throw "Elemento HTML não encontrado pelo ID. ID="+elid;

	el.scrollIntoView();			
}

export function show( elid ) {
	let el = document.getElementById( elid );
	if ( el === undefined || el === null )
		throw "Elemento HTML não encontrado pelo ID. ID="+elid;
		
	el.classList.remove( "hidden" );
	el.classList.remove( 'd-none' );
	el.classList.add( 'd-inline-block' );
	el.classList.add( 'visible' );
}

export function hide( elid ) {
	let el = document.getElementById( elid );
	if ( el === undefined || el === null )
		throw "Elemento HTML não encontrado pelo ID. ID="+elid;
		
	el.classList.remove( 'd-inline-block' );
	el.classList.remove( 'd-block' );
	el.classList.remove( 'visible' );	
	el.classList.add( "hidden" );
	el.classList.add( 'd-none' );
}
	
export function showHide( elid ) {
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
	