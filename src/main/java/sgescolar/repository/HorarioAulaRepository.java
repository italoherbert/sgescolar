package sgescolar.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.HorarioAula;

public interface HorarioAulaRepository extends JpaRepository<HorarioAula, Long> {
	
	@Query( "select a from HorarioAula a join a.listaFrequencia lf where lf.dataDia=?1 and a.numeroAula=?2" )
	public Optional<HorarioAula> buscaAula( Date dataDia, int numero );		
	
}