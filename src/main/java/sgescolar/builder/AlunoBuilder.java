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
	private PessoaMaeOuPaiBuilder pessoaMaeOuPaiBuilder;
	
	private UsuarioBuilder usuarioBuilder;
		
	public void carregaAluno( Aluno a, SaveAlunoRequest request ) {		
		pessoaBuilder.carregaPessoa( a.getPessoa(), request.getPessoa() );
		usuarioBuilder.carregaUsuario( a.getUsuario(), request.getUsuario() );
		
		if ( request.getMae() != null )
			pessoaMaeOuPaiBuilder.carregaPessoaMaeOuPai( a.getMae(), request.getMae() );
		if ( request.getPai() != null )
			pessoaMaeOuPaiBuilder.carregaPessoaMaeOuPai( a.getPai(), request.getPai() );		
	}
	
	public void carregaAlunoResponse( AlunoResponse resp, Aluno a ) {
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), a.getPessoa() ); 
		usuarioBuilder.carregaUsuarioResponse( resp.getUsuario(), a.getUsuario() );
		
		resp.setPai( null );
		resp.setMae( null );
		
		if ( a.getMae() != null )
			pessoaMaeOuPaiBuilder.carregaPessoaMaeOuPaiResponse( resp.getMae(), a.getMae() );		
		if ( a.getPai() != null )
			pessoaMaeOuPaiBuilder.carregaPessoaMaeOuPaiResponse( resp.getPai(), a.getPai() ); 
	}
	
	public Aluno novoAluno() {
		Aluno a = new Aluno();
		a.setPessoa( pessoaBuilder.novoPessoa() );
		a.setUsuario( usuarioBuilder.novoUsuario() ); 
		a.setMae( pessoaMaeOuPaiBuilder.novoPessoaMaeOuPai() );
		a.setPai( pessoaMaeOuPaiBuilder.novoPessoaMaeOuPai() );
		return a;
	}
	
	public AlunoResponse novoAlunoResponse() {
		AlunoResponse resp = new AlunoResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() );
		resp.setUsuario( usuarioBuilder.novoUsuarioResponse() );
		resp.setMae( pessoaMaeOuPaiBuilder.novoPessoaMaeOuPaiResponse() );
		resp.setPai( pessoaMaeOuPaiBuilder.novoPessoaMaeOuPaiResponse() );
		return resp;
	}
	
}
