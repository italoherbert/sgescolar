package sgescolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PlanejamentoBuilder;
import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.repository.PlanejamentoRepository;
import sgescolar.repository.ProfessorAlocacaoRepository;

@Service
public class PlanejamentoService {

	@Autowired
	private PlanejamentoRepository planejamentoRepository;
	
	@Autowired
	private ProfessorAlocacaoRepository professorAlocacaoRepository;
	
	@Autowired
	private PlanejamentoBuilder planejamentoBuilder;
	
	public void registraPlanejamento( Long professorAlocacaoId, SavePlanejamentoRequest request ) throws ServiceException {
		
	}
	
}
