
import {loginLayout} from './layout/login/LoginLayoutService.js';
import {appLayout} from './layout/app/AppLayoutService.js';

import {menuLateral} from './layout/app/menu-lateral/MenuLateralService.js';
import {menuNav} from './layout/app/menu-nav/MenuNavService.js';

import {loginForm} from './telas/login/form/LoginFormService.js';

import {usuarioForm} from './telas/usuario/form/UsuarioFormService.js';
import {usuarioTela} from './telas/usuario/tela/UsuarioTelaService.js';
import {usuarioDetalhes} from './telas/usuario/detalhes/UsuarioDetalhesService.js';

import {usuarioGrupoForm} from './telas/usuario-grupo/form/UsuarioGrupoFormService.js';
import {usuarioGrupoTela} from './telas/usuario-grupo/tela/UsuarioGrupoTelaService.js';
import {usuarioGrupoDetalhes} from './telas/usuario-grupo/detalhes/UsuarioGrupoDetalhesService.js';

import {recursoForm} from './telas/recurso/form/RecursoFormService.js';
import {recursoTela} from './telas/recurso/tela/RecursoTelaService.js';
import {recursoDetalhes} from './telas/recurso/detalhes/RecursoDetalhesService.js';

import {instituicaoForm} from './telas/instituicao/form/InstituicaoFormService.js';
import {instituicaoDetalhes} from './telas/instituicao/detalhes/InstituicaoDetalhesService.js';

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

import {anoletivoForm} from './telas/anoletivo/form/AnoLetivoFormService.js';
import {anoletivoTela} from './telas/anoletivo/tela/AnoLetivoTelaService.js';
import {anoletivoDetalhes} from './telas/anoletivo/detalhes/AnoLetivoDetalhesService.js';

import {feriadoTela} from './telas/feriado/FeriadoTelaService.js';
import {periodoTela} from './telas/periodo/PeriodoTelaService.js';

import {cursoTela} from './telas/curso/tela/CursoTelaService.js';

import {sistema} from '../sistema/Sistema.js';

window.appLayout = appLayout;
window.loginLayout = loginLayout;

window.menuLateral = menuLateral;
window.menuNav = menuNav;

window.loginForm = loginForm;

window.usuarioForm = usuarioForm;
window.usuarioTela = usuarioTela;
window.usuarioDetalhes = usuarioDetalhes;

window.usuarioGrupoForm = usuarioGrupoForm;
window.usuarioGrupoTela = usuarioGrupoTela;
window.usuarioGrupoDetalhes = usuarioGrupoDetalhes;

window.recursoForm = recursoForm;
window.recursoTela = recursoTela;
window.recursoDetalhes = recursoDetalhes;

window.instituicaoForm = instituicaoForm;
window.instituicaoDetalhes = instituicaoDetalhes;

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

window.anoletivoForm = anoletivoForm;
window.anoletivoTela = anoletivoTela;
window.anoletivoDetalhes = anoletivoDetalhes;

window.feriadoTela = feriadoTela;
window.periodoTela = periodoTela;

window.cursoTela = cursoTela;

