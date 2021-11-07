
import {loginForm} from './login/form/LoginFormService.js';
import {loginLayout} from './login/layout/LoginLayoutService.js';

import {appLayout} from './app/layout/AppLayoutService.js';

import {alunoForm} from './app/aluno/form/AlunoFormService.js';
import {alunoTela} from './app/aluno/tela/AlunoTelaService.js';
import {alunoDetalhes} from './app/aluno/detalhes/AlunoDetalhesService.js';

import {sistema} from '../sistema/Sistema.js';

window.loginForm = loginForm;
window.loginLayout = loginLayout;
window.appLayout = appLayout;

window.alunoForm = alunoForm;
window.alunoTela = alunoTela;
window.alunoDetalhes = alunoDetalhes;

let componentes = {
	'field' : { doc : 'componentes/modelo/field.html' },
	
	'login-layout' : { doc : 'componentes/login/layout/login-layout.html', service : loginLayout },
	'login-form'   : { doc : 'componentes/login/form/login-form.html', service : loginForm },
	
	'app-layout'   : { doc : 'componentes/app/layout/app-layout.html', service : appLayout },		
	'navbar-menu'  : { doc : 'componentes/app/layout/navbar/navbar-menu.html' },

	'admin-menu'       : { doc : 'componentes/app/layout/menu/admin-menu.html' },
	'secretario-menu'  : { doc : 'componentes/app/layout/menu/secretario-menu.html' },
	'professor-menu'   : { doc : 'componentes/app/layout/menu/professor-menu.html' },
	'aluno-menu'       : { doc : 'componentes/app/layout/menu/aluno-menu.html' },
	
	'endereco-form'     : { doc : 'componentes/app/endereco/form/endereco-form.html' },
	'contato-info-form' : { doc : 'componentes/app/contato-info/form/contato-info-form.html' },
	'usuario-form'      : { doc : 'componentes/app/usuario/form/usuario-form.html' },
	'pessoa-form'       : { doc : 'componentes/app/pessoa/form/pessoa-form.html' },

	'endereco-detalhes'      : { doc : 'componentes/app/endereco/detalhes/endereco-detalhes.html' },
	'contato-info-detalhes'  : { doc : 'componentes/app/contato-info/detalhes/contato-info-detalhes.html' },
	'usuario-detalhes'       : { doc : 'componentes/app/usuario/detalhes/usuario-detalhes.html' },
	'pessoa-detalhes'        : { doc : 'componentes/app/pessoa/detalhes/pessoa-detalhes.html' },
	
	'pai-ou-mae-form'        : { doc : 'componentes/app/aluno/form/paioumae/pai-ou-mae-form.html' },		
	'modal-pai-ou-mae-form'  : { doc : 'componentes/app/aluno/form/paioumae/modal-pai-ou-mae-form.html' },
	'resumo-pai-ou-mae-form' : { doc : 'componentes/app/aluno/form/paioumae/resumo-pai-ou-mae-form.html' },

	'pai-ou-mae-detalhes'    : { doc : 'componentes/app/aluno/detalhes/paioumae/pai-ou-mae-detalhes.html' },
		
	'aluno-form'     : { doc : 'componentes/app/aluno/form/aluno-form.html', service : alunoForm },
	'aluno-tela'     : { doc : 'componentes/app/aluno/tela/aluno-tela.html', service : alunoTela },
	'aluno-detalhes' : { doc : 'componentes/app/aluno/detalhes/aluno-detalhes.html', service : alunoDetalhes }
};

sistema.inicializa( componentes );
