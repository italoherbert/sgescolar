
class Conversor {
	
	formataData( date ) {
		if ( date === null || date === undefined || date === '' )
			return "Formato de data não reconhecido";
		
		return moment( date ).format( "DD/MM/YYYY" );
	}
	
	formataReal( valor ) {
		let v = parseFloat( parseFloat( valor ).toFixed( 2 ) );
		if ( v === 0 ) 
			return "R$ 0,00";
				
		if ( Math.abs( v ) >= 1.0 ) {
			let n = parseInt( v * 100 );
			let s = ""+n;
			return "R$ "+s.substring( 0, s.length-2 ) + ',' + s.substring( s.length-2, s.length );		
		} else {
			return "R$ " +( ( ""+v ).replace( '.', ',' ) );			
		}				
	}
			
	formataFloat( valor ) {
		if ( (""+valor) === "0" ) 
			return "0";
		
		let v = (""+valor).split( '.' );
		
		let frac = 0;
		if ( v.length === 2 )
			frac = parseInt( v[1] );
		
		return ( frac === 0 ? v[0] : v[0]+','+v[1] );				
	}
	
	valorFloat( valor ) {
		if ( isNaN( (""+valor).replace( ',', '.' ) ) === true )
			return valor;
		
		if ( (""+valor) === "0" ) 
			return 0;
		
		return parseFloat( parseFloat( (""+valor).replace( ',', '.' ) ).toFixed( 2 ) );
	}	
	
}