package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.PessoaPaiOuMae;

public interface PessoaPaiOuMaeRepository extends JpaRepository<PessoaPaiOuMae, Long> {

	@Query( "select ppm from PessoaPaiOuMae ppm join ppm.pessoa p where ppm.desconhecido=false and p.cpf=?1")
	public Optional<PessoaPaiOuMae> buscaPorCpf( String cpf );
		
}