package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Aluno;
import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.response.AlunoResponse;

@Component
public class AlunoBuilder {

	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private PessoaPaiOuMaeBuilder pessoaPaiOuMaeBuilder;
	
	private UsuarioBuilder usuarioBuilder;
		
	public void carregaAluno( Aluno a, SaveAlunoRequest request ) {		
		pessoaBuilder.carregaPessoa( a.getPessoa(), request.getPessoa() );
		usuarioBuilder.carregaUsuario( a.getUsuario(), request.getUsuario() );
		
		if ( request.getMae() != null )
			pessoaPaiOuMaeBuilder.carregaPessoaPaiOuMae( a.getMae(), request.getMae() );
		if ( request.getPai() != null )
			pessoaPaiOuMaeBuilder.carregaPessoaPaiOuMae( a.getPai(), request.getPai() );		
	}
	
	public void carregaAlunoResponse( AlunoResponse resp, Aluno a ) {
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), a.getPessoa() ); 
		usuarioBuilder.carregaUsuarioResponse( resp.getUsuario(), a.getUsuario() );
		
		pessoaPaiOuMaeBuilder.carregaPessoaPaiOuMaeResponse( resp.getMae(), a.getMae() );
		pessoaPaiOuMaeBuilder.carregaPessoaPaiOuMaeResponse( resp.getPai(), a.getPai() );		
	}
	
	public Aluno novoAluno() {
		Aluno a = new Aluno();
		a.setPessoa( pessoaBuilder.novoPessoa() );
		a.setUsuario( usuarioBuilder.novoUsuario() ); 
		a.setMae( pessoaPaiOuMaeBuilder.novoPessoaPaiOuMae() );
		a.setPai( pessoaPaiOuMaeBuilder.novoPessoaPaiOuMae() );
		return a;
	}
	
	public AlunoResponse novoAlunoResponse() {
		AlunoResponse resp = new AlunoResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() );
		resp.setUsuario( usuarioBuilder.novoUsuarioResponse() );
		resp.setMae( pessoaPaiOuMaeBuilder.novoPessoaPaiOuMaeResponse() );
		resp.setPai( pessoaPaiOuMaeBuilder.novoPessoaPaiOuMaeResponse() );
		return resp;
	}
	
}
