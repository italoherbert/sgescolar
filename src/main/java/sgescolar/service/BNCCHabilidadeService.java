package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sgescolar.builder.BNCCHabilidadeBuilder;
import sgescolar.model.BNCCHabilidade;
import sgescolar.model.request.SaveBNCCHabilidadeRequest;
import sgescolar.model.request.filtro.FiltraBNCCHabilidadesRequest;
import sgescolar.model.response.BNCCHabilidadeResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.BNCCHabilidadeRepository;

@Service
public class BNCCHabilidadeService {
	
	@Autowired
	private BNCCHabilidadeRepository bnccHabilidadeRepository;
	
	@Autowired
	private BNCCHabilidadeBuilder bnccHabilidadeBuilder;
		
	public void registraBNCCHabilidade( SaveBNCCHabilidadeRequest request ) throws ServiceException {		
		Optional<BNCCHabilidade> hOp = bnccHabilidadeRepository.findByCodigo( request.getCodigo() );
		if ( hOp.isPresent() )
			throw new ServiceException( ServiceErro.BNCC_HABILIDADE_JA_EXISTE );						
		
		BNCCHabilidade h = bnccHabilidadeBuilder.novoBNCCHabilidade();
		bnccHabilidadeBuilder.carregaBNCCHabilidade( h, request );
		bnccHabilidadeRepository.save( h );
	}
	
	public void alteraBNCCHabilidade( String codigo, SaveBNCCHabilidadeRequest request ) throws ServiceException {
		Optional<BNCCHabilidade> hOp = bnccHabilidadeRepository.findByCodigo( codigo );
		if ( !hOp.isPresent() )
			throw new ServiceException( ServiceErro.BNCC_HABILIDADE_NAO_ENCONTRADA );
		
		if ( !codigo.equalsIgnoreCase( request.getCodigo() ) )
			if ( bnccHabilidadeRepository.existsById( codigo ) )
				throw new ServiceException( ServiceErro.BNCC_HABILIDADE_JA_EXISTE );
		
		BNCCHabilidade h = hOp.get();						
		bnccHabilidadeBuilder.carregaBNCCHabilidade( h, request );
		bnccHabilidadeRepository.save( h );
	}
		
	public List<BNCCHabilidadeResponse> filtraBNCCHabilidades( FiltraBNCCHabilidadesRequest request, Pageable p ) throws ServiceException {
		String codigoIni = request.getCodigoIni()+"%";				
		
		List<BNCCHabilidade> lista = bnccHabilidadeRepository.filtra( codigoIni, p );
		
		List<BNCCHabilidadeResponse> resps = new ArrayList<>();
		for( BNCCHabilidade h : lista ) {		
			BNCCHabilidadeResponse resp = bnccHabilidadeBuilder.novoBNCCHabilidadeResponse();
			bnccHabilidadeBuilder.carregaBNCCHabilidadeResponse( resp, h );
			resps.add( resp );
		}
		
		return resps;
	}
	
	public BNCCHabilidadeResponse buscaBNCCHabilidade( String codigo ) throws ServiceException {
		Optional<BNCCHabilidade> hOp = bnccHabilidadeRepository.findByCodigo( codigo );
		if ( !hOp.isPresent() )
			throw new ServiceException( ServiceErro.BNCC_HABILIDADE_NAO_ENCONTRADA );
		
		BNCCHabilidade h = hOp.get();
		
		BNCCHabilidadeResponse resp = bnccHabilidadeBuilder.novoBNCCHabilidadeResponse();
		bnccHabilidadeBuilder.carregaBNCCHabilidadeResponse( resp, h );
		return resp;
	}

	public void deletaBNCCHabilidade( String codigo ) throws ServiceException {
		boolean existe = bnccHabilidadeRepository.existsByCodigo( codigo );
		if ( !existe )
			throw new ServiceException( ServiceErro.BNCC_HABILIDADE_NAO_ENCONTRADA );
		
		bnccHabilidadeRepository.deleteById( codigo );
	}
	
}

