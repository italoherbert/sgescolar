package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AvaliacaoBuilder;
import sgescolar.model.Avaliacao;
import sgescolar.model.Escola;
import sgescolar.model.Nota;
import sgescolar.model.Periodo;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAgendamentoAvaliacaoRequest;
import sgescolar.model.request.SaveResultadoAvaliacaoRequest;
import sgescolar.model.response.AvaliacaoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AvaliacaoRepository;
import sgescolar.repository.PeriodoRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;
import sgescolar.service.filtro.AvaliacoesListagem;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
		
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Autowired
	private AvaliacaoBuilder avaliacaoBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	public void agendaAvaliacao( Long turmaDisciplinaId, Long periodoId, SaveAgendamentoAvaliacaoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		Optional<Periodo> periodoOp = periodoRepository.findById( periodoId );
		if ( !periodoOp.isPresent() )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
				
		TurmaDisciplina td = tdOp.get();
		Periodo periodo = periodoOp.get();
		
		Escola escola = td.getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
				
		Avaliacao avaliacao = avaliacaoBuilder.novoAvaliacao( td, periodo );
		avaliacaoBuilder.carregaAgendamentoAvaliacao( avaliacao, request );
		avaliacaoRepository.save( avaliacao );
	}
	
	@Transactional
	public void salvaResultadoAvaliacao( Long avaliacaoId, SaveResultadoAvaliacaoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Avaliacao> avOp = avaliacaoRepository.findById( avaliacaoId );
		if ( !avOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_NAO_ENCONTRADA );
		
		Avaliacao avaliacao = avOp.get();
		List<Nota> notas = avaliacao.getNotas();
		if ( notas != null )
			notas.clear();
				
		Escola escola = avaliacao.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );

		avaliacaoBuilder.carregaResultadoAvaliacao( avaliacao, request ); 		
		avaliacaoRepository.save( avaliacao );
	}
	
	public List<AvaliacaoResponse> listaAvaliacoes( Long turmaDisciplinaId, TokenInfos tokenInfos, AvaliacoesListagem listagem ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Escola escola = td.getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		List<AvaliacaoResponse> lista = new ArrayList<>();

		List<Avaliacao> avaliacoes = listagem.lista( avaliacaoRepository, turmaDisciplinaId ); 
		for( Avaliacao a : avaliacoes ) {			
			AvaliacaoResponse resp = avaliacaoBuilder.novoAvaliacaoResponse();
			avaliacaoBuilder.carregaAvaliacaoResponse( resp, a );
			lista.add( resp );
		}		
		return lista;
	}
	
	public AvaliacaoResponse getAvaliacao( Long avaliacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Avaliacao> avOp = avaliacaoRepository.findById( avaliacaoId );
		if ( !avOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_NAO_ENCONTRADA );
		
		Avaliacao avaliacao = avOp.get();
		
		Escola escola = avaliacao.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		AvaliacaoResponse resp = avaliacaoBuilder.novoAvaliacaoResponse();
		avaliacaoBuilder.carregaAvaliacaoResponse( resp, avaliacao );
		return resp;
	}
	
	public void removeAvaliacao( Long avaliacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Avaliacao> avOp = avaliacaoRepository.findById( avaliacaoId );
		if ( !avOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_NAO_ENCONTRADA );
		
		Avaliacao avaliacao = avOp.get();
		
		Escola escola = avaliacao.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		avaliacaoRepository.deleteById( avaliacaoId ); 
	}
	
}
