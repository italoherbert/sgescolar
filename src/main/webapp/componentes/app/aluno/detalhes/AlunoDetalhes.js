
import {sistema} from '../../../../sistema/Sistema.js';

export default class AlunoDetalhes {			
		
	onCarregado() {		
		sistema.ajax( "GET", "/api/pessoa/get/"+this.params.pessoaId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				
				document.getElementById( "pessoa-nome" ).innerHTML = dados.nome;			
				document.getElementById( "pessoa-telefone" ).innerHTML = dados.telefone;			
				document.getElementById( "pessoa-email" ).innerHTML = dados.email;							
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );
	}
	
	editar() {				
		sistema.carregaPagina( 'pessoa-form', { pessoaId : this.params.pessoaId, op : 'editar', titulo : "Edição de pessoa" } );
	}
			
	paraPessoaTela() {
		sistema.carregaPagina( 'pessoa-tela' );
	}
	
}
export const alunoDetalhes = new AlunoDetalhes(); 