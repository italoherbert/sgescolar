package sgescolar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PessoaBuilder;
import sgescolar.exception.PessoaJaExisteException;
import sgescolar.exception.PessoaNaoEncontradaException;
import sgescolar.model.Pessoa;
import sgescolar.model.request.PessoaRequest;
import sgescolar.model.response.PessoaResponse;
import sgescolar.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	public void registraPessoa( PessoaRequest request ) throws PessoaJaExisteException {
		if ( pessoaRepository.buscaPorNome( request.getNome() ).isPresent() )
			throw new PessoaJaExisteException();
		
		Pessoa p = new Pessoa();
		pessoaBuilder.carregaPessoa( p, request );
		
		pessoaRepository.save( p );
	}
	
	public void atualizaPessoa( Long id, PessoaRequest request ) throws PessoaNaoEncontradaException, PessoaJaExisteException {
		Pessoa p = pessoaRepository.findById( id ).orElseThrow( PessoaNaoEncontradaException::new );
		
		if ( !p.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( pessoaRepository.buscaPorNome( request.getNome() ).isPresent() )
				throw new PessoaJaExisteException();
		
		pessoaBuilder.carregaPessoa( p, request );
		
		pessoaRepository.save( p ); 
	}
	
	public List<PessoaResponse> filtraPessoasPorNomeIni( String nomeIni ) {
		List<Pessoa> pessoas;
		if ( nomeIni.equals( "*" ) )
			pessoas = pessoaRepository.findAll();
		else pessoas = pessoaRepository.filtraPorNomeIni( nomeIni+"%" );
		
		List<PessoaResponse> lista = new ArrayList<>();
		for( Pessoa p : pessoas ) {
			PessoaResponse resp = new PessoaResponse();
			pessoaBuilder.carregaPessoaResponse( resp, p );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public PessoaResponse buscaPessoa( Long id ) throws PessoaNaoEncontradaException {
		Pessoa p = pessoaRepository.findById( id ).orElseThrow( PessoaNaoEncontradaException::new );
		
		PessoaResponse resp = new PessoaResponse();
		pessoaBuilder.carregaPessoaResponse( resp, p );
		return resp;
	}
	
	public void removePessoa( Long id ) throws PessoaNaoEncontradaException {
		if ( !pessoaRepository.existsById( id ) )
			throw new PessoaNaoEncontradaException();
		
		pessoaRepository.deleteById( id );		
	}
	
}
