package sgescolar.repository.filtro.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import sgescolar.model.Feriado;
import sgescolar.repository.filtro.FeriadoFiltroRepository;
import sgescolar.repository.filtro.exp.field.FeriadoAnoLetivoIDFieldOf;
import sgescolar.repository.filtro.exp.field.FeriadoDescricaoFieldOf;
import sgescolar.repository.filtro.spec.LikeIgnoreCaseEAcentoStringSpecification;

public class FeriadoFiltroRepositoryImpl implements FeriadoFiltroRepository {

	@Autowired
	private EntityManager entityManager;
	
	private FeriadoDescricaoFieldOf feriadoDescricaoFieldOf = new FeriadoDescricaoFieldOf();
	private FeriadoAnoLetivoIDFieldOf feriadoAnoLetivoIDIDFieldOf = new FeriadoAnoLetivoIDFieldOf();
	
	@Override
	public List<Feriado> filtra(Long anoLetivoId, String descricaoIni) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Feriado> query = cb.createQuery( Feriado.class );
		Root<Feriado> root = query.from( Feriado.class );
				
		LikeIgnoreCaseEAcentoStringSpecification<Feriado> likeSpec = 
				new LikeIgnoreCaseEAcentoStringSpecification<>( feriadoDescricaoFieldOf, descricaoIni );
		
		Predicate likeP = likeSpec.toPredicate( root, query, cb );
		Predicate anoLetivoIDsIguaisP = cb.equal( feriadoAnoLetivoIDIDFieldOf.exp( root ), anoLetivoId );
				
		Predicate[] predicados = { anoLetivoIDsIguaisP, likeP };		
		query.where( predicados ); 
		
		return entityManager.createQuery( query ).getResultList();
	}
	
}