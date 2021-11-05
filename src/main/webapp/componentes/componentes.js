
import {loginForm} from './login/form/LoginFormService.js';
import {loginLayout} from './login/layout/LoginLayoutService.js';

import {appLayout} from './app/layout/AppLayoutService.js';

import {alunoForm} from './app/aluno/form/AlunoFormService.js';
import {alunoTela} from './app/aluno/tela/AlunoTelaService.js';

import {sistema} from '../sistema/Sistema.js';

window.loginForm = loginForm;
window.loginLayout = loginLayout;
window.appLayout = appLayout;

window.alunoForm = alunoForm;
window.alunoTela = alunoTela;

let componentes = {
	'confirm-modal' : { doc : 'lib/ext/confirm-modal/confirm-modal.html' },
	
	'login-layout' : { doc : 'componentes/login/layout/login-layout.html', service : loginLayout },
	'login-form'   : { doc : 'componentes/login/form/login-form.html', service : loginForm },
	
	'app-layout'   : { doc : 'componentes/app/layout/app-layout.html', service : appLayout },		
	'navbar-menu'  : { doc : 'componentes/app/layout/navbar/navbar-menu.html' },

	'admin-menu'       : { doc : 'componentes/app/layout/menu/admin-menu.html' },
	'secretario-menu'  : { doc : 'componentes/app/layout/menu/secretario-menu.html' },
	'professor-menu'   : { doc : 'componentes/app/layout/menu/professor-menu.html' },
	'aluno-menu'       : { doc : 'componentes/app/layout/menu/aluno-menu.html' },
	
	'endereco-form'  : { doc : 'componentes/app/endereco/endereco-form.html' },
	'contatoinfo-form'  : { doc : 'componentes/app/contatoinfo/contatoinfo-form.html' },
	'usuario-form'  : { doc : 'componentes/app/usuario/usuario-form.html' },
	'pessoa-form'  : { doc : 'componentes/app/pessoa/pessoa-form.html' },
	
	'pai-ou-mae-form'  : { doc : 'componentes/app/aluno/form/paioumae/pai-ou-mae-form.html' },		
	'modal-pai-ou-mae-form'  : { doc : 'componentes/app/aluno/form/paioumae/modal-pai-ou-mae-form.html' },
	'resumo-pai-ou-mae-form'  : { doc : 'componentes/app/aluno/form/paioumae/resumo-pai-ou-mae-form.html' },
		
	'aluno-form'  : { doc : 'componentes/app/aluno/form/aluno-form.html', service : alunoForm },
	'aluno-tela'  : { doc : 'componentes/app/aluno/tela/aluno-tela.html', service : alunoTela }
};

sistema.inicializa( componentes );
