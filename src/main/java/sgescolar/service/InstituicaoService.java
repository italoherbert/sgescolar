package sgescolar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.InstituicaoBuilder;
import sgescolar.model.Instituicao;
import sgescolar.model.request.SaveInstituicaoRequest;
import sgescolar.model.response.InstituicaoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.InstituicaoRepository;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepository instituicaoRepository;
	
	@Autowired
	private InstituicaoBuilder instituicaoBuilder;
		
	public void salvaInstituicao( SaveInstituicaoRequest request ) throws ServiceException {
		long count = instituicaoRepository.count();
		
		Instituicao inst;
		if ( count == 0 ) {
			inst = instituicaoBuilder.novoInstituicao();
			
		} else {
			inst = instituicaoRepository.findAll().get( 0 );
		}
		instituicaoBuilder.carregaInstituicao( inst, request );
		instituicaoRepository.save( inst );
	}
	
	public InstituicaoResponse buscaInstituicao() throws ServiceException {
		List<Instituicao> lista = instituicaoRepository.findAll();
		if ( lista.isEmpty() )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		Instituicao inst = lista.get( 0 );
				
		InstituicaoResponse resp = instituicaoBuilder.novoInstituicaoResponse();
		instituicaoBuilder.carregaInstituicaoResponse( resp, inst );		
		return resp;
	}
	
	public void deletaInstituicao() throws ServiceException {
		long count = instituicaoRepository.count();
		if ( count == 0 )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		instituicaoRepository.deleteAll();				
	}
	
}
