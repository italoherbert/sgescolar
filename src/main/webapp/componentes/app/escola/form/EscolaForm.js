
class EscolaForm {
	
	salvar() {
		const instance = this;
		
		sistema.ajax( 'POST', '/api/escola/registra', {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				nome : document.escola_form.nome.value
			} ),
			sucesso : function( resposta ) {
				instance.limpa();
				sistema.mostraMensagem( 'mensagem-el', 'info', 'Escola cadastrada com êxito.' );
			},
			erro : function( erromsg ) {
				sistema.mostraMensagem( 'mensagem-el', 'erro', erromsg );
			}
			
		} );
		
	}
	
	limpa() {
		document.escola_form.nome.value = "";
		document.escola_form.cnpj.value = "";
	}
	
}