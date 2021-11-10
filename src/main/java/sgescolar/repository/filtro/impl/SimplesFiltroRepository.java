package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sgescolar.repository.filtro.exp.EntityFieldOf;
import sgescolar.repository.filtro.spec.LikeIgnoreCaseEAcentoStringSpecification;

public class SimplesFiltroRepository<T extends Object> {

	private final EntityManager entityManager;
	private final Class<T> clazz;
	private EntityFieldOf<T, String> fieldOfEntity;
	
	public SimplesFiltroRepository( EntityManager entityManager, Class<T> clazz, EntityFieldOf<T, String> field ) {
		this.entityManager = entityManager;
		this.clazz = clazz;
		this.fieldOfEntity = field;		
	}
	
	public List<T> filtra( String str ) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery( clazz );
		Root<T> root = query.from( clazz );
				
		LikeIgnoreCaseEAcentoStringSpecification<T> likeSpec = new LikeIgnoreCaseEAcentoStringSpecification<>( fieldOfEntity, str );		
		
		Predicate[] predicados = { likeSpec.toPredicate( root, query, cb ) };		
		query.where( predicados ); 
		
		return entityManager.createQuery( query ).getResultList();
	}

}
