package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.EscolaBuilder;
import sgescolar.model.Escola;
import sgescolar.model.request.FiltraEscolasRequest;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.EscolaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;

@Service
public class EscolaService {

	@Autowired
	private EscolaRepository escolaRepository;

	@Autowired
	private EscolaBuilder escolaBuilder;
	
	public void registraEscola( SaveEscolaRequest request ) throws ServiceException {
		if ( escolaRepository.buscaPorNome( request.getNome() ).isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_JA_EXISTE );
		
		Escola e = escolaBuilder.novoEscola();
		escolaBuilder.carregaEscola( e, request );
		
		escolaRepository.save( e );
	}
	
	public void atualizaEscola( Long id, SaveEscolaRequest request ) throws ServiceException {
		Optional<Escola> escolaOp = escolaRepository.findById( id );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola e = escolaOp.get();
		if ( !e.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( escolaRepository.buscaPorNome( request.getNome() ).isPresent() )
				throw new ServiceException( ServiceErro.ESCOLA_JA_EXISTE );
		
		escolaBuilder.carregaEscola( e, request );		
		escolaRepository.save( e ); 
	}
	
	public List<EscolaResponse> filtraEscolas( FiltraEscolasRequest request ) {
		String nomeIni = request.getNomeIni();
		
		List<Escola> escolas;
		if ( nomeIni.equals( "*" ) )
			escolas = escolaRepository.findAll();
		else escolas = escolaRepository.filtraPorNomeIni( nomeIni+"%" );
		
		List<EscolaResponse> lista = new ArrayList<>();
		for( Escola e : escolas ) {
			EscolaResponse resp = escolaBuilder.novoEscolaResponse();
			escolaBuilder.carregaEscolaResponse( resp, e );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public EscolaResponse buscaEscola( Long id ) throws ServiceException {
		Optional<Escola> eop = escolaRepository.findById( id );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola e = eop.get();
		
		EscolaResponse resp = escolaBuilder.novoEscolaResponse();
		escolaBuilder.carregaEscolaResponse( resp, e );
		return resp;
	}
	
	public void removeEscola( Long id ) throws ServiceException {
		if ( !escolaRepository.existsById( id ) )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		escolaRepository.deleteById( id );		
	}
	
}

