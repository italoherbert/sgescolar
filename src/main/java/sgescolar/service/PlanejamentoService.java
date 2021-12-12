package sgescolar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sgescolar.builder.PlanejamentoAnexoBuilder;
import sgescolar.builder.PlanejamentoBuilder;
import sgescolar.builder.PlanejamentoConteudoBuilder;
import sgescolar.builder.PlanejamentoObjetivoBuilder;
import sgescolar.enums.tipos.PlanejamentoTipo;
import sgescolar.model.Escola;
import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.PlanejamentoConteudo;
import sgescolar.model.PlanejamentoObjetivo;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.request.SavePlanejamentoConteudoRequest;
import sgescolar.model.request.SavePlanejamentoObjetivoRequest;
import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.model.request.filtro.FiltraPlanejamentosRequest;
import sgescolar.model.response.PlanejamentoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PlanejamentoAnexoRepository;
import sgescolar.repository.PlanejamentoRepository;
import sgescolar.repository.ProfessorAlocacaoRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;
import sgescolar.util.ConversorUtil;

@Service
public class PlanejamentoService {

	@Autowired
	private PlanejamentoRepository planejamentoRepository;
	
	@Autowired
	private PlanejamentoAnexoRepository planejamentoAnexoRepository;
		
	@Autowired
	private ProfessorAlocacaoRepository professorAlocacaoRepository;
	
	@Autowired
	private PlanejamentoBuilder planejamentoBuilder;
	
	@Autowired
	private PlanejamentoObjetivoBuilder planejamentoObjetivoBuilder;
	
	@Autowired
	private PlanejamentoConteudoBuilder planejamentoConteudoBuilder;
	
	@Autowired
	private PlanejamentoAnexoBuilder planejamentoAnexoBuilder;
			
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	public void registraPlanejamento( Long professorAlocacaoId, SavePlanejamentoRequest request, MultipartFile[] files, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao profAloc = paOp.get();
		
		Escola escola = profAloc.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Planejamento planejamento = planejamentoBuilder.novoPlanejamento( profAloc );
		
		this.salvaPlanejamento( planejamento, request, files );
	}
	
	public void alteraPlanejamento( Long planejamentoId, SavePlanejamentoRequest request, MultipartFile[] files, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Planejamento> pOp = planejamentoRepository.findById( planejamentoId );
		if ( !pOp.isPresent() )
			throw new ServiceException( ServiceErro.PLANEJAMENTO_NAO_ENCONTRADO );
		
		Planejamento planejamento = pOp.get();
		
		Escola escola = planejamento.getProfessorAlocacao().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		this.salvaPlanejamento( planejamento, request, files );
	}
		
	@Transactional
	private void salvaPlanejamento( Planejamento planejamento, SavePlanejamentoRequest request, MultipartFile[] files ) throws ServiceException {	
		planejamentoBuilder.carregaPlanejamento( planejamento, request ); 				
		
		List<PlanejamentoObjetivo> objetivos = planejamento.getObjetivos();
		if ( objetivos == null )
			objetivos = new ArrayList<>();
		
		List<PlanejamentoConteudo> conteudos = planejamento.getConteudos();
		if ( conteudos == null )
			conteudos = new ArrayList<>();
						
		objetivos.clear();
		conteudos.clear();
				
		List<SavePlanejamentoObjetivoRequest> oreqs = request.getObjetivos();
		for( SavePlanejamentoObjetivoRequest oreq : oreqs ) {
			PlanejamentoObjetivo obj = planejamentoObjetivoBuilder.novoPlanejamentoObjetivo( planejamento );
			planejamentoObjetivoBuilder.carregaPlanejamentoObjetivo( obj, oreq );
			objetivos.add( obj );
		}
		
		List<SavePlanejamentoConteudoRequest> conReqs = request.getConteudos();
		for( SavePlanejamentoConteudoRequest conReq : conReqs ) {
			PlanejamentoConteudo conteudo = planejamentoConteudoBuilder.novoPlanejamentoConteudo( planejamento );
			planejamentoConteudoBuilder.carregaPlanejamentoConteudo( conteudo, conReq );
			conteudos.add( conteudo );
		}				
		
		planejamento.setObjetivos( objetivos );
		planejamento.setConteudos( conteudos );
		
		planejamentoRepository.save( planejamento );
		
		for( MultipartFile file : files ) {
			PlanejamentoAnexo anexo = planejamentoAnexoBuilder.novoPlanejamentoAnexo( planejamento );
			planejamentoAnexoBuilder.carregaPlanejamentoAnexo( anexo, file );
			planejamentoAnexoRepository.save( anexo );			
		}
	}
	
