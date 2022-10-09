package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PeriodoBuilder;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Escola;
import sgescolar.model.Periodo;
import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.model.response.PeriodoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AnoLetivoRepository;
import sgescolar.repository.PeriodoRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class PeriodoService {

	@Autowired
	private AnoLetivoRepository anoLetivoRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Autowired
	private PeriodoBuilder periodoBuilder;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	public void registraPeriodo( Long anoLetivoId, SavePeriodoRequest request, TokenInfos tokenInfos ) throws ServiceException {		
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( anoLetivoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		Escola escola = al.getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Periodo pl = periodoBuilder.novoPeriodo( al );
		periodoBuilder.carregaPeriodo( pl, request );
		periodoRepository.save( pl );
	}
	
	public void alteraPeriodo( Long periodoId, SavePeriodoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Periodo> alOp = periodoRepository.findById( periodoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
		
		Periodo p = alOp.get();						
		
		Escola escola = p.getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		periodoBuilder.carregaPeriodo( p, request );
		periodoRepository.save( p );
	}
		
	public List<PeriodoResponse> listaPeriodos( Long anoLetivoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( anoLetivoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		Escola escola = al.getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		List<Periodo> lista = periodoRepository.listaPorAnoLetivo( anoLetivoId );
		
		List<PeriodoResponse> resps = new ArrayList<>();
		for( Periodo p : lista ) {				
			PeriodoResponse resp = periodoBuilder.novoPeriodoResponse();
			periodoBuilder.carregaPeriodoResponse( resp, p );
			resps.add( resp );			
		}
		
		return resps;
	}
	
	public PeriodoResponse buscaPeriodo( Long id, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Periodo> alOp = periodoRepository.findById( id );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
		
		Periodo p = alOp.get();
		
		Escola escola = p.getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		PeriodoResponse resp = periodoBuilder.novoPeriodoResponse();
		periodoBuilder.carregaPeriodoResponse( resp, p );
		return resp;
	}

	public void deletaPeriodo( Long id, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Periodo> pOp = periodoRepository.findById( id );
		if ( !pOp.isPresent() )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
		
		Periodo p = pOp.get();
		
		Escola escola = p.getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		periodoRepository.deleteById( id );
	}
	
}
