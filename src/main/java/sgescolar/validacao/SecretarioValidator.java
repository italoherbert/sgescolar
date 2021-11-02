package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraSecretariosRequest;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class SecretarioValidator {

	@Autowired
	private FuncionarioValidator funcionarioValidator;
						
	public void validaSaveRequest( SaveSecretarioRequest request ) throws ValidacaoException {		
		if ( request.getFuncionario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_FUNCIONARIO_OBRIGATORIOS );
		if ( request.getFuncionario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_FUNCIONARIO_OBRIGATORIOS );
		
		funcionarioValidator.validaSaveRequest( request.getFuncionario() ); 
	}
	
	public void validaFiltroRequest( FiltraSecretariosRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
	}
	
}
