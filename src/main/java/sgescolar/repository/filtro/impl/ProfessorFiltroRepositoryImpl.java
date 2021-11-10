package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.model.Professor;
import sgescolar.repository.filtro.ProfessorFiltroRepository;
import sgescolar.repository.filtro.exp.field.ProfessorNomeFieldOf;

@Repository
public class ProfessorFiltroRepositoryImpl implements ProfessorFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private ProfessorNomeFieldOf professorNomeFieldOf = new ProfessorNomeFieldOf();
	
	@Override
	public List<Professor> filtra(String nomeIni) {
		SimplesFiltroRepository<Professor> simplesRepo = new SimplesFiltroRepository<>( entityManager, Professor.class, professorNomeFieldOf );
		return simplesRepo.filtra( nomeIni );
	}

}
