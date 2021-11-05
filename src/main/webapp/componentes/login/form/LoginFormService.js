
import {sistema} from "../../../sistema/Sistema.js";

export default class LoginFormService {
	
	onTeclaPressionada( e ) {
		e.preventDefault();
		
		if ( e.keyCode === 13 )
			this.logon();
	}
	
	logon() {
		sistema.ajax( "POST", "/api/login/entrar", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				username : document.login_form.username.value,
				password : document.login_form.password.value,
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				sistema.globalVars['usuarioLogado'] = dados.usuario;
				sistema.globalVars['token'] = dados.token;
				sistema.globalVars['logado'] = true;
								
				sistema.carregaLayout( 'app-layout' );	
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );
	}
	
	logoff() {
		sistema.globalVars['logado'] = false;

		sistema.carregaLayout( 'login-layout' );
	}
	
}
export const loginForm = new LoginFormService();