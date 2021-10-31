package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PermissaoGrupoBuilder;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PermissaoGrupoRepository;

@Service
public class PermissaoGrupoService {
	
	@Autowired
	private PermissaoGrupoRepository permissaoGrupoRepository;
	
	@Autowired
	private PermissaoGrupoBuilder permissaoGrupoBuilder;
	
	public void salvaPermissao( Long permissaoGrupoId, SavePermissaoRequest request ) throws ServiceException {			
		Optional<PermissaoGrupo> pgOp = permissaoGrupoRepository.findById( permissaoGrupoId );
		if ( !pgOp.isPresent() )
			throw new ServiceException( ServiceErro.PERMISSAO_GRUPO_NAO_ENCONTRADO );
		
		PermissaoGrupo pg = pgOp.get();
		
		permissaoGrupoBuilder.carregaPermissao( pg, request );		
		permissaoGrupoRepository.save( pg );
	}
	
	public void salvaPermissaoGrupo( Long permissaoGrupoId, SavePermissaoGrupoRequest request ) throws ServiceException {
		Optional<PermissaoGrupo> pgOp = permissaoGrupoRepository.findById( permissaoGrupoId );
		if ( !pgOp.isPresent() )
			throw new ServiceException( ServiceErro.PERMISSAO_GRUPO_NAO_ENCONTRADO );
		
		PermissaoGrupo pg = pgOp.get();
		
		permissaoGrupoBuilder.carregaPermissaoGrupo( pg, request );		
		permissaoGrupoRepository.save( pg );		
	}
	
}
