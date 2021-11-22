package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sgescolar.model.Secretario;
import sgescolar.repository.filtro.SecretarioFiltroRepository;
import sgescolar.repository.filtro.exp.field.SecretarioNomeFieldOf;
import sgescolar.repository.filtro.spec.LikeIgnoreCaseEAcentoStringSpecification;

@Repository
public class SecretarioFiltroRepositoryImpl implements SecretarioFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private SecretarioNomeFieldOf secretarioNomeFieldOf = new SecretarioNomeFieldOf();
	
	@Override
	public List<Secretario> filtra(Long escolaId, String nomeIni) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Secretario> query = cb.createQuery( Secretario.class );
		Root<Secretario> root = query.from( Secretario.class );
				
		LikeIgnoreCaseEAcentoStringSpecification<Secretario> likeSpec = 
				new LikeIgnoreCaseEAcentoStringSpecification<>( secretarioNomeFieldOf, nomeIni );
		
		Predicate likeP = likeSpec.toPredicate( root, query, cb );
		Predicate escolaIDsIguaisP = cb.equal( root.join( "escola" ).get( "id" ), escolaId );
				
		Predicate[] predicados = { escolaIDsIguaisP, likeP };		
		query.where( predicados ); 
		
		return entityManager.createQuery( query ).getResultList();
	}
	
	@Override
	public List<Secretario> filtra(String nomeIni) {
		SimplesFiltroRepository<Secretario> simplesRepo = new SimplesFiltroRepository<>( entityManager, Secretario.class, secretarioNomeFieldOf );
		return simplesRepo.filtra( nomeIni );
	}

}
