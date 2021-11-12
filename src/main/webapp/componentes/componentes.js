

import {loginLayout} from './layout/login/LoginLayoutService.js';
import {appLayout} from './layout/app/AppLayoutService.js';

import {menuLateral} from './layout/app/menu-lateral/MenuLateralService.js';
import {menuNav} from './layout/app/menu-nav/MenuNavService.js';

import {loginForm} from './telas/login/form/LoginFormService.js';

import {usuarioTela} from './telas/usuario/tela/UsuarioTelaService.js';

import {escolaForm} from './telas/escola/form/EscolaFormService.js';
import {escolaTela} from './telas/escola/tela/EscolaTelaService.js';
import {escolaDetalhes} from './telas/escola/detalhes/EscolaDetalhesService.js';

import {alunoForm} from './telas/aluno/form/AlunoFormService.js';
import {alunoTela} from './telas/aluno/tela/AlunoTelaService.js';
import {alunoDetalhes} from './telas/aluno/detalhes/AlunoDetalhesService.js';

import {professorForm} from './telas/professor/form/ProfessorFormService.js';
import {professorTela} from './telas/professor/tela/ProfessorTelaService.js';
import {professorDetalhes} from './telas/professor/detalhes/ProfessorDetalhesService.js';

import {secretarioForm} from './telas/secretario/form/SecretarioFormService.js';
import {secretarioTela} from './telas/secretario/tela/SecretarioTelaService.js';
import {secretarioDetalhes} from './telas/secretario/detalhes/SecretarioDetalhesService.js';

import {sistema} from '../sistema/Sistema.js';

window.appLayout = appLayout;
window.loginLayout = loginLayout;

window.menuLateral = menuLateral;
window.menuNav = menuNav;

window.loginForm = loginForm;

window.usuarioTela = usuarioTela;

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
	'campo' : { doc : 'componentes/modelo/campo.html' },
	'tabela' : { doc : 'componentes/modelo/tabela.html' },
	
	'login-layout' : { doc : 'componentes/layout/login/login-layout.html', service : loginLayout },	
	'app-layout'   : { doc : 'componentes/layout/app/app-layout.html', service : appLayout },		
	
	'menu-nav'     : { doc : 'componentes/layout/app/menu-nav/menu-nav.html', service : menuNav },	
	'menu-lateral' : { doc : 'componentes/layout/app/menu-lateral/menu-lateral.html', service : menuLateral },
		
	'endereco-form'     : { doc : 'componentes/component/endereco/form/endereco-form.html' },
	'contato-info-form' : { doc : 'componentes/component/contato-info/form/contato-info-form.html' },
	'usuario-form'      : { doc : 'componentes/component/usuario/form/usuario-form.html' },
	'pessoa-form'       : { doc : 'componentes/component/pessoa/form/pessoa-form.html' },
	'funcionario-form'  : { doc : 'componentes/component/funcionario/form/funcionario-form.html' },

	'endereco-detalhes'      : { doc : 'componentes/component/endereco/detalhes/endereco-detalhes.html' },
	'contato-info-detalhes'  : { doc : 'componentes/component/contato-info/detalhes/contato-info-detalhes.html' },
	'usuario-detalhes'       : { doc : 'componentes/component/usuario/detalhes/usuario-detalhes.html' },
	'pessoa-detalhes'        : { doc : 'componentes/component/pessoa/detalhes/pessoa-detalhes.html' },
	'funcionario-detalhes'   : { doc : 'componentes/component/funcionario/detalhes/funcionario-detalhes.html' },

	'pai-ou-mae-detalhes'    : { doc : 'componentes/component/aluno/detalhes/paioumae/pai-ou-mae-detalhes.html' },
	'pai-ou-mae-form'        : { doc : 'componentes/component/aluno/form/paioumae/pai-ou-mae-form.html' },			
	'modal-pai-ou-mae-form'  : { doc : 'componentes/component/aluno/form/paioumae/modal-pai-ou-mae-form.html' },
	'resumo-pai-ou-mae-form' : { doc : 'componentes/component/aluno/form/paioumae/resumo-pai-ou-mae-form.html' },

	'inicial'      : { doc : 'componentes/telas/home/home.html' },
	'login-form'   : { doc : 'componentes/telas/login/form/login-form.html', service : loginForm },
		
	'usuario-tela' : { doc : 'componentes/telas/usuario/tela/usuario-tela.html', service : usuarioTela },	
		
	'escola-form'     : { doc : 'componentes/telas/escola/form/escola-form.html', service : escolaForm },
	'escola-tela'     : { doc : 'componentes/telas/escola/tela/escola-tela.html', service : escolaTela },	
	'escola-detalhes' : { doc : 'componentes/telas/escola/detalhes/escola-detalhes.html', service : escolaDetalhes },	
		
	'aluno-form'     : { doc : 'componentes/telas/aluno/form/aluno-form.html', service : alunoForm },
	'aluno-tela'     : { doc : 'componentes/telas/aluno/tela/aluno-tela.html', service : alunoTela },
	'aluno-detalhes' : { doc : 'componentes/telas/aluno/detalhes/aluno-detalhes.html', service : alunoDetalhes },

	'professor-form'     : { doc : 'componentes/telas/professor/form/professor-form.html', service : professorForm },
	'professor-tela'     : { doc : 'componentes/telas/professor/tela/professor-tela.html', service : professorTela },
	'professor-detalhes' : { doc : 'componentes/telas/professor/detalhes/professor-detalhes.html', service : professorDetalhes },
	
	'secretario-form'     : { doc : 'componentes/telas/secretario/form/secretario-form.html', service : secretarioForm },
	'secretario-tela'     : { doc : 'componentes/telas/secretario/tela/secretario-tela.html', service : secretarioTela },
	'secretario-detalhes' : { doc : 'componentes/telas/secretario/detalhes/secretario-detalhes.html', service : secretarioDetalhes }
};

sistema.inicializa( componentes );
