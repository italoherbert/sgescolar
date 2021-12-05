
import {sistema} from '../../../sistema/Sistema.js';

export default class HomeService {
		
	onCarregado() {		
		if ( sistema.globalVars.perfil.name === 'PROFESSOR' ) {
			
		}
	}	
		
}
export const homeTela = new HomeService();