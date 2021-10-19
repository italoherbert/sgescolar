
class PessoaDetalhes {		
		
	onCarregado() {		
		sistema.ajax( "GET", "/api/pessoa/get/"+this.props.pessoaId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				
				document.getElementById( "pessoa-nome" ).innerHTML = dados.nome;			
				document.getElementById( "pessoa-telefone" ).innerHTML = dados.telefone;			
				document.getElementById( "pessoa-email" ).innerHTML = dados.email;							
			},
			erro : function( msg ) {
				sistema.mostraMensagem( "mensagem-el", 'erro', msg );	
			}
		} );
	}
	
	editar() {				
		sistema.carregaPagina( 'pessoa-form', { pessoaId : this.props.pessoaId, op : 'editar' } );
	}
			
	paraPessoaTela() {
		sistema.carregaPagina( 'pessoa-tela' );
	}
	
}