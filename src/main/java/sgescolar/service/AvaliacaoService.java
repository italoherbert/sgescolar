package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AvaliacaoBuilder;
import sgescolar.builder.AvaliacaoResultadoBuilder;
import sgescolar.enums.tipos.AvaliacaoConceito;
import sgescolar.model.Avaliacao;
import sgescolar.model.AvaliacaoResultado;
import sgescolar.model.Escola;
import sgescolar.model.Matricula;
import sgescolar.model.Periodo;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAvaliacaoAgendamentoRequest;
import sgescolar.model.request.SaveAvaliacaoResultadoGrupoRequest;
import sgescolar.model.response.AvaliacaoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AvaliacaoRepository;
import sgescolar.repository.AvaliacaoResultadoRepository;
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
	private AvaliacaoResultadoRepository avaliacaoResultadoRepository; 
		
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Autowired
	private AvaliacaoBuilder avaliacaoBuilder;
	
	@Autowired
	private AvaliacaoResultadoBuilder avaliacaoResultadoBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Transactional
	public void sincronizaAvaliacaoMatriculas( Long avaliacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Avaliacao> avOp = avaliacaoRepository.findById( avaliacaoId );
		if ( !avOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_NAO_ENCONTRADA );
		
		Avaliacao av = avOp.get();
		Turma turma = av.getTurmaDisciplina().getTurma();
		
		Escola escola = turma.getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		List<AvaliacaoResultado> resultados = new ArrayList<>();
		
		List<AvaliacaoResultado> lista = av.getResultados();
		int size = lista.size();
						
		List<Matricula> matriculas = turma.getMatriculas();
		for( Matricula matricula : matriculas ) {
			if ( matricula.isEncerrada() )
				continue;
			
			AvaliacaoResultado avR = null;
			for( int i = 0; avR == null && i < size; i++ ) {
				AvaliacaoResultado ar = lista.get( i );
				Matricula m = ar.getMatricula();
				if ( matricula.getId() == m.getId() )
					avR = ar;
			}
				
			AvaliacaoResultado resultado = avaliacaoResultadoBuilder.novoAvaliacaoResultado( matricula, av );	
			if ( avR == null ) {							
				resultado.setNota( 0 );
				resultado.setConceito( AvaliacaoConceito.NAO_DISPONIVEL );
				resultado.setDescricao( "" );
			} else {
				resultado.setNota( avR.getNota() );
				resultado.setConceito( avR.getConceito() );
				resultado.setDescricao( avR.getDescricao() );	
			}
			resultados.add( resultado );
		}				
		
		lista.clear();
		
		for( AvaliacaoResultado avr : resultados )
			avaliacaoResultadoRepository.save( avr );				
	}
	
	public void registraAgendaAvaliacao( Long turmaDisciplinaId, Long periodoId, SaveAvaliacaoAgendamentoRequest request, TokenInfos tokenInfos ) throws ServiceException {
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
	
	public void alteraAgendaAvaliacao( Long avaliacaoId, SaveAvaliacaoAgendamentoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Avaliacao> avOp = avaliacaoRepository.findById( avaliacaoId );
		if ( !avOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_NAO_ENCONTRADA );
		
		Avaliacao avaliacao = avOp.get();
		
		Escola escola = avaliacao.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		avaliacaoBuilder.carregaAgendamentoAvaliacao( avaliacao, request );
		avaliacaoRepository.save( avaliacao );		
	}
			
	@Transactional
	public void salvaResultadoAvaliacao( Long avaliacaoId, SaveAvaliacaoResultadoGrupoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Avaliacao> avOp = avaliacaoRepository.findById( avaliacaoId );
		if ( !avOp.isPresent() )
			throw new ServiceException( ServiceErro.AVALIACAO_NAO_ENCONTRADA );
		
		Avaliacao avaliacao = avOp.get();
		List<AvaliacaoResultado> resultados = avaliacao.getResultados();
		if ( resultados != null )
			resultados.clear();
				
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
