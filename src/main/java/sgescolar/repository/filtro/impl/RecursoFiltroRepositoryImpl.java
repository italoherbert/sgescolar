package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.model.Recurso;
import sgescolar.repository.filtro.RecursoFiltroRepository;
import sgescolar.repository.filtro.exp.field.RecursoNomeFieldOf;

@Repository
public class RecursoFiltroRepositoryImpl implements RecursoFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private RecursoNomeFieldOf recursoNomeFieldOf = new RecursoNomeFieldOf();
	
	@Override
	public List<Recurso> filtra( String nomeIni ) {
		SimplesFiltroRepository<Recurso> simplesRepo = new SimplesFiltroRepository<>( entityManager, Recurso.class, recursoNomeFieldOf );
		return simplesRepo.filtra( nomeIni );
	}

}
