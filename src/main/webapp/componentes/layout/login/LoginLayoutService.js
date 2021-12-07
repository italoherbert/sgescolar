
import {sistema} from '../../../sistema/Sistema.js';

export default class LoginLayoutService {
	
	onCarregado() {
		sistema.carregaComponente( 'login-sis-info', 'login-sis-info' );
		sistema.carregaComponente( 'login-form', 'login-form' );
	}
			
}
export const loginLayout = new LoginLayoutService();