package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.Escola;
import sgescolar.model.Secretario;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.util.ConversorUtil;

@Component
public class SecretarioBuilder {

	@Autowired
	private FuncionarioBuilder funcionarioBuilder;
	
	@Autowired
	private ConversorUtil numeroUtil;
		
	public void carregaSecretario( Secretario s, SaveSecretarioRequest request ) {				
		request.getFuncionario().getUsuario().setPerfil( UsuarioPerfil.SECRETARIO.name() ); 
		
		funcionarioBuilder.carregaFuncionario( s.getFuncionario(), request.getFuncionario() );		
	}
	
	public void carregaSecretarioResponse( SecretarioResponse resp, Secretario s ) {
		Escola e = s.getEscola();
		
		resp.setId( s.getId() );
		resp.setEscolaId( numeroUtil.longParaString( e.getId() ) );
		resp.setEscolaNome( e.getNome() );
		
		funcionarioBuilder.carregaFuncionarioResponse( resp.getFuncionario(), s.getFuncionario() ); 
	}
	
	public Secretario novoSecretario( Escola escola, UsuarioGrupo grupo ) {
		Secretario s = new Secretario();
		s.setEscola( escola );
		s.setFuncionario( funcionarioBuilder.novoFuncionario( grupo ) );
		return s;
	}
	
	public SecretarioResponse novoSecretarioResponse() {
		SecretarioResponse resp = new SecretarioResponse();
		resp.setFuncionario( funcionarioBuilder.novoFuncionarioResponse() );
		return resp;
	}
	
}
