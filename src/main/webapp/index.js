
let loginLayout = new LoginLayout();
let loginForm = new LoginForm();

let appLayout = new AppLayout();

let pessoaTela = new PessoaTela();
let pessoaForm = new PessoaForm();
let pessoaDetalhes = new PessoaDetalhes();

let escolaForm = new EscolaForm();
let escolaTela = new EscolaTela();

let componentes = {
	'confirm-modal' : { pagina : 'componentes/ext/confirm-modal.html' },
	
	'login-layout' : { pagina : 'componentes/login/layout/login-layout.html', jsObj : loginLayout },
	'login-form'   : { pagina : 'componentes/login/form/login-form.html', jsObj : loginForm },
	
	'app-layout'   : { pagina : 'componentes/app/layout/app-layout.html', jsObj : appLayout },		
	'navbar-menu'  : { pagina : 'componentes/app/layout/navbar/navbar-menu.html' },

	'admin-menu'       : { pagina : 'componentes/app/layout/menu/admin-menu.html' },
	'secretario-menu'  : { pagina : 'componentes/app/layout/menu/secretario-menu.html' },
	'professor-menu'   : { pagina : 'componentes/app/layout/menu/professor-menu.html' },
	'aluno-menu'       : { pagina : 'componentes/app/layout/menu/aluno-menu.html' },
	
	'pessoa-tela'  : { pagina : 'componentes/app/pessoa/tela/pessoa-tela.html', jsObj : pessoaTela },
	'pessoa-form'  : { pagina : 'componentes/app/pessoa/form/pessoa-form.html', jsObj : pessoaForm },
	'pessoa-detalhes' : { pagina : 'componentes/app/pessoa/detalhes/pessoa-detalhes.html', jsObj : pessoaDetalhes },
	
	'escola-form'  : { pagina : 'componentes/app/escola/form/escola-form.html', jsObj : escolaForm },
	'escola-tela'  : { pagina : 'componentes/app/escola/tela/escola-tela.html', jsObj : escolaTela }
};

let sistema = new Sistema( componentes );

window.onload = function() {
	sistema.carregaLayout( 'login-layout' );
}
