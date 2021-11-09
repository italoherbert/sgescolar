

import {loginLayout} from './layout/login/LoginLayoutService.js';
import {appLayout} from './layout/app/AppLayoutService.js';

import {menuLateral} from './layout/app/menu-lateral/MenuLateralService.js';
import {menuNav} from './layout/app/menu-nav/MenuNavService.js';

import {loginForm} from './doc/login/form/LoginFormService.js';

import {escolaForm} from './doc/escola/form/EscolaFormService.js';
import {escolaTela} from './doc/escola/tela/EscolaTelaService.js';
import {escolaDetalhes} from './doc/escola/detalhes/EscolaDetalhesService.js';

import {alunoForm} from './doc/aluno/form/AlunoFormService.js';
import {alunoTela} from './doc/aluno/tela/AlunoTelaService.js';
import {alunoDetalhes} from './doc/aluno/detalhes/AlunoDetalhesService.js';

import {professorForm} from './doc/professor/form/ProfessorFormService.js';
import {professorTela} from './doc/professor/tela/ProfessorTelaService.js';
import {professorDetalhes} from './doc/professor/detalhes/ProfessorDetalhesService.js';

import {secretarioForm} from './doc/secretario/form/SecretarioFormService.js';
import {secretarioTela} from './doc/secretario/tela/SecretarioTelaService.js';
import {secretarioDetalhes} from './doc/secretario/detalhes/SecretarioDetalhesService.js';

import {sistema} from '../sistema/Sistema.js';

window.appLayout = appLayout;
window.loginLayout = loginLayout;

window.menuLateral = menuLateral;
window.menuNav = menuNav;

window.loginForm = loginForm;

window.escolaForm = escolaForm;
window.escolaTela = escolaTela;
window.escolaDetalhes = escolaDetalhes;

window.alunoForm = alunoForm;
window.alunoTela = alunoTela;
window.alunoDetalhes = alunoDetalhes;

window.professorForm = professorForm;
window.professorTela = professorTela;
window.professorDetalhes = professorDetalhes;

window.secretarioForm = secretarioForm;
window.secretarioTela = secretarioTela;
window.secretarioDetalhes = secretarioDetalhes;

let componentes = {
	'field' : { doc : 'componentes/modelo/field.html' },
	
	'login-layout' : { doc : 'componentes/layout/login/login-layout.html', service : loginLayout },	
	'app-layout'   : { doc : 'componentes/layout/app/app-layout.html', service : appLayout },		
	
	'menu-nav'     : { doc : 'componentes/layout/app/menu-nav/menu-nav.html', service : menuNav },	
	'menu-lateral' : { doc : 'componentes/layout/app/menu-lateral/menu-lateral.html', service : menuLateral },

	'login-form'   : { doc : 'componentes/doc/login/form/login-form.html', service : loginForm },

	'inicial'          : { doc : 'componentes/doc/home/home.html' },
	
	'endereco-form'     : { doc : 'componentes/doc/endereco/form/endereco-form.html' },
	'contato-info-form' : { doc : 'componentes/doc/contato-info/form/contato-info-form.html' },
	'usuario-form'      : { doc : 'componentes/doc/usuario/form/usuario-form.html' },
	'pessoa-form'       : { doc : 'componentes/doc/pessoa/form/pessoa-form.html' },
	'funcionario-form'       : { doc : 'componentes/doc/funcionario/form/funcionario-form.html' },

	'endereco-detalhes'      : { doc : 'componentes/doc/endereco/detalhes/endereco-detalhes.html' },
	'contato-info-detalhes'  : { doc : 'componentes/doc/contato-info/detalhes/contato-info-detalhes.html' },
	'usuario-detalhes'       : { doc : 'componentes/doc/usuario/detalhes/usuario-detalhes.html' },
	'pessoa-detalhes'        : { doc : 'componentes/doc/pessoa/detalhes/pessoa-detalhes.html' },
	'funcionario-detalhes'        : { doc : 'componentes/doc/funcionario/detalhes/funcionario-detalhes.html' },
	
	'pai-ou-mae-form'        : { doc : 'componentes/doc/aluno/form/paioumae/pai-ou-mae-form.html' },		
	'modal-pai-ou-mae-form'  : { doc : 'componentes/doc/aluno/form/paioumae/modal-pai-ou-mae-form.html' },
	'resumo-pai-ou-mae-form' : { doc : 'componentes/doc/aluno/form/paioumae/resumo-pai-ou-mae-form.html' },

	'pai-ou-mae-detalhes'    : { doc : 'componentes/doc/aluno/detalhes/paioumae/pai-ou-mae-detalhes.html' },
		
	'escola-form'     : { doc : 'componentes/doc/escola/form/escola-form.html', service : escolaForm },
	'escola-tela'     : { doc : 'componentes/doc/escola/tela/escola-tela.html', service : escolaTela },	
	'escola-detalhes' : { doc : 'componentes/doc/escola/detalhes/escola-detalhes.html', service : escolaDetalhes },	
		
	'aluno-form'     : { doc : 'componentes/doc/aluno/form/aluno-form.html', service : alunoForm },
	'aluno-tela'     : { doc : 'componentes/doc/aluno/tela/aluno-tela.html', service : alunoTela },
	'aluno-detalhes' : { doc : 'componentes/doc/aluno/detalhes/aluno-detalhes.html', service : alunoDetalhes },

	'professor-form'     : { doc : 'componentes/doc/professor/form/professor-form.html', service : professorForm },
	'professor-tela'     : { doc : 'componentes/doc/professor/tela/professor-tela.html', service : professorTela },
	'professor-detalhes' : { doc : 'componentes/doc/professor/detalhes/professor-detalhes.html', service : professorDetalhes },
	
	'secretario-form'     : { doc : 'componentes/doc/secretario/form/secretario-form.html', service : secretarioForm },
	'secretario-tela'     : { doc : 'componentes/doc/secretario/tela/secretario-tela.html', service : secretarioTela },
	'secretario-detalhes' : { doc : 'componentes/doc/secretario/detalhes/secretario-detalhes.html', service : secretarioDetalhes }
};

sistema.inicializa( componentes );
