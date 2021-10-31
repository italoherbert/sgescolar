package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.PessoaMaeOuPai;
import sgescolar.model.request.SavePessoaMaeOuPaiRequest;
import sgescolar.model.response.PessoaMaeOuPaiResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PessoaMaeOuPaiBuilder {

	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaPessoaMaeOuPai( PessoaMaeOuPai p, SavePessoaMaeOuPaiRequest request ) {
		p.setFalecido( conversorUtil.stringParaBoolean( request.getFalecido() ) );				
		pessoaBuilder.carregaPessoa( p.getPessoa(), request.getPessoa() ); 
	}
	
	public void carregaPessoaMaeOuPaiResponse( PessoaMaeOuPaiResponse resp, PessoaMaeOuPai p ) {
		resp.setId( p.getId() );
		resp.setFalecido( conversorUtil.booleanParaString( p.isFalecido() ) ); 
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), p.getPessoa() );
	}
	
	public PessoaMaeOuPai novoPessoaMaeOuPai() {
		PessoaMaeOuPai p = new PessoaMaeOuPai();
		p.setPessoa( pessoaBuilder.novoPessoa() );
		return p;
	}
	
	public PessoaMaeOuPaiResponse novoPessoaMaeOuPaiResponse() {
		PessoaMaeOuPaiResponse resp = new PessoaMaeOuPaiResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() ); 
		return resp;
	}
	
}
