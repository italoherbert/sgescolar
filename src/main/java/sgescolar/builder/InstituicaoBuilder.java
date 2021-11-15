package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Instituicao;
import sgescolar.model.request.SaveInstituicaoRequest;
import sgescolar.model.response.InstituicaoResponse;

@Component
public class InstituicaoBuilder {

	@Autowired
	private EnderecoBuilder enderecoBuilder;
	
	@Autowired
	private ContatoInfoBuilder contatoInfoBuilder;
	
	public void carregaInstituicao( Instituicao ins, SaveInstituicaoRequest request ) {
		ins.setCnpj( request.getCnpj() );
		ins.setRazaoSocial( request.getRazaoSocial() ); 
		
		enderecoBuilder.carregaEndereco( ins.getEndereco(), request.getEndereco() );
		contatoInfoBuilder.carregaContatoInfo( ins.getContatoInfo(), request.getContatoInfo() );
	}
	
	public void carregaInstituicaoResponse( InstituicaoResponse resp, Instituicao ins ) {
		resp.setId( ins.getId() );
		resp.setCnpj( ins.getCnpj() );
		resp.setRazaoSocial( ins.getRazaoSocial() );		
		
		enderecoBuilder.carregaEnderecoResponse( resp.getEndereco(), ins.getEndereco() );
		contatoInfoBuilder.carregaContatoInfoResponse( resp.getContatoInfo(), ins.getContatoInfo() );
	}
	
	public Instituicao novoInstituicao() {
		Instituicao ins = new Instituicao();
		ins.setEndereco( enderecoBuilder.novoEndereco() );
		ins.setContatoInfo( contatoInfoBuilder.novoContatoInfo() );
		return ins;
	}
	
	public InstituicaoResponse novoInstituicaoResponse() {
		InstituicaoResponse resp = new InstituicaoResponse();
		resp.setEndereco( enderecoBuilder.novoEnderecoResponse() );
		resp.setContatoInfo( contatoInfoBuilder.novoContatoInfoResponse() );
		return resp;
	}
	
}