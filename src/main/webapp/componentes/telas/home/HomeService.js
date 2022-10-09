
import {sistema} from '../../../sistema/Sistema.js';

export default class HomeService {
		
	onCarregado() {		
		if ( sistema.globalVars.perfil.name === 'PROFESSOR' ) {
			let professorId = sistema.globalVars.entidadeId;
			sistema.ajax( 'GET', '/api/professor/verifica-se-alocado/'+professorId, {
				erro : (msg) => {
					sistema.mostraMensagemAlerta( 'mensagem-el', msg );
				}
			} );
		}
	}	
		
}
export const homeTela = new HomeService();