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
	private PessoaResponsavelBuilder pessoaResponsavelBuilder;
	
	@Autowired
	private UsuarioBuilder usuarioBuilder;
		
	public void carregaAluno( Aluno a, SaveAlunoRequest request ) {				
		pessoaBuilder.carregaPessoa( a.getPessoa(), request.getPessoa() );
		usuarioBuilder.carregaUsuario( a.getUsuario(), request.getUsuario() );
		
		pessoaResponsavelBuilder.carregaPessoaResponsavel( a.getMae(), request.getMae() );		
		pessoaResponsavelBuilder.carregaPessoaResponsavel( a.getPai(), request.getPai() );
		pessoaResponsavelBuilder.carregaPessoaResponsavel( a.getResponsavel(), request.getResponsavel() );
	}
	
	public void carregaAlunoResponse( AlunoResponse resp, Aluno a ) {
		resp.setId( a.getId() ); 
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), a.getPessoa() ); 
		usuarioBuilder.carregaUsuarioResponse( resp.getUsuario(), a.getUsuario() );
		
		pessoaResponsavelBuilder.carregaPessoaResponsavelResponse( resp.getMae(), a.getMae() );
		pessoaResponsavelBuilder.carregaPessoaResponsavelResponse( resp.getPai(), a.getPai() );		
		pessoaResponsavelBuilder.carregaPessoaResponsavelResponse( resp.getResponsavel(), a.getResponsavel() ); 
	}
	
	public Aluno novoAluno() {
		Aluno a = new Aluno();
		a.setPessoa( pessoaBuilder.novoPessoa() );
		a.setUsuario( usuarioBuilder.novoUsuario() );  
		a.setMae( pessoaResponsavelBuilder.novoPessoaResponsavel() );
		a.setPai( pessoaResponsavelBuilder.novoPessoaResponsavel() );
		a.setResponsavel( pessoaResponsavelBuilder.novoPessoaResponsavel() );
		return a;
	}
	
	public AlunoResponse novoAlunoResponse() {
		AlunoResponse resp = new AlunoResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() );
		resp.setUsuario( usuarioBuilder.novoUsuarioResponse() );
		resp.setMae( pessoaResponsavelBuilder.novoPessoaResponsavelResponse() );
		resp.setPai( pessoaResponsavelBuilder.novoPessoaResponsavelResponse() );
		resp.setResponsavel( pessoaResponsavelBuilder.novoPessoaResponsavelResponse() );
		return resp;
	}
	
}
