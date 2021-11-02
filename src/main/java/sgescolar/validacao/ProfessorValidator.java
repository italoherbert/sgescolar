package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraProfessoresRequest;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class ProfessorValidator {

	@Autowired
	private FuncionarioValidator funcionarioValidator;
		
	public void validaSaveRequest( SaveProfessorRequest request ) throws ValidacaoException {
		if ( request.getFuncionario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_FUNCIONARIO_OBRIGATORIOS );
		if ( request.getFuncionario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_FUNCIONARIO_OBRIGATORIOS );
		
		funcionarioValidator.validaSaveRequest( request.getFuncionario() ); 
	}
	
	public void validaFiltroRequest( FiltraProfessoresRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
	}
	
}
