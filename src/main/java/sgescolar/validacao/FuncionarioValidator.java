package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.EscolaridadeEnumManager;
import sgescolar.enums.FuncionarioFuncaoEnumManager;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveFuncionarioRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class FuncionarioValidator {

	@Autowired
	private EscolaridadeEnumManager escolaridadeEnumManager;
	
	@Autowired
	private FuncionarioFuncaoEnumManager funcionarioFuncaoEnumManager;
	
	@Autowired
	private PessoaValidator pessoaValidator;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveFuncionarioRequest request ) throws ValidacaoException {
		if ( request.getUsuario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_USUARIO_OBRIGATORIOS );
		
		if ( request.getCodigoInep() == null )
			throw new ValidacaoException( ValidacaoErro.CODIGO_INEP_OBRIGATORIO );
		if ( request.getCodigoInep().isBlank() )
			throw new ValidacaoException( ValidacaoErro.CODIGO_INEP_OBRIGATORIO );
		
		if ( !escolaridadeEnumManager.enumValida( request.getEscolaridade() ) )
			throw new ValidacaoException( ValidacaoErro.ESCOLARIDADE_NAO_RECONHECIDA );
		
		if ( !funcionarioFuncaoEnumManager.enumValida( request.getFuncao() ) )
			throw new ValidacaoException( ValidacaoErro.FUNCIONARIO_FUNCAO_NAO_RECONHECIDA );
		
		if ( !validatorUtil.booleanValido( request.getEscolaFunc() ) )
			throw new ValidacaoException( ValidacaoErro.EH_ESCOLA_FUNCIONARIO_VALOR_INVALIDO );
		
		if ( !validatorUtil.intValido( request.getCargaHoraria() ) )
			throw new ValidacaoException( ValidacaoErro.CARGA_HORARIA_INVALIDA );
				
		pessoaValidator.validaSaveRequest( request.getPessoa() );
		usuarioValidator.validaSaveRequest( request.getUsuario() );
	}
	
}
