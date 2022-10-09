package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Instituicao;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long>  {

	@Query( "select i from Instituicao i where i.cnpj=?1" )
	public Optional<Instituicao> buscaPorCNPJ( String cnpj );
	
	@Query( "select i from Instituicao i "
			+ "where i.cnpj=?1 or lower_unaccent(i.razaoSocial) like lower_unaccent(?2)" )
	public List<Instituicao> filtra( String cnpj, String razaoSocialIni );
	
}
