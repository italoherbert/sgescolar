package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.Aluno;

public interface AlunoFiltroRepository {

	public List<Aluno> filtra( String nomeIni );
	
}
