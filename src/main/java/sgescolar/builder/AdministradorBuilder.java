package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Administrador;
import sgescolar.model.Instituicao;
import sgescolar.model.request.SaveAdministradorRequest;
import sgescolar.model.response.AdministradorResponse;

@Component
public class AdministradorBuilder {

	@Autowired
	private FuncionarioBuilder funcionarioBuilder;
	
	@Autowired
	private InstituicaoBuilder instituicaoBuilder;
			
	public void carregaAdministrador( Administrador adm, SaveAdministradorRequest request ) {						
		funcionarioBuilder.carregaFuncionario( adm.getFuncionario(), request.getFuncionario() );
	}
	
	public void carregaAdministradorResponse( AdministradorResponse resp, Administrador adm ) {
		resp.setId( adm.getId() );
		
		funcionarioBuilder.carregaFuncionarioResponse( resp.getFuncionario(), adm.getFuncionario() );
		instituicaoBuilder.carregaInstituicaoResponse( resp.getInstituicao(), adm.getInstituicao() ); 
	}
	
	public Administrador novoAdministrador( Instituicao instituicao ) {
		Administrador adm = new Administrador();
		adm.setInstituicao( instituicao );
		adm.setFuncionario( funcionarioBuilder.novoFuncionario() );
		return adm;
	}
	
	public AdministradorResponse novoAdministradorResponse() {
		AdministradorResponse resp = new AdministradorResponse();
		resp.setFuncionario( funcionarioBuilder.novoFuncionarioResponse() );
		resp.setInstituicao( instituicaoBuilder.novoInstituicaoResponse() ); 
		return resp;
	}
	
}
