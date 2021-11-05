
import {sistema} from '../../../sistema/Sistema.js';

export default class LoginLayout {
	
	onCarregado() {
		sistema.carregaComponente( 'login-form', 'login-form' );
	}
		
}
export const loginLayout = new LoginLayout();