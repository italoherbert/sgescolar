package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.exception.BooleanFormatoException;
import sgescolar.exception.CargaHorariaFormatoException;
import sgescolar.exception.DataNascFormatoException;
import sgescolar.exception.EscolaFuncFormatoException;
import sgescolar.exception.EscolaridadeFormatoException;
import sgescolar.exception.EstadoCivilNaoReconhecidoException;
import sgescolar.exception.InteiroFormatoException;
import sgescolar.exception.NacionalidadeNaoReconhecidoException;
import sgescolar.exception.RacaNaoReconhecidoException;
import sgescolar.exception.ReligiaoNaoReconhecidoException;
import sgescolar.exception.SexoNaoReconhecidoException;
import sgescolar.model.Escola;
import sgescolar.model.Funcionario;
import sgescolar.model.request.SaveFuncionarioRequest;
import sgescolar.model.response.FuncionarioResponse;
import sgescolar.util.ConversorUtil;
import sgescolar.util.enums.EscolaridadeEnumConversor;

@Component
public class FuncionarioBuilder {

	@Autowired
	private UsuarioBuilder usuarioBuilder;
	
	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private EscolaridadeEnumConversor escolaridadeEnumConversor;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaFuncionario( Funcionario f, SaveFuncionarioRequest req ) 
			throws EscolaridadeFormatoException, 
				EscolaFuncFormatoException, 
				CargaHorariaFormatoException, 
				DataNascFormatoException, 
				SexoNaoReconhecidoException, 
				NacionalidadeNaoReconhecidoException, 
				EstadoCivilNaoReconhecidoException, 
				RacaNaoReconhecidoException, 
				ReligiaoNaoReconhecidoException {		
		
		f.setCodigoInep( req.getCargaHoraria() );
		f.setEscolaridade( escolaridadeEnumConversor.getEnum( req.getEscolaridade() ) );

		try {
			f.setEscolaFunc( conversorUtil.stringParaBoolean( req.getEscolaFunc() ) );
		} catch (BooleanFormatoException e) {
			throw new EscolaFuncFormatoException();
		}
		
		try {
			f.setCargaHoraria( conversorUtil.stringParaInteiro( req.getCargaHoraria() ) );
		} catch (InteiroFormatoException e) {
			throw new CargaHorariaFormatoException();
		}
		
		usuarioBuilder.carregaUsuario( f.getUsuario(), req.getUsuario() ); 
		pessoaBuilder.carregaPessoa( f.getPessoa(), req.getPessoa() ); 
	}
	
	public void carregaFuncionarioResponse( FuncionarioResponse resp, Funcionario f ) {
		resp.setId( f.getId() );
		resp.setEscolaId( f.getEscola().getId() );
		resp.setCodigoInep( f.getCodigoInep() );
		resp.setEscolaFunc( conversorUtil.booleanParaString( f.isEscolaFunc() ) );
		resp.setEscolaridade( escolaridadeEnumConversor.getString( f.getEscolaridade() ) );
		resp.setCargaHoraria( conversorUtil.inteiroParaString( f.getCargaHoraria() ) ); 
	}	
	
	public Funcionario novoFuncionario( Escola escola ) {
		Funcionario f = new Funcionario();
		f.setUsuario( usuarioBuilder.novoUsuario() );
		f.setPessoa( pessoaBuilder.novoPessoa() );
		f.setEscola( escola ); 
		return f;
	}
	
	public FuncionarioResponse novoFuncionarioResponse() {
		FuncionarioResponse resp = new FuncionarioResponse();
		resp.setUsuario( usuarioBuilder.novoUsuarioResponse() ); 
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() ); 
		return resp;
	}
	
}
