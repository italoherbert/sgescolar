package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AnoLetivoBuilder;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Escola;
import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.model.response.AnoLetivoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AnoLetivoRepository;
import sgescolar.repository.EscolaRepository;
import sgescolar.util.ConversorUtil;

@Service
public class AnoLetivoService {

	@Autowired
	private AnoLetivoRepository anoLetivoRepository;
	
	@Autowired
	private EscolaRepository escolaRepository;
	
	@Autowired
	private AnoLetivoBuilder anoLetivoBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void registraAnoLetivo( Long escolaId, SaveAnoLetivoRequest request ) throws ServiceException {
		int ano = conversorUtil.stringParaInteiro( request.getAno() );
		
		Optional<AnoLetivo> alOp = anoLetivoRepository.buscaPorAno( escolaId, ano );
		if ( alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_JA_EXISTE, request.getAno() );
		
		Optional<Escola> eop = escolaRepository.findById( escolaId );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
								
		Escola escola = eop.get();
		
		AnoLetivo al = anoLetivoBuilder.novoAnoLetivo( escola );
		anoLetivoBuilder.carregaAnoLetivo( al, request );
		anoLetivoRepository.save( al );
	}
	
	public void alteraAnoLetivo( Long anoLetivoId, SaveAnoLetivoRequest request ) throws ServiceException {
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( anoLetivoId );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		Long escolaId = al.getEscola().getId();
		
		int ano = conversorUtil.stringParaInteiro( request.getAno() );
		if ( al.getAno() != ano )
			if ( anoLetivoRepository.buscaPorAno( escolaId, ano ).isPresent() )
				throw new ServiceException( ServiceErro.ANO_LETIVO_JA_EXISTE, request.getAno() );
		
		anoLetivoBuilder.carregaAnoLetivo( al, request );
		anoLetivoRepository.save( al );
	}
	
	public List<AnoLetivoResponse> listaTodosPorEscola( Long escolaId ) throws ServiceException {
		List<AnoLetivo> lista = anoLetivoRepository.buscaTodosPorEscola( escolaId );
		
		List<AnoLetivoResponse> responses = new ArrayList<>();
		for( AnoLetivo al : lista ) {
			AnoLetivoResponse resp = anoLetivoBuilder.novoAnoLetivoResponse();
			anoLetivoBuilder.carregaAnoLetivoResponse( resp, al );
			responses.add( resp ); 
		}
		
		return responses;
	}
	
	public AnoLetivoResponse buscaAnoLetivoPorAno( Long escolaId, String anostr ) throws ServiceException {
		int ano = conversorUtil.stringParaInteiro( anostr );
		
		Optional<AnoLetivo> alOp = anoLetivoRepository.buscaPorAno( escolaId, ano );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		AnoLetivoResponse resp = anoLetivoBuilder.novoAnoLetivoResponse();
		anoLetivoBuilder.carregaAnoLetivoResponse( resp, al );
		return resp;
	}
	
	public AnoLetivoResponse buscaAnoLetivo( Long id ) throws ServiceException {
		Optional<AnoLetivo> alOp = anoLetivoRepository.findById( id );
		if ( !alOp.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alOp.get();
		
		AnoLetivoResponse resp = anoLetivoBuilder.novoAnoLetivoResponse();
		anoLetivoBuilder.carregaAnoLetivoResponse( resp, al );
		return resp;
	}

	public void deletaAnoLetivo( Long id ) throws ServiceException {
		boolean existe = anoLetivoRepository.existsById( id );
		if ( !existe )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		anoLetivoRepository.deleteById( id );
	}
	
}
