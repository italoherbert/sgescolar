package sgescolar.service.dao;

import org.springframework.stereotype.Component;

import sgescolar.model.ProfessorAlocacao;
import sgescolar.msg.ServiceErro;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.ServiceException;

@Component
public class PlanejamentoDAO {

	public void autorizaSeDono( ProfessorAlocacao paloc, TokenInfos tokenInfos ) throws ServiceException {
		Long profUID = paloc.getProfessor().getFuncionario().getUsuario().getId();
		if ( profUID != tokenInfos.getLogadoUID() )
			throw new ServiceException( ServiceErro.SEM_PERMISSAO );
	}
	
}
