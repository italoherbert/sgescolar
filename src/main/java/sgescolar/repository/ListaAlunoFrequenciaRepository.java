package sgescolar.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.enums.tipos.Turno;
import sgescolar.model.ListaAlunoFrequencia;

public interface ListaAlunoFrequenciaRepository extends JpaRepository<ListaAlunoFrequencia, Long> {

	@Query( "select lst from ListaAlunoFrequencia lst join lst.turma t where t.id=?1 and lst.dataDia=?2 and lst.turno=?3 and lst.numeroAula=?4")
	public Optional<ListaAlunoFrequencia> busca( Long turmaId, Date dataDia, Turno turno, int numeroAula );
	
}
