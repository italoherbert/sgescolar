package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PeriodoBuilder;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Periodo;
import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.model.response.PeriodoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AnoLetivoRepository;
import sgescolar.repository.PeriodoRepository;

@Service
public class PeriodoService {

	@Autowired
	private AnoLetivoRepository anoLetivoRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Autowired
	private PeriodoBuilder periodoBuilder;
		
	public void registraPeriodo( Long anoLetivoId, SavePeriodoRequest request ) throws ServiceException {		
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( anoLetivoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		Periodo pl = periodoBuilder.novoPeriodo( al );
		periodoBuilder.carregaPeriodo( pl, request );
		periodoRepository.save( pl );
	}
	
	public void alteraPeriodo( Long periodoId, SavePeriodoRequest request ) throws ServiceException {
		Optional<Periodo> alOp = periodoRepository.findById( periodoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
		
		Periodo f = alOp.get();						
		periodoBuilder.carregaPeriodo( f, request );
		periodoRepository.save( f );
	}
		
	public List<PeriodoResponse> listaPeriodos( Long anoLetivoId ) throws ServiceException {				
		List<Periodo> lista = periodoRepository.listaPorAnoLetivo( anoLetivoId );
		
		List<PeriodoResponse> resps = new ArrayList<>();
		for( Periodo f : lista ) {		
			PeriodoResponse resp = periodoBuilder.novoPeriodoResponse();
			periodoBuilder.carregaPeriodoResponse( resp, f );
			resps.add( resp );
		}
		
		return resps;
	}
	
	public PeriodoResponse buscaPeriodo( Long id ) throws ServiceException {
		Optional<Periodo> alOp = periodoRepository.findById( id );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
		
		Periodo f = alOp.get();
		
		PeriodoResponse resp = periodoBuilder.novoPeriodoResponse();
		periodoBuilder.carregaPeriodoResponse( resp, f );
		return resp;
	}

	public void deletaPeriodo( Long id ) throws ServiceException {
		boolean existe = periodoRepository.existsById( id );
		if ( !existe )
			throw new ServiceException( ServiceErro.PERIODO_NAO_ENCONTRADO );
		
		periodoRepository.deleteById( id );
	}
	
}
