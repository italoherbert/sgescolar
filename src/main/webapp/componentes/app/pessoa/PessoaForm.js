
class PessoaForm {
	
	onCarregado() {
		sistema.carregaComponente( 'endereco-form-content', 'aluno_endereco_form_el', {
			prefixo : this.params.prefixo,										
		} );
		sistema.carregaComponente( 'contatoinfo-form-content', 'aluno_contatoinfo_form_el', {
			prefixo : "aluno_"
		} );
	}
	
}