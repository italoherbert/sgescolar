package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.PessoaResponsavel;

public interface PessoaResponsavelRepository extends JpaRepository<PessoaResponsavel, Long> {

	@Query( "select pr from PessoaResponsavel pr join pr.pessoa p where p.cpf=?1 and p.cpf is not null and p.cpf is not empty")
	public Optional<PessoaResponsavel> buscaPorCpf( String cpf );
		
}
