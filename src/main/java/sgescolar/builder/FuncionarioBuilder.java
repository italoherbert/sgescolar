package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.EscolaridadeEnumManager;
import sgescolar.model.Funcionario;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.SaveFuncionarioRequest;
import sgescolar.model.response.FuncionarioResponse;
import sgescolar.util.ConversorUtil;

@Component
public class FuncionarioBuilder {

	@Autowired
	private UsuarioBuilder usuarioBuilder;
	
	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private EscolaridadeEnumManager escolaridadeEnumManager;
		
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaFuncionario( Funcionario f, SaveFuncionarioRequest req ) {				
		f.setCodigoInep( req.getCodigoInep() );
		f.setEscolaridade( escolaridadeEnumManager.getEnum( req.getEscolaridade() ) );
		f.setEscolaFunc( conversorUtil.stringParaBoolean( req.getEscolaFunc() ) );
		f.setCargaHoraria( conversorUtil.stringParaInteiro( req.getCargaHoraria() ) );		
		
		usuarioBuilder.carregaUsuario( f.getUsuario(), req.getUsuario() ); 
		pessoaBuilder.carregaPessoa( f.getPessoa(), req.getPessoa() ); 
	}
	
	public void carregaFuncionarioResponse( FuncionarioResponse resp, Funcionario f ) {
		resp.setId( f.getId() );
		resp.setCodigoInep( f.getCodigoInep() );
		resp.setEscolaFunc( conversorUtil.booleanParaString( f.isEscolaFunc() ) );
		resp.setEscolaridade( escolaridadeEnumManager.getString( f.getEscolaridade() ) );
		resp.setCargaHoraria( conversorUtil.inteiroParaString( f.getCargaHoraria() ) ); 
		
		usuarioBuilder.carregaUsuarioResponse( resp.getUsuario(), f.getUsuario() );
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), f.getPessoa() );
	}	
	
	public Funcionario novoFuncionario( UsuarioGrupo grupo ) {
		Funcionario f = new Funcionario();
		f.setUsuario( usuarioBuilder.novoUsuario( grupo ) );
		f.setPessoa( pessoaBuilder.novoPessoa() );
		return f;
	}
	
	public FuncionarioResponse novoFuncionarioResponse() {
		FuncionarioResponse resp = new FuncionarioResponse();
		resp.setUsuario( usuarioBuilder.novoUsuarioResponse() ); 
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() ); 
		return resp;
	}
	
}
