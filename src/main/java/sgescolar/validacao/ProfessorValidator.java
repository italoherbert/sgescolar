package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
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
		
		String perfil = request.getFuncionario().getUsuario().getPerfil();
		if ( !perfil.equalsIgnoreCase( UsuarioPerfil.PROFESSOR.name() ) )
			throw new ValidacaoException( ValidacaoErro.SEM_PERMISSAO_PARA_REG_POR_PERFIL );
	}
	
	public void validaFiltroRequest( FiltraProfessoresRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
	}
	
}
