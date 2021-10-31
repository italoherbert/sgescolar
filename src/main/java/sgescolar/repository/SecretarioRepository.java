package sgescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Secretario;

public interface SecretarioRepository extends JpaRepository<Secretario, Long>{

	@Query( "select s from Secretario s join s.funcionario f join f.pessoa p where lower(p.nome) like lower(?1)" )
	public List<Secretario> filtra( String nomeIni );
	
}
