package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.FeriadoBuilder;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Feriado;
import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.model.request.filtro.FiltraFeriadoRequest;
import sgescolar.model.response.FeriadoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AnoLetivoRepository;
import sgescolar.repository.FeriadoRepository;

@Service
public class FeriadoService {

	@Autowired
	private AnoLetivoRepository anoLetivoRepository;
	
	@Autowired
	private FeriadoRepository feriadoRepository;
		
	@Autowired
	private FeriadoBuilder feriadoBuilder;
		
	public void registraFeriado( Long anoLetivoId, SaveFeriadoRequest request ) throws ServiceException {
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( anoLetivoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		Feriado f = feriadoBuilder.novoFeriado( al );
		feriadoBuilder.carregaFeriado( f, request );
		feriadoRepository.save( f );
	}
	
	public void alteraFeriado( Long feriadoId, SaveFeriadoRequest request ) throws ServiceException {
		Optional<Feriado> alOp = feriadoRepository.findById( feriadoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.FERIADO_NAO_ENCONTRADO );
		
		Feriado f = alOp.get();						
		feriadoBuilder.carregaFeriado( f, request );
		feriadoRepository.save( f );
	}
	
	public List<FeriadoResponse> filtra( Long anoLetivoId, FiltraFeriadoRequest request ) throws ServiceException {
		String descIni = request.getDescricaoIni();
		if ( descIni.equals( "*" ) )
			descIni = "";
		descIni += "%";
		
		List<Feriado> lista = feriadoRepository.filtra( anoLetivoId, descIni );
		
		List<FeriadoResponse> resps = new ArrayList<>();
		for( Feriado f : lista ) {		
			FeriadoResponse resp = feriadoBuilder.novoFeriadoResponse();
			feriadoBuilder.carregaFeriadoResponse( resp, f );
			resps.add( resp );
		}
		
		return resps;
	}
	
	public List<FeriadoResponse> lista( Long anoLetivoId ) throws ServiceException {				
		List<Feriado> lista = feriadoRepository.listaPorAnoLetivo( anoLetivoId );
		
		List<FeriadoResponse> resps = new ArrayList<>();
		for( Feriado f : lista ) {		
			FeriadoResponse resp = feriadoBuilder.novoFeriadoResponse();
			feriadoBuilder.carregaFeriadoResponse( resp, f );
			resps.add( resp );
		}
		
		return resps;
	}
	
	public FeriadoResponse buscaFeriado( Long id ) throws ServiceException {
		Optional<Feriado> alOp = feriadoRepository.findById( id );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.FERIADO_NAO_ENCONTRADO );
		
		Feriado f = alOp.get();
		
		FeriadoResponse resp = feriadoBuilder.novoFeriadoResponse();
		feriadoBuilder.carregaFeriadoResponse( resp, f );
		return resp;
	}

	public void deletaFeriado( Long id ) throws ServiceException {
		boolean existe = feriadoRepository.existsById( id );
		if ( !existe )
			throw new ServiceException( ServiceErro.FERIADO_NAO_ENCONTRADO );
		
		feriadoRepository.deleteById( id );
	}
	
}

