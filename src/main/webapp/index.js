
let loginLayout = new LoginLayout();
let loginForm = new LoginForm();

let appLayout = new AppLayout();

let alunoForm = new AlunoForm();

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
	
	'endereco-form'  : { pagina : 'componentes/app/endereco/endereco-form.html' },
	'contatoinfo-form'  : { pagina : 'componentes/app/contatoinfo/contatoinfo-form.html' },
	'pessoa-form'  : { pagina : 'componentes/app/pessoa/pessoa-form.html' },
	'pessoa-form-modal'  : { pagina : 'componentes/app/pessoa/pessoa-form-modal.html' },
	
	'aluno-form'  : { pagina : 'componentes/app/aluno/form/aluno-form.html', jsObj : alunoForm },
};

let sistema = new Sistema( componentes );
let conversor = new Conversor();

window.onload = function() {
	sistema.carregaLayout( 'login-layout' );
}
