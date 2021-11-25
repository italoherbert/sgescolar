package sgescolar.builder.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.builder.FuncionarioBuilder;
import sgescolar.model.Administrador;
import sgescolar.model.request.SaveAdministradorRequest;
import sgescolar.model.response.AdministradorResponse;

@Component
public class AdministradorBuilder {

	@Autowired
	private FuncionarioBuilder funcionarioBuilder;
			
	public void carregaAdministrador( Administrador adm, SaveAdministradorRequest request ) {						
		funcionarioBuilder.carregaFuncionario( adm.getFuncionario(), request.getFuncionario() );		
	}
	
	public void carregaAdministradorResponse( AdministradorResponse resp, Administrador adm ) {
		resp.setId( adm.getId() );
		
		funcionarioBuilder.carregaFuncionarioResponse( resp.getFuncionario(), adm.getFuncionario() ); 
	}
	
	public Administrador novoAdministrador() {
		Administrador adm = new Administrador();
		adm.setFuncionario( funcionarioBuilder.novoFuncionario() );
		return adm;
	}
	
	public AdministradorResponse novoAdministradorResponse() {
		AdministradorResponse resp = new AdministradorResponse();
		resp.setFuncionario( funcionarioBuilder.novoFuncionarioResponse() );
		return resp;
	}
	
}
