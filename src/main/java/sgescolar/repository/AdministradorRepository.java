package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

	@Query( "select adm from Administrador adm join adm.funcionario f join f.usuario u where u.username=?1")
	public Optional<Administrador> buscaPorUsername( String username );
	
	@Query( "select adm from Administrador adm join adm.funcionario f join f.usuario u where u.id=?1" )
	public Optional<Administrador> buscaPorUID( Long logadoUID );
	
	@Query( "select adm from Administrador adm join adm.funcionario f join f.usuario u where lower_unaccent(u.username) like lower_unaccent(?1)")
	public List<Administrador> filtra( String usernameIni );
	
}
