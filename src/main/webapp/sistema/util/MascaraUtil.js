
export default class MascaraUtil {
	
	oninputCPF( e ) {		
		let v = e.target.value.replace( /\D/g, '' ).match( /(\d{0,3})(\d{0,3})(\d{0,3})(\d{0,2})/ );			
		e.target.value = ( v[1] ? v[1] + ( v[2] ? '.' + v[2] + ( v[3] ? '.' + v[3] + ( v[4] ? '-' + v[4] : '' ) : '' ) : '' ) : '' );							
	}
	
	oninputCEP( e ) {
		let v = e.target.value.replace( /\D/g, '' ).match( /(\d{0,5})(\d{0,3})/ );			
		e.target.value = ( v[1] ? v[1] + ( v[2] ? '-' + v[2] : '' ) : '' );								
	}
	
}
export const mascaraUtil = new MascaraUtil(); 