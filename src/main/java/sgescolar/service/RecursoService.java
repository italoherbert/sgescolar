package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.RecursoBuilder;
import sgescolar.model.Recurso;
import sgescolar.model.request.FiltraRecursosRequest;
import sgescolar.model.request.SaveRecursoRequest;
import sgescolar.model.response.RecursoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.RecursoRepository;

@Service
public class RecursoService {
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private RecursoBuilder recursoBuilder;
			
	public void registraRecurso( SaveRecursoRequest request ) throws ServiceException {
		Optional<Recurso> rop = recursoRepository.buscaPorNome( request.getNome() );
		if ( rop.isPresent() )
			throw new ServiceException( ServiceErro.RECURSO_JA_EXISTE );
		
		Recurso r = recursoBuilder.novoRecurso();
		recursoBuilder.carregaRecurso( r, request );
		
		recursoRepository.save( r );						
	}
	
	public void alteraRecurso( Long recursoId, SaveRecursoRequest request ) throws ServiceException {
		Optional<Recurso> rop = recursoRepository.findById( recursoId );
		if ( !rop.isPresent() )
			throw new ServiceException( ServiceErro.RECURSO_NAO_ENCONTRADO );
		
		Recurso r = rop.get();
		
		String nome = request.getNome();		
		if ( !nome.equalsIgnoreCase( r.getNome() ) )
			if ( recursoRepository.buscaPorNome( nome ).isPresent() )
				throw new ServiceException( ServiceErro.RECURSO_JA_EXISTE );
		
		recursoBuilder.carregaRecurso( r, request );		
		recursoRepository.save( r );		
	}
	
	public List<RecursoResponse> filtraRecursos( FiltraRecursosRequest request ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni = "%" + nomeIni + "%";
		
		List<Recurso> recursos = recursoRepository.filtra( nomeIni );
		
		List<RecursoResponse> lista = new ArrayList<>();
		for( Recurso r : recursos ) {
			RecursoResponse resp = recursoBuilder.novoRecursoResponse();
			recursoBuilder.carregaRecursoResponse( resp, r );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<RecursoResponse> listaRecursos() {				
		List<Recurso> recursos = recursoRepository.findAll();
		
		List<RecursoResponse> lista = new ArrayList<>();
		for( Recurso r : recursos ) {
			RecursoResponse resp = recursoBuilder.novoRecursoResponse();
			recursoBuilder.carregaRecursoResponse( resp, r );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public RecursoResponse buscaRecurso( Long recursoId ) throws ServiceException {
		Optional<Recurso> rop = recursoRepository.findById( recursoId );
		if ( !rop.isPresent() )
			throw new ServiceException( ServiceErro.RECURSO_NAO_ENCONTRADO );
		
		Recurso r = rop.get();
		
		RecursoResponse resp = recursoBuilder.novoRecursoResponse();
		recursoBuilder.carregaRecursoResponse( resp, r );
		
		return resp;
	}
	
	public void deletaRecurso( Long recursoId ) throws ServiceException {
		boolean existe = recursoRepository.existsById( recursoId );
		if ( !existe )
			throw new ServiceException( ServiceErro.RECURSO_NAO_ENCONTRADO );
		
		recursoRepository.deleteById( recursoId ); 
	}	
	
}
