
import {loginLayout} from './layout/login/LoginLayoutService.js';
import {appLayout} from './layout/app/AppLayoutService.js';

import {menuLateral} from './layout/app/menu-lateral/MenuLateralService.js';
import {menuNav} from './layout/app/menu-nav/MenuNavService.js';
import {perfilService} from './layout/app/perfil/PerfilService.js';

import {loginForm} from './telas/login/form/LoginFormService.js';

import {homeTela} from './telas/home/HomeService.js';

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
import {instituicaoTela} from './telas/instituicao/tela/InstituicaoTelaService.js';
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

import {administradorForm} from './telas/administrador/form/AdministradorFormService.js';
import {administradorTela} from './telas/administrador/tela/AdministradorTelaService.js';
import {administradorDetalhes} from './telas/administrador/detalhes/AdministradorDetalhesService.js';

import {anoletivoForm} from './telas/anoletivo/form/AnoLetivoFormService.js';
import {anoletivoTela} from './telas/anoletivo/tela/AnoLetivoTelaService.js';
import {anoletivoDetalhes} from './telas/anoletivo/detalhes/AnoLetivoDetalhesService.js';

import {feriadoTela} from './telas/feriado/FeriadoTelaService.js';
import {periodoTela} from './telas/periodo/PeriodoTelaService.js';

import {cursoForm} from './telas/curso/form/CursoFormService.js';
import {cursoTela} from './telas/curso/tela/CursoTelaService.js';
import {cursoDetalhes} from './telas/curso/detalhes/CursoDetalhesService.js';

import {serieForm} from './telas/serie/form/SerieFormService.js';
import {serieTela} from './telas/serie/tela/SerieTelaService.js';
import {serieDetalhes} from './telas/serie/detalhes/SerieDetalhesService.js';

import {disciplinaForm} from './telas/disciplina/form/DisciplinaFormService.js';
import {disciplinaTela} from './telas/disciplina/tela/DisciplinaTelaService.js';
import {disciplinaDetalhes} from './telas/disciplina/detalhes/DisciplinaDetalhesService.js';

import {turmaForm} from './telas/turma/form/TurmaFormService.js';
import {turmaTela} from './telas/turma/tela/TurmaTelaService.js';
import {turmaDetalhes} from './telas/turma/detalhes/TurmaDetalhesService.js';

import {turmaDisciplinaForm} from './telas/turma-disciplina/form/TurmaDisciplinaFormService.js';
import {turmaDisciplinaTela} from './telas/turma-disciplina/tela/TurmaDisciplinaTelaService.js';

import {professorAlocacaoForm} from './telas/professor-alocacao/form/ProfessorAlocacaoFormService.js';
import {professorAlocacaoTela} from './telas/professor-alocacao/tela/ProfessorAlocacaoTelaService.js';

import {horarioTela} from './telas/horario/HorarioTelaService.js';

import {matriculaForm} from './telas/matricula/form/MatriculaFormService.js';
import {matriculaTela} from './telas/matricula/tela/MatriculaTelaService.js';

import {listaFrequencia} from './telas/frequencia/ListaFrequenciaService.js';

import {avaliacaoTela} from './telas/avaliacao/tela/AvaliacaoTelaService.js';
import {avaliacaoDetalhes} from './telas/avaliacao/detalhes/AvaliacaoDetalhesService.js';
import {agendamentoAvaliacaoForm} from './telas/avaliacao/agendamento/AgendamentoAvaliacaoFormService.js';
import {resultadoAvaliacaoForm} from './telas/avaliacao/resultado/ResultadoAvaliacaoFormService.js';

import {planejamentoForm} from './telas/planejamento/form/PlanejamentoFormService.js';
import {planejamentoTela} from './telas/planejamento/tela/PlanejamentoTelaService.js';
import {planejamentoDetalhes} from './telas/planejamento/detalhes/PlanejamentoDetalhesService.js';

import {alunoHorario} from './telas/aluno-horario/AlunoHorarioService.js';
import {alunoBoletim} from './telas/aluno-boletim/AlunoBoletimService.js';

import {sistema} from '../sistema/Sistema.js';

window.appLayout = appLayout;
window.loginLayout = loginLayout;

window.menuLateral = menuLateral;
window.menuNav = menuNav;
window.perfilService = perfilService;

window.loginForm = loginForm;

window.homeTela = homeTela;

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
window.instituicaoTela = instituicaoTela;
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

window.administradorForm = administradorForm;
window.administradorTela = administradorTela;
window.administradorDetalhes = administradorDetalhes;

window.anoletivoForm = anoletivoForm;
window.anoletivoTela = anoletivoTela;
window.anoletivoDetalhes = anoletivoDetalhes;

window.feriadoTela = feriadoTela;
window.periodoTela = periodoTela;

window.cursoForm = cursoForm;
window.cursoTela = cursoTela;
window.cursoDetalhes = cursoDetalhes;

window.serieForm = serieForm;
window.serieTela = serieTela;
window.serieDetalhes = serieDetalhes;

