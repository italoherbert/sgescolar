package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AvaliacaoExternaBuilder;
import sgescolar.model.AvaliacaoExterna;
import sgescolar.model.Escola;
import sgescolar.model.Matricula;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAvaliacaoExternaRequest;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AvaliacaoExternaRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.AnoAtualDAO;
import sgescolar.service.dao.TokenDAO;

@Service
public class AvaliacaoExternaService {

	@Autowired
	private AvaliacaoExternaRepository avaliacaoExternaRepository;
	
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private AvaliacaoExternaBuilder avaliacaoExternaBuilder;
	
	@Autowired
	private AnoAtualDAO anoAtualDAO;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	public void registraAvaliacaoExternaAnoAtual( Long alunoId, Long turmaDisciplinaId, SaveAvaliacaoExternaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<AvaliacaoExterna> aextOp = anoAtualDAO.buscaAvaliacaoExternaPorAnoAtual( alunoId, turmaDisciplinaId );
		if ( aextOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_EXTERNA_JA_EXISTE );
		
		Optional<Matricula> matOp = anoAtualDAO.buscaMatriculaPorAnoAtual( alunoId );
		if ( !matOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
				
		Matricula matricula = matOp.get();
		TurmaDisciplina turmaDisciplina = tdOp.get();
		
		Escola escola = turmaDisciplina.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		AvaliacaoExterna aext = avaliacaoExternaBuilder.novoAvaliacaoExterna( matricula, turmaDisciplina );
		avaliacaoExternaBuilder.carregaAvaliacaoExterna( aext, request );
		
		avaliacaoExternaRepository.save( aext );
	}
	
	public void removeAvaliacaoExternaAnoAtual( Long alunoId, Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<AvaliacaoExterna> aextOp = anoAtualDAO.buscaAvaliacaoExternaPorAnoAtual( alunoId, turmaDisciplinaId );
		if ( !aextOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_EXTERNA_NAO_ENCONTRADA );
		
		AvaliacaoExterna aext = aextOp.get();
		
		Escola escola = aext.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		avaliacaoExternaRepository.deleteById( aext.getId() ); 
	}
	
}
