package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Secretario;
import sgescolar.repository.filtro.SecretarioFiltroRepository;

public interface SecretarioRepository extends JpaRepository<Secretario, Long>, SecretarioFiltroRepository {

	@Query( "select s from Secretario s join s.funcionario f join f.usuario u where u.id=?1" )
	public Optional<Secretario> buscaPorUID( Long usuarioId );
				
}
