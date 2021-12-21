package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.PessoaResponsavel;
import sgescolar.model.request.SavePessoaResponsavelRequest;
import sgescolar.model.response.PessoaResponsavelResponse;

@Component
public class PessoaResponsavelBuilder {

	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaPessoaResponsavel( PessoaResponsavel p, SavePessoaResponsavelRequest request ) {
		p.setRegistrado( conversorUtil.stringParaBoolean( request.getRegistrar() ) );				
		pessoaBuilder.carregaPessoa( p.getPessoa(), request.getPessoa() ); 
	}
	
	public void carregaPessoaResponsavelResponse( PessoaResponsavelResponse resp, PessoaResponsavel p ) {
		resp.setId( p.getId() );
		resp.setRegistrado( conversorUtil.booleanParaString( p.isRegistrado() ) ); 
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), p.getPessoa() );
	}
		
	public PessoaResponsavel novoPessoaResponsavel() {
		PessoaResponsavel p = new PessoaResponsavel();
		p.setPessoa( pessoaBuilder.novoPessoa() );
		return p;
	}
	
	public PessoaResponsavelResponse novoPessoaResponsavelResponse() {
		PessoaResponsavelResponse resp = new PessoaResponsavelResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() ); 
		return resp;
	}
	
}

