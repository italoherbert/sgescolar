package sgescolar.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.msg.ServiceErro;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.ServiceException;

@Component
public class PlanejamentoDAO {

	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	public void autorizaSeDono( ProfessorAlocacao paloc, TokenInfos tokenInfos ) throws ServiceException {
		Long profUID = paloc.getProfessor().getFuncionario().getUsuario().getId();		
		if ( profUID != tokenInfos.getLogadoUID() ) {
			UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tokenInfos.getPerfil() );
			if ( perfil.getPeso() < UsuarioPerfil.SECRETARIO.getPeso() )
				throw new ServiceException( ServiceErro.SEM_PERMISSAO );
		}
	}
	
}
