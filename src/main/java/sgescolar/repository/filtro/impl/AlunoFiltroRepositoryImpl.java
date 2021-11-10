package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.model.Aluno;
import sgescolar.repository.filtro.AlunoFiltroRepository;
import sgescolar.repository.filtro.exp.field.AlunoNomeFieldOf;

@Repository
public class AlunoFiltroRepositoryImpl implements AlunoFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private AlunoNomeFieldOf alunoNomeFieldOf = new AlunoNomeFieldOf();
		
	public List<Aluno> filtra( String nomeIni ) {
		SimplesFiltroRepository<Aluno> fr = new SimplesFiltroRepository<>( entityManager, Aluno.class, alunoNomeFieldOf );		
		return fr.filtra( nomeIni );		
	}		
	
}