window.disciplinaForm = disciplinaForm;
window.disciplinaTela = disciplinaTela;
window.disciplinaDetalhes = disciplinaDetalhes;

window.turmaForm = turmaForm;
window.turmaTela = turmaTela;
window.turmaDetalhes = turmaDetalhes;

window.turmaDisciplinaForm = turmaDisciplinaForm;
window.turmaDisciplinaTela = turmaDisciplinaTela;

window.professorAlocacaoForm = professorAlocacaoForm;
window.professorAlocacaoTela = professorAlocacaoTela;

window.horarioTela = horarioTela;

window.matriculaForm = matriculaForm;
window.matriculaTela = matriculaTela;

window.listaFrequencia = listaFrequencia;

window.avaliacaoTela = avaliacaoTela;
window.avaliacaoDetalhes = avaliacaoDetalhes;
window.agendamentoAvaliacaoForm = agendamentoAvaliacaoForm;
window.resultadoAvaliacaoForm = resultadoAvaliacaoForm;

window.planejamentoForm = planejamentoForm;
window.planejamentoTela = planejamentoTela;
window.planejamentoDetalhes = planejamentoDetalhes;

window.alunoHorario = alunoHorario;
window.alunoBoletim = alunoBoletim;

let componentes = {
	'campo'        : { doc : 'componentes/modelo/campo.html' },
	'lista-campo'  : { doc : 'componentes/modelo/lista-campo.html' },
	
	'login-layout' : { doc : 'componentes/layout/login/login-layout.html', service : loginLayout },	
	'app-layout'   : { doc : 'componentes/layout/app/app-layout.html', service : appLayout },		
	
	'login-sis-info' 	   : { doc : 'componentes/layout/login/login-sis-info.html' },		
	
	'menu-nav'      : { doc : 'componentes/layout/app/menu-nav/menu-nav.html', service : menuNav },	
	'menu-lateral'  : { doc : 'componentes/layout/app/menu-lateral/menu-lateral.html', service : menuLateral },
	'perfil-form'   : { doc : 'componentes/layout/app/perfil/perfil-form.html', service : perfilService },
		
	'tabela'       : { doc : 'componentes/component/tabela/tabela.html' },
	'horario'      : { doc : 'componentes/component/horario/horario.html'},
	
	'calendario-mes' : { doc : 'componentes/component/calendario/mes/calendario-mes.html' },
	'calendario'     : { doc : 'componentes/component/calendario/calendario.html' },

	'autocomplete-input' : { doc : 'componentes/component/autocomplete/autocomplete-input.html' },
		
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

	'inicial'      : { doc : 'componentes/telas/home/home.html', service : homeTela },
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
	'instituicao-tela'     : { doc : 'componentes/telas/instituicao/tela/instituicao-tela.html', service : instituicaoTela },
	'instituicao-detalhes' : { doc : 'componentes/telas/instituicao/detalhes/instituicao-detalhes.html', service : instituicaoDetalhes },	
	
	'escola-form'     : { doc : 'componentes/telas/escola/form/escola-form.html', service : escolaForm },
	'escola-tela'     : { doc : 'componentes/telas/escola/tela/escola-tela.html', service : escolaTela },	
	'escola-detalhes' : { doc : 'componentes/telas/escola/detalhes/escola-detalhes.html', service : escolaDetalhes },	

	'responsavel-detalhes'    : { doc : 'componentes/telas/aluno/detalhes/responsavel/responsavel-detalhes.html' },
	'responsavel-form'        : { doc : 'componentes/telas/aluno/form/responsavel/responsavel-form.html' },			
	'modal-responsavel-form'  : { doc : 'componentes/telas/aluno/form/responsavel/modal-responsavel-form.html' },
	'resumo-responsavel-form' : { doc : 'componentes/telas/aluno/form/responsavel/resumo-responsavel-form.html' },
			
	'aluno-form'     : { doc : 'componentes/telas/aluno/form/aluno-form.html', service : alunoForm },
	'aluno-tela'     : { doc : 'componentes/telas/aluno/tela/aluno-tela.html', service : alunoTela },
	'aluno-detalhes' : { doc : 'componentes/telas/aluno/detalhes/aluno-detalhes.html', service : alunoDetalhes },

	'professor-form'     : { doc : 'componentes/telas/professor/form/professor-form.html', service : professorForm },
	'professor-tela'     : { doc : 'componentes/telas/professor/tela/professor-tela.html', service : professorTela },
	'professor-detalhes' : { doc : 'componentes/telas/professor/detalhes/professor-detalhes.html', service : professorDetalhes },
	
	'secretario-form'     : { doc : 'componentes/telas/secretario/form/secretario-form.html', service : secretarioForm },
	'secretario-tela'     : { doc : 'componentes/telas/secretario/tela/secretario-tela.html', service : secretarioTela },
	'secretario-detalhes' : { doc : 'componentes/telas/secretario/detalhes/secretario-detalhes.html', service : secretarioDetalhes },

	'administrador-form'     : { doc : 'componentes/telas/administrador/form/administrador-form.html', service : administradorForm },
	'administrador-tela'     : { doc : 'componentes/telas/administrador/tela/administrador-tela.html', service : administradorTela },
	'administrador-detalhes' : { doc : 'componentes/telas/administrador/detalhes/administrador-detalhes.html', service : administradorDetalhes },

	'feriado-form' : { doc : 'componentes/telas/feriado/form/feriado-form.html' },
	'feriado-tela' : { doc : 'componentes/telas/feriado/feriado-tela.html', service : feriadoTela },
	
	'periodo-form' : { doc : 'componentes/telas/periodo/form/periodo-form.html' },
	'periodo-tela' : { doc : 'componentes/telas/periodo/periodo-tela.html', service : periodoTela },
	
	'anoletivo-form'     : { doc : 'componentes/telas/anoletivo/form/anoletivo-form.html', service : anoletivoForm },
	'anoletivo-tela'     : { doc : 'componentes/telas/anoletivo/tela/anoletivo-tela.html', service : anoletivoTela },
	'anoletivo-detalhes' : { doc : 'componentes/telas/anoletivo/detalhes/anoletivo-detalhes.html', service : anoletivoDetalhes },
	
	'curso-form'     : { doc : 'componentes/telas/curso/form/curso-form.html', service : cursoForm },
	'curso-tela'     : { doc : 'componentes/telas/curso/tela/curso-tela.html', service : cursoTela },
	'curso-detalhes' : { doc : 'componentes/telas/curso/detalhes/curso-detalhes.html', service : cursoDetalhes },
	
	'serie-form'     : { doc : 'componentes/telas/serie/form/serie-form.html', service : serieForm },
	'serie-tela'     : { doc : 'componentes/telas/serie/tela/serie-tela.html', service : serieTela },
	'serie-detalhes' : { doc : 'componentes/telas/serie/detalhes/serie-detalhes.html', service : serieDetalhes },

	'disciplina-form'     : { doc : 'componentes/telas/disciplina/form/disciplina-form.html', service : disciplinaForm },
	'disciplina-tela'     : { doc : 'componentes/telas/disciplina/tela/disciplina-tela.html', service : disciplinaTela },
	'disciplina-detalhes' : { doc : 'componentes/telas/disciplina/detalhes/disciplina-detalhes.html', service : disciplinaDetalhes },

	'turma-form'     : { doc : 'componentes/telas/turma/form/turma-form.html', service : turmaForm },
	'turma-tela'     : { doc : 'componentes/telas/turma/tela/turma-tela.html', service : turmaTela },
	'turma-detalhes' : { doc : 'componentes/telas/turma/detalhes/turma-detalhes.html', service : turmaDetalhes },
	
	'turma-disciplina-form' : { doc : 'componentes/telas/turma-disciplina/form/turma-disciplina-form.html', service : turmaDisciplinaForm },
	'turma-disciplina-tela' : { doc : 'componentes/telas/turma-disciplina/tela/turma-disciplina-tela.html', service : turmaDisciplinaTela },

	'professor-alocacao-form' : { doc : 'componentes/telas/professor-alocacao/form/professor-alocacao-form.html', service : professorAlocacaoForm },
	'professor-alocacao-tela' : { doc : 'componentes/telas/professor-alocacao/tela/professor-alocacao-tela.html', service : professorAlocacaoTela },
	
	'horario-tela' : { doc : 'componentes/telas/horario/horario-tela.html', service : horarioTela },
	
	'matricula-form' : { doc : 'componentes/telas/matricula/form/matricula-form.html', service : matriculaForm },
	'matricula-tela' : { doc : 'componentes/telas/matricula/tela/matricula-tela.html', service : matriculaTela },
	
	'lista-frequencia' : { doc : 'componentes/telas/frequencia/lista-frequencia.html', service : listaFrequencia },
	
	'planejamento-form' : { doc : 'componentes/telas/planejamento/form/planejamento-form.html', service : planejamentoForm },
	'planejamento-tela' : { doc : 'componentes/telas/planejamento/tela/planejamento-tela.html', service : planejamentoTela },
	'planejamento-detalhes' : { doc : 'componentes/telas/planejamento/detalhes/planejamento-detalhes.html', service : planejamentoDetalhes },

	'avaliacao-tela' : { doc : 'componentes/telas/avaliacao/tela/avaliacao-tela.html', service : avaliacaoTela },
	'avaliacao-detalhes' : { doc : 'componentes/telas/avaliacao/detalhes/avaliacao-detalhes.html', service : avaliacaoDetalhes },
	'agendamento-avaliacao-form' : { doc : 'componentes/telas/avaliacao/agendamento/agendamento-avaliacao-form.html', service : agendamentoAvaliacaoForm },
	'resultado-avaliacao-form' : { doc : 'componentes/telas/avaliacao/resultado/resultado-avaliacao-form.html', service : resultadoAvaliacaoForm },
	
	'aluno-horario' : { doc : 'componentes/telas/aluno-horario/aluno-horario.html', service : alunoHorario },
	'aluno-boletim' : { doc : 'componentes/telas/aluno-boletim/aluno-boletim.html', service : alunoBoletim }
};

sistema.inicializa( componentes );
