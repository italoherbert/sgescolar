
let loginLayout = new LoginLayout();
let loginForm = new LoginForm();

let appLayout = new AppLayout();

let alunoForm = new AlunoForm();
let alunoTela = new AlunoTela();

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
	
	'endereco-form-content'  : { pagina : 'componentes/app/endereco/endereco-form-content.html' },
	'contatoinfo-form-content'  : { pagina : 'componentes/app/contatoinfo/contatoinfo-form-content.html' },
	'usuario-form-content'  : { pagina : 'componentes/app/usuario/usuario-form-content.html' },
	'pessoa-form-content'  : { pagina : 'componentes/app/pessoa/pessoa-form-content.html' },
	
	'pai-ou-mae-form-modal'  : { pagina : 'componentes/app/aluno/form/pai-ou-mae-form-modal.html' },
	'pai-ou-mae-form-content'  : { pagina : 'componentes/app/aluno/form/pai-ou-mae-form-content.html' },	
	
	'filiacao-aluno-form'  : { pagina : 'componentes/app/aluno/form/filiacao-aluno-form.html' },	
	'aluno-form'  : { pagina : 'componentes/app/aluno/form/aluno-form.html', jsObj : alunoForm },
	'aluno-tela'  : { pagina : 'componentes/app/aluno/tela/aluno-tela.html', jsObj : alunoTela }
};

let sistema = new Sistema( componentes );
let wsExternos = new WSExternos();
let conversor = new Conversor();

window.onload = function() {
	sistema.carregaLayout( 'login-layout' );
}
