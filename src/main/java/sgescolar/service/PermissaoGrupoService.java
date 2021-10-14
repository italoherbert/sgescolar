package sgescolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PermissaoGrupoBuilder;
import sgescolar.exception.PermissaoEscritaException;
import sgescolar.exception.PermissaoGrupoNaoEncontradoException;
import sgescolar.exception.PermissaoLeituraException;
import sgescolar.exception.PermissaoRemocaoException;
import sgescolar.exception.PermissaoTipoInvalidoException;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.repository.PermissaoGrupoRepository;

@Service
public class PermissaoGrupoService {
	
	@Autowired
	private PermissaoGrupoRepository permissaoGrupoRepository;
	
	@Autowired
	private PermissaoGrupoBuilder permissaoGrupoBuilder;
	
	public void salvaPermissao( Long permissaoGrupoId, SavePermissaoRequest request )
			throws PermissaoGrupoNaoEncontradoException, 
				PermissaoLeituraException, 
				PermissaoEscritaException,
				PermissaoRemocaoException,
				PermissaoTipoInvalidoException {	
		
		PermissaoGrupo pg = permissaoGrupoRepository.findById( permissaoGrupoId ).orElseThrow( PermissaoGrupoNaoEncontradoException::new );
		permissaoGrupoBuilder.carregaPermissao( pg, request );
		
		permissaoGrupoRepository.save( pg );
	}
	
	public void salvaPermissaoGrupo( Long permissaoGrupoId, SavePermissaoGrupoRequest request ) 
			throws PermissaoGrupoNaoEncontradoException, 
				PermissaoLeituraException, 
				PermissaoEscritaException,
				PermissaoRemocaoException {
		
		PermissaoGrupo pg = permissaoGrupoRepository.findById( permissaoGrupoId ).orElseThrow( PermissaoGrupoNaoEncontradoException::new );
		permissaoGrupoBuilder.carregaPermissaoGrupo( pg, request );
		
		permissaoGrupoRepository.save( pg );		
	}
	
}
