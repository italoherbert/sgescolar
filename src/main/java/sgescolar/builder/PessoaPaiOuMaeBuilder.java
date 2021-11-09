package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.PessoaPaiOuMae;
import sgescolar.model.request.SavePessoaPaiOuMaeRequest;
import sgescolar.model.response.PessoaPaiOuMaeResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PessoaPaiOuMaeBuilder {

	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaPessoaPaiOuMae( PessoaPaiOuMae p, SavePessoaPaiOuMaeRequest request ) {
		p.setDesconhecido( conversorUtil.stringParaBoolean( request.getDesconhecido() ) );				
		p.setFalecido( conversorUtil.stringParaBoolean( request.getFalecido() ) );				
		pessoaBuilder.carregaPessoa( p.getPessoa(), request.getPessoa() ); 
	}
	
	public void carregaPessoaPaiOuMaeResponse( PessoaPaiOuMaeResponse resp, PessoaPaiOuMae p ) {
		resp.setId( p.getId() );
		resp.setDesconhecido( conversorUtil.booleanParaString( p.isDesconhecido() ) ); 
		resp.setFalecido( conversorUtil.booleanParaString( p.isFalecido() ) ); 
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), p.getPessoa() );
	}
		
	public PessoaPaiOuMae novoPessoaPaiOuMae() {
		PessoaPaiOuMae p = new PessoaPaiOuMae();
		p.setPessoa( pessoaBuilder.novoPessoa() );
		return p;
	}
	
	public PessoaPaiOuMaeResponse novoPessoaPaiOuMaeResponse() {
		PessoaPaiOuMaeResponse resp = new PessoaPaiOuMaeResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() ); 
		return resp;
	}
	
}
