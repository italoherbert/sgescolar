package sgescolar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.EscolaBuilder;
import sgescolar.exception.EscolaJaExisteException;
import sgescolar.exception.EscolaNaoEncontradaException;
import sgescolar.model.Escola;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.EscolaResponse;
import sgescolar.repository.EscolaRepository;

@Service
public class EscolaService {

	@Autowired
	private EscolaRepository escolaRepository;

	@Autowired
	private EscolaBuilder escolaBuilder;
	
	public void registraEscola( SaveEscolaRequest request ) throws EscolaJaExisteException {
		if ( escolaRepository.buscaPorNome( request.getNome() ).isPresent() )
			throw new EscolaJaExisteException();
		
		Escola e = new Escola();
		escolaBuilder.carregaEscola( e, request );
		
		escolaRepository.save( e );
	}
	
	public void atualizaEscola( Long id, SaveEscolaRequest request ) throws EscolaNaoEncontradaException, EscolaJaExisteException {
		Escola e = escolaRepository.findById( id ).orElseThrow( EscolaNaoEncontradaException::new );
		
		if ( !e.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( escolaRepository.buscaPorNome( request.getNome() ).isPresent() )
				throw new EscolaJaExisteException();
		
		escolaBuilder.carregaEscola( e, request );
		
		escolaRepository.save( e ); 
	}
	
	public List<EscolaResponse> filtraEscolasPorNomeIni( String nomeIni ) {
		List<Escola> escolas;
		if ( nomeIni.equals( "*" ) )
			escolas = escolaRepository.findAll();
		else escolas = escolaRepository.filtraPorNomeIni( nomeIni+"%" );
		
		List<EscolaResponse> lista = new ArrayList<>();
		for( Escola e : escolas ) {
			EscolaResponse resp = new EscolaResponse();
			escolaBuilder.carregaEscolaResponse( resp, e );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public EscolaResponse buscaEscola( Long id ) throws EscolaNaoEncontradaException {
		Escola e = escolaRepository.findById( id ).orElseThrow( EscolaNaoEncontradaException::new );
		
		EscolaResponse resp = new EscolaResponse();
		escolaBuilder.carregaEscolaResponse( resp, e );
		return resp;
	}
	
	public void removeEscola( Long id ) throws EscolaNaoEncontradaException {
		if ( !escolaRepository.existsById( id ) )
			throw new EscolaNaoEncontradaException();
		
		escolaRepository.deleteById( id );		
	}
	
}

