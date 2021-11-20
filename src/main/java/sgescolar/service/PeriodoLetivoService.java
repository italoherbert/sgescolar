package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PeriodoLetivoBuilder;
import sgescolar.model.AnoLetivo;
import sgescolar.model.PeriodoLetivo;
import sgescolar.model.request.SavePeriodoLetivoRequest;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AnoLetivoRepository;
import sgescolar.repository.PeriodoLetivoRepository;

@Service
public class PeriodoLetivoService {

	@Autowired
	private AnoLetivoRepository anoLetivoRepository;
	
	@Autowired
	private PeriodoLetivoRepository periodoLetivoRepository;
	
	@Autowired
	private PeriodoLetivoBuilder periodoLetivoBuilder;
		
	public void registraPeriodoLetivo( Long anoLetivoId, SavePeriodoLetivoRequest request ) throws ServiceException {		
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( anoLetivoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		PeriodoLetivo pl = periodoLetivoBuilder.novoPeriodoLetivo( al );
		periodoLetivoBuilder.carregaPeriodoLetivo( pl, request );
		periodoLetivoRepository.save( pl );
	}
	
}
