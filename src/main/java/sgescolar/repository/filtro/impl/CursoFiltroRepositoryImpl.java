package sgescolar.repository.filtro.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.enums.tipos.CursoModalidade;
import sgescolar.model.Curso;
import sgescolar.repository.filtro.CursoFiltroRepository;
import sgescolar.repository.filtro.exp.field.CursoNomeFieldOf;
import sgescolar.repository.filtro.spec.LikeIgnoreCaseEAcentoStringSpecification;

@Repository
public class CursoFiltroRepositoryImpl implements CursoFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private CursoNomeFieldOf cursoNomeFieldOf = new CursoNomeFieldOf();
	
	@Override
	public List<Curso> filtra( Long escolaId, String nomeIni, CursoModalidade modalidade ) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Curso> query = cb.createQuery( Curso.class );
		Root<Curso> root = query.from( Curso.class );
				
		LikeIgnoreCaseEAcentoStringSpecification<Curso> likeSpec = 
				new LikeIgnoreCaseEAcentoStringSpecification<>( cursoNomeFieldOf, nomeIni );
		
		List<Predicate> lista = new ArrayList<>();
		
		lista.add( likeSpec.toPredicate( root, query, cb ) );
		lista.add( cb.equal( root.join("escola").get( "id"), escolaId ) );
		
		if ( modalidade != null )
			lista.add( cb.equal( root.get("modalidade"), modalidade ) );
				
		Predicate[] predicados = new Predicate[ lista.size() ];
		predicados = lista.toArray( predicados );
		
		query.where( predicados ); 
		
		return entityManager.createQuery( query ).getResultList();
	}
	
}
