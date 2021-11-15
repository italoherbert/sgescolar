package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.model.UsuarioGrupo;
import sgescolar.repository.filtro.UsuarioGrupoFiltroRepository;
import sgescolar.repository.filtro.exp.field.UsuarioGrupoNomeFieldOf;

@Repository
public class UsuarioGrupoFiltroRepositoryImpl implements UsuarioGrupoFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private UsuarioGrupoNomeFieldOf usuarioGrupoNomeFieldOf = new UsuarioGrupoNomeFieldOf();
	
	@Override
	public List<UsuarioGrupo> filtra( String nomeIni ) {
		SimplesFiltroRepository<UsuarioGrupo> simplesRepo = new SimplesFiltroRepository<>( entityManager, UsuarioGrupo.class, usuarioGrupoNomeFieldOf );
		return simplesRepo.filtra( nomeIni );
	}

}
