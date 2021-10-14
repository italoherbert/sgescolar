
let loginLayout = new LoginLayout();
let loginForm = new LoginForm();

let appLayout = new AppLayout();

let pessoaTela = new PessoaTela();
let pessoaForm = new PessoaForm();

let escolaForm = new EscolaForm();
let escolaTela = new EscolaTela();

let componentes = {
	'login-layout' : { pagina : 'componentes/login/layout/login-layout.html', jsObj : loginLayout },
	'login-form'   : { pagina : 'componentes/login/form/login-form.html', jsObj : loginForm },
	
	'app-layout'   : { pagina : 'componentes/app/layout/app-layout.html', jsObj : appLayout },		
	'navbar-menu'  : { pagina : 'componentes/app/layout/navbar/navbar-menu.html', jsObj : null },

	'admin-menu'       : { pagina : 'componentes/app/layout/menu/admin-menu.html', jsObj : null },
	'secretario-menu'  : { pagina : 'componentes/app/layout/menu/secretario-menu.html', jsObj : null },
	'professor-menu'   : { pagina : 'componentes/app/layout/menu/professor-menu.html', jsObj : null },
	'aluno-menu'       : { pagina : 'componentes/app/layout/menu/aluno-menu.html', jsObj : null },
	
	'pessoa-tela'  : { pagina : 'componentes/app/pessoa/tela/pessoa-tela.html', jsObj : pessoaTela },
	'pessoa-form'  : { pagina : 'componentes/app/pessoa/form/pessoa-form.html', jsObj : pessoaForm },
	
	'escola-form'  : { pagina : 'componentes/app/escola/form/escola-form.html', jsObj : escolaForm },
	'escola-tela'  : { pagina : 'componentes/app/escola/tela/escola-tela.html', jsObj : escolaTela }
};

let icones = [	
	
];

let sistema = new Sistema( componentes, icones );

window.onload = function() {
	sistema.carregaLayout( 'login-layout' );
}
