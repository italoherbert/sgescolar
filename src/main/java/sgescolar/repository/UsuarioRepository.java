package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query( "select u from Usuario u where lower_unaccent( u.username ) like lower_unaccent(?1)" )
	public List<Usuario> buscaPorUsernameIni( String usernameIni );
	
	public Optional<Usuario> findByUsername( String username );
		
}
