
let loginLayout = new LoginLayout();
let loginForm = new LoginForm();

let appLayout = new AppLayout();

let pessoaTela = new PessoaTela();
let pessoaForm = new PessoaForm();

let componentes = {
	'login-layout' : { pagina : 'componentes/login/layout/login-layout.html', jsObj : loginLayout },
	'login-form' : { pagina : 'componentes/login/form/login-form.html', jsObj : loginForm },
	
	'app-layout' : { pagina : 'componentes/app/layout/app-layout.html', jsObj : appLayout },
	'pessoa-tela' : { pagina : 'componentes/app/pessoa/tela/pessoa-tela.html', jsObj : pessoaTela },
	'pessoa-form' : { pagina : 'componentes/app/pessoa/form/pessoa-form.html', jsObj : pessoaForm },
};

let icones = [	
	{ nome : "bt-menu-toggle-left", src : "icons/chevron-double-left.svg" },
	{ nome : "bt-menu-toggle-right", src : "icons/chevron-double-right.svg" }
];

let sistema = new Sistema( componentes, icones );

window.onload = function() {
	sistema.carregaLayout( 'login-layout' );
}
