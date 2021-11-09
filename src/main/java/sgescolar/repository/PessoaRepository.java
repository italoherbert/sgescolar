package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query( "select p from Pessoa p where p.cpf=?1 and p.cpf is not null and p.cpf is not empty" )
	public Optional<Pessoa> buscaPorCpf( String cpf );
	
	@Query( "select p from Pessoa p where lower(p.nome)=lower(?1)" )
	public Optional<Pessoa> buscaPorNome( String nome );
	
	@Query( "select p from Pessoa p where lower(p.nome) like lower(?1)" )
	public List<Pessoa> filtraPorNomeIni( String nomeIni );
	
}
