
import {loginForm} from './login/form/LoginForm.js';
import {loginLayout} from './login/layout/LoginLayout.js';

import {appLayout} from './app/layout/AppLayout.js';

import {alunoForm} from './app/aluno/form/AlunoForm.js';
import {alunoTela} from './app/aluno/tela/AlunoTela.js';

import {sistema} from '../sistema/Sistema.js';

window.loginForm = loginForm;
window.loginLayout = loginLayout;
window.appLayout = appLayout;

window.alunoForm = alunoForm;
window.alunoTela = alunoTela;

let componentes = {
	'confirm-modal' : { pagina : 'lib/ext/confirm-modal/confirm-modal.html' },
	
	'login-layout' : { pagina : 'componentes/login/layout/login-layout.html', jsObj : loginLayout },
	'login-form'   : { pagina : 'componentes/login/form/login-form.html', jsObj : loginForm },
	
	'app-layout'   : { pagina : 'componentes/app/layout/app-layout.html', jsObj : appLayout },		
	'navbar-menu'  : { pagina : 'componentes/app/layout/navbar/navbar-menu.html' },

	'admin-menu'       : { pagina : 'componentes/app/layout/menu/admin-menu.html' },
	'secretario-menu'  : { pagina : 'componentes/app/layout/menu/secretario-menu.html' },
	'professor-menu'   : { pagina : 'componentes/app/layout/menu/professor-menu.html' },
	'aluno-menu'       : { pagina : 'componentes/app/layout/menu/aluno-menu.html' },
	
	'endereco-form-comp'  : { pagina : 'componentes/app/endereco/endereco-form-comp.html' },
	'contatoinfo-form-comp'  : { pagina : 'componentes/app/contatoinfo/contatoinfo-form-comp.html' },
	'usuario-form-comp'  : { pagina : 'componentes/app/usuario/usuario-form-comp.html' },
	'pessoa-form-comp'  : { pagina : 'componentes/app/pessoa/pessoa-form-comp.html' },
	
	'pai-ou-mae-form-comp'  : { pagina : 'componentes/app/aluno/form/paioumae/pai-ou-mae-form-comp.html' },		
	'modal-pai-ou-mae-form-comp'  : { pagina : 'componentes/app/aluno/form/paioumae/modal-pai-ou-mae-form-comp.html' },
	'resumo-pai-ou-mae-form-comp'  : { pagina : 'componentes/app/aluno/form/paioumae/resumo-pai-ou-mae-form-comp.html' },
		
	'aluno-form'  : { pagina : 'componentes/app/aluno/form/aluno-form.html', jsObj : alunoForm },
	'aluno-tela'  : { pagina : 'componentes/app/aluno/tela/aluno-tela.html', jsObj : alunoTela }
};

sistema.inicializa( componentes );
