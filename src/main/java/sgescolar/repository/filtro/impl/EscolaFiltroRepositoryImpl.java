package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.model.Escola;
import sgescolar.repository.filtro.EscolaFiltroRepository;
import sgescolar.repository.filtro.exp.field.EscolaNomeFieldOf;

@Repository
public class EscolaFiltroRepositoryImpl implements EscolaFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private EscolaNomeFieldOf escolaNomeFieldOf = new EscolaNomeFieldOf();
	
	@Override
	public List<Escola> filtra(String nomeIni) {
		SimplesFiltroRepository<Escola> simplesRepo = new SimplesFiltroRepository<>( entityManager, Escola.class, escolaNomeFieldOf );
		return simplesRepo.filtra( nomeIni );
	}

}