let componentes = {
	'campo' : { doc : 'componentes/modelo/campo.html' },
	'lista-campo' : { doc : 'componentes/modelo/lista-campo.html' },
	
	'login-layout' : { doc : 'componentes/layout/login/login-layout.html', service : loginLayout },	
	'app-layout'   : { doc : 'componentes/layout/app/app-layout.html', service : appLayout },		
	
	'menu-nav'     : { doc : 'componentes/layout/app/menu-nav/menu-nav.html', service : menuNav },	
	'menu-lateral' : { doc : 'componentes/layout/app/menu-lateral/menu-lateral.html', service : menuLateral },
		
	'tabela'         : { doc : 'componentes/component/tabela/tabela.html' },
	'calendario-mes' : { doc : 'componentes/component/calendario/mes/calendario-mes.html' },
	'calendario'     : { doc : 'componentes/component/calendario/calendario.html' },
		
	'endereco-form'       : { doc : 'componentes/component/endereco/form/endereco-form.html' },
	'endereco-local-form' : { doc : 'componentes/component/endereco-local/form/endereco-local-form.html' },
	'contato-info-form'   : { doc : 'componentes/component/contato-info/form/contato-info-form.html' },
	'usuario-form'        : { doc : 'componentes/component/usuario/form/usuario-form.html' },
	'pessoa-form'         : { doc : 'componentes/component/pessoa/form/pessoa-form.html' },
	'funcionario-form'    : { doc : 'componentes/component/funcionario/form/funcionario-form.html' },

	'endereco-detalhes'       : { doc : 'componentes/component/endereco/detalhes/endereco-detalhes.html' },
	'endereco-local-detalhes' : { doc : 'componentes/component/endereco-local/detalhes/endereco-local-detalhes.html' },
	'contato-info-detalhes'   : { doc : 'componentes/component/contato-info/detalhes/contato-info-detalhes.html' },
	'usuario-detalhes'        : { doc : 'componentes/component/usuario/detalhes/usuario-detalhes.html' },
	'pessoa-detalhes'         : { doc : 'componentes/component/pessoa/detalhes/pessoa-detalhes.html' },
	'funcionario-detalhes'    : { doc : 'componentes/component/funcionario/detalhes/funcionario-detalhes.html' },
	
	'anoletivo-select-form'   : { doc : 'componentes/component/anoletivo-select/anoletivo-select-form.html' },

	'inicial'      : { doc : 'componentes/telas/home/home.html' },
	'login-form'   : { doc : 'componentes/telas/login/form/login-form.html', service : loginForm },
		
	'usuario-form-end'   : { doc : 'componentes/telas/usuario/form/usuario-form-end.html' },
	'usuario-form-2'     : { doc : 'componentes/telas/usuario/form/usuario-form.html', service : usuarioForm },	
	'usuario-tela'       : { doc : 'componentes/telas/usuario/tela/usuario-tela.html', service : usuarioTela },	
	'usuario-detalhes-2' : { doc : 'componentes/telas/usuario/detalhes/usuario-detalhes.html', service : usuarioDetalhes },
	
	'usuario-grupo-form'     : { doc : 'componentes/telas/usuario-grupo/form/usuario-grupo-form.html', service : usuarioGrupoForm },	
	'usuario-grupo-tela'     : { doc : 'componentes/telas/usuario-grupo/tela/usuario-grupo-tela.html', service : usuarioGrupoTela },	
	'usuario-grupo-detalhes' : { doc : 'componentes/telas/usuario-grupo/detalhes/usuario-grupo-detalhes.html', service : usuarioGrupoDetalhes },	
	
	'recurso-form'     : { doc : 'componentes/telas/recurso/form/recurso-form.html', service : recursoForm },	
	'recurso-tela'     : { doc : 'componentes/telas/recurso/tela/recurso-tela.html', service : recursoTela },	
	'recurso-detalhes' : { doc : 'componentes/telas/recurso/detalhes/recurso-detalhes.html', service : recursoDetalhes },	
		
	'instituicao-form'     : { doc : 'componentes/telas/instituicao/form/instituicao-form.html', service : instituicaoForm },
	'instituicao-detalhes' : { doc : 'componentes/telas/instituicao/detalhes/instituicao-detalhes.html', service : instituicaoDetalhes },	
	
	'escola-form'     : { doc : 'componentes/telas/escola/form/escola-form.html', service : escolaForm },
	'escola-tela'     : { doc : 'componentes/telas/escola/tela/escola-tela.html', service : escolaTela },	
	'escola-detalhes' : { doc : 'componentes/telas/escola/detalhes/escola-detalhes.html', service : escolaDetalhes },	

	'pai-ou-mae-detalhes'    : { doc : 'componentes/telas/aluno/detalhes/paioumae/pai-ou-mae-detalhes.html' },
	'pai-ou-mae-form'        : { doc : 'componentes/telas/aluno/form/paioumae/pai-ou-mae-form.html' },			
	'modal-pai-ou-mae-form'  : { doc : 'componentes/telas/aluno/form/paioumae/modal-pai-ou-mae-form.html' },
	'resumo-pai-ou-mae-form' : { doc : 'componentes/telas/aluno/form/paioumae/resumo-pai-ou-mae-form.html' },
			
	'aluno-form'     : { doc : 'componentes/telas/aluno/form/aluno-form.html', service : alunoForm },
	'aluno-tela'     : { doc : 'componentes/telas/aluno/tela/aluno-tela.html', service : alunoTela },
	'aluno-detalhes' : { doc : 'componentes/telas/aluno/detalhes/aluno-detalhes.html', service : alunoDetalhes },

	'professor-form'     : { doc : 'componentes/telas/professor/form/professor-form.html', service : professorForm },
	'professor-tela'     : { doc : 'componentes/telas/professor/tela/professor-tela.html', service : professorTela },
	'professor-detalhes' : { doc : 'componentes/telas/professor/detalhes/professor-detalhes.html', service : professorDetalhes },
	
	'secretario-form'     : { doc : 'componentes/telas/secretario/form/secretario-form.html', service : secretarioForm },
	'secretario-tela'     : { doc : 'componentes/telas/secretario/tela/secretario-tela.html', service : secretarioTela },
	'secretario-detalhes' : { doc : 'componentes/telas/secretario/detalhes/secretario-detalhes.html', service : secretarioDetalhes },

	'feriado-form' : { doc : 'componentes/telas/feriado/form/feriado-form.html' },
	'feriado-tela' : { doc : 'componentes/telas/feriado/feriado-tela.html', service : feriadoTela },
	
	'periodo-form' : { doc : 'componentes/telas/periodo/form/periodo-form.html' },
	'periodo-tela' : { doc : 'componentes/telas/periodo/periodo-tela.html', service : periodoTela },
	
	'anoletivo-form'     : { doc : 'componentes/telas/anoletivo/form/anoletivo-form.html', service : anoletivoForm },
	'anoletivo-tela'     : { doc : 'componentes/telas/anoletivo/tela/anoletivo-tela.html', service : anoletivoTela },
	'anoletivo-detalhes' : { doc : 'componentes/telas/anoletivo/detalhes/anoletivo-detalhes.html', service : anoletivoDetalhes },
	
	'curso-tela' : { doc : 'componentes/telas/curso/tela/curso-tela.html', service : cursoTela }
};

sistema.inicializa( componentes );
