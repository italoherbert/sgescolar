package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Escola;

public interface EscolaRepository extends JpaRepository<Escola, Long> {

	@Query( "select e from Escola e where lower(e.nome)=lower(?1)" )
	public Optional<Escola> buscaPorNome( String nome );
	
	@Query( "select e from Escola e where lower(e.nome) like lower(?1)" )
	public List<Escola> filtraPorNomeIni( String nomeIni );
	
}