	public List<PlanejamentoResponse> listaEnsinoPlanejamentos( Long professorAlocacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao profAloc = paOp.get();
		
		Escola escola = profAloc.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
								
		List<PlanejamentoResponse> responses = new ArrayList<>();
		
		List<Planejamento> planejamentos = planejamentoRepository.lista( professorAlocacaoId, PlanejamentoTipo.ENSINO );
		for( Planejamento p : planejamentos ) {
			PlanejamentoResponse resp = planejamentoBuilder.novoPlanejamentoResponse();
			planejamentoBuilder.carregaPlanejamentoResponse( resp, p );
			responses.add( resp );			
		}
		
		return responses;
	}
	
	public List<PlanejamentoResponse> listaPlanejamentos( Long professorAlocacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao profAloc = paOp.get();
		
		Escola escola = profAloc.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
								
		List<PlanejamentoResponse> responses = new ArrayList<>();
		
		List<Planejamento> planejamentos = planejamentoRepository.lista( professorAlocacaoId );
		for( Planejamento p : planejamentos ) {
			PlanejamentoResponse resp = planejamentoBuilder.novoPlanejamentoResponse();
			planejamentoBuilder.carregaPlanejamentoResponse( resp, p );
			responses.add( resp );			
		}
		
		return responses;
	}
	
	public List<PlanejamentoResponse> filtraPlanejamentos( Long professorAlocacaoId, FiltraPlanejamentosRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao profAloc = paOp.get();
		
		Escola escola = profAloc.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
				
		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni += "%";
		
		Date intervaloData = conversorUtil.stringParaData( request.getIntervaloData() );
		
		List<PlanejamentoResponse> responses = new ArrayList<>();
		
		List<Planejamento> planejamentos = planejamentoRepository.filtra( professorAlocacaoId, descricaoIni, intervaloData );
		for( Planejamento p : planejamentos ) {
			PlanejamentoResponse resp = planejamentoBuilder.novoPlanejamentoResponse();
			planejamentoBuilder.carregaPlanejamentoResponse( resp, p );
			responses.add( resp );			
		}
		
		return responses;		
	}
	
	public PlanejamentoResponse getPlanejamento( Long planejamentoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Planejamento> pOp = planejamentoRepository.findById( planejamentoId );
		if ( !pOp.isPresent() )
			throw new ServiceException( ServiceErro.PLANEJAMENTO_NAO_ENCONTRADO );
		
		Planejamento planejamento = pOp.get();
		
		Escola escola = planejamento.getProfessorAlocacao().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		PlanejamentoResponse resp = planejamentoBuilder.novoPlanejamentoResponse();
		planejamentoBuilder.carregaPlanejamentoResponse( resp, planejamento );
		return resp;
	}
	
	public void deletaPlanejamento( Long planejamentoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Planejamento> pOp = planejamentoRepository.findById( planejamentoId );
		if ( !pOp.isPresent() )
			throw new ServiceException( ServiceErro.PLANEJAMENTO_NAO_ENCONTRADO );
		
		Planejamento planejamento = pOp.get();
		
		Escola escola = planejamento.getProfessorAlocacao().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		planejamentoRepository.deleteById( planejamentoId ); 
	}
	
}
