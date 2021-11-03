
class AlunoTela {

	onCarregado() {						
		this.filtrar();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'aluno-detalhes', { alunoId : id } );																	
	}
	
	filtrar() {						
		sistema.ajax( "POST", "/api/aluno/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.aluno_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let html = "";
				for( let i = 0; i < dados.length; i++ ) {
					html += "<tr>" 
						+ "<td>" + dados[ i ].id + "</td>" 					
						+ "<td>" + dados[ i ].nome + "</td>" 
						+ "<td>" + dados[ i ].telefone + "</td>" 
						+ "<td>" + dados[ i ].email + "</td>"
						+ "<td><a href=\"#\" onclick=\"alunoTela.detalhes( " + dados[ i ].id + " )\">detalhes</a></td>" 	 
						+ "</tr>";
				}
				document.getElementById( "tbody-alunos-el" ).innerHTML = html;			
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "mensagem-el", msg );	
			}
		} );	
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'aluno-form', { titulo : "Registro de aluno" } )
	}

}