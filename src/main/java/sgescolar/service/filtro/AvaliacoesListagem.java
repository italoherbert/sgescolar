package sgescolar.service.filtro;

import java.util.List;

import sgescolar.model.Avaliacao;
import sgescolar.repository.AvaliacaoRepository;

public interface AvaliacoesListagem {

	public List<Avaliacao> lista( AvaliacaoRepository repository, Long turmaDisciplinaId );
	
}
