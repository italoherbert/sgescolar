package sgescolar.service.filtro.avaliacao;

import java.util.List;

import sgescolar.model.Avaliacao;
import sgescolar.repository.AvaliacaoRepository;
import sgescolar.service.filtro.AvaliacoesListagem;

public class TodasAvaliacoesListagem implements AvaliacoesListagem {

	@Override
	public List<Avaliacao> lista(AvaliacaoRepository repository, Long turmaDisciplinaId) {
		return repository.listaPorTurmaDisciplina( turmaDisciplinaId ); 
	}

}
