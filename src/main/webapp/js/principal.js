
let pessoaTela = new PessoaTela();
let pessoaForm = new PessoaForm();

let componentes = {
	 'pessoa-tela' : { pagina : 'pages/pessoa/pessoa-tela.html', jsObj : pessoaTela },
	 'pessoa-form' : { pagina : 'pages/pessoa/pessoa-form.html', jsObj : pessoaForm },
};

let sistema = new Sistema( componentes );

window.onload = function() {
	sistema.carregaPagina( 'pessoa-tela' );
}