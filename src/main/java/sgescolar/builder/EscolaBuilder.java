package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Escola;
import sgescolar.model.Instituicao;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.EscolaResponse;

@Component
public class EscolaBuilder {

	@Autowired
	private EnderecoBuilder enderecoBuilder;
	
	@Autowired
	private ContatoInfoBuilder contatoInfoBuilder;
	
	@Autowired
	private InstituicaoBuilder instituicaoBuilder;
	
	public void carregaEscola( Escola e, SaveEscolaRequest request ) {
		e.setNome( request.getNome() );
		
		enderecoBuilder.carregaEndereco( e.getEndereco(), request.getEndereco() );
		contatoInfoBuilder.carregaContatoInfo( e.getContatoInfo(), request.getContatoInfo() );
	}
	
	public void carregaEscolaResponse( EscolaResponse resp, Escola e ) {
		resp.setId( e.getId() );
		resp.setNome( e.getNome() );
		
		enderecoBuilder.carregaEnderecoResponse( resp.getEndereco(), e.getEndereco() );
		contatoInfoBuilder.carregaContatoInfoResponse( resp.getContatoInfo(), e.getContatoInfo() );
		instituicaoBuilder.carregaInstituicaoResponse( resp.getInstituicao(), e.getInstituicao() );
	}
	
	public Escola novoEscola( Instituicao inst ) {
		Escola e = new Escola();
		e.setEndereco( enderecoBuilder.novoEndereco() );
		e.setContatoInfo( contatoInfoBuilder.novoContatoInfo() );
		e.setInstituicao( inst );
		return e;
	}
	
	public EscolaResponse novoEscolaResponse() {
		EscolaResponse resp = new EscolaResponse();
		resp.setEndereco( enderecoBuilder.novoEnderecoResponse() );
		resp.setContatoInfo( contatoInfoBuilder.novoContatoInfoResponse() );
		resp.setInstituicao( instituicaoBuilder.novoInstituicaoResponse() ); 
		return resp;
	}
	
}

