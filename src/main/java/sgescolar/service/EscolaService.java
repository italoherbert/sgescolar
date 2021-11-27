package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.EscolaBuilder;
import sgescolar.model.Escola;
import sgescolar.model.Instituicao;
import sgescolar.model.request.FiltraEscolasRequest;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.EscolaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;
import sgescolar.repository.InstituicaoRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;

@Service
public class EscolaService {

	@Autowired
	private EscolaRepository escolaRepository;

	@Autowired
	private InstituicaoRepository instituicaoRepository;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private EscolaBuilder escolaBuilder;
	
	public void registraEscola( Long instituicaoId, SaveEscolaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		if ( escolaRepository.buscaPorNome( request.getNome() ).isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_JA_EXISTE );
		
		Optional<Instituicao> iop = instituicaoRepository.findById( instituicaoId );
		if ( !iop.isPresent() )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		Instituicao inst = iop.get();
		
		tokenDAO.autorizaPorInstituicao( inst, tokenInfos );
		
		Escola e = escolaBuilder.novoEscola( inst ); 
		escolaBuilder.carregaEscola( e, request );
		
		escolaRepository.save( e );
	}
	
	public void atualizaEscola( Long id, SaveEscolaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Escola> escolaOp = escolaRepository.findById( id );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola e = escolaOp.get();
		if ( !e.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( escolaRepository.buscaPorNome( request.getNome() ).isPresent() )
				throw new ServiceException( ServiceErro.ESCOLA_JA_EXISTE );
		
		tokenDAO.autorizaPorEscolaOuInstituicao( e, tokenInfos );
		
		escolaBuilder.carregaEscola( e, request );		
		escolaRepository.save( e ); 
	}
	
	public List<EscolaResponse> listaEscolas( Long instituicaoId, TokenInfos tokenInfos ) {
		List<Escola> escolas = escolaRepository.lista( instituicaoId );
		
		List<EscolaResponse> responses = new ArrayList<>();
		for( Escola e : escolas ) {
			try {
				tokenDAO.autorizaPorEscolaOuInstituicao( e, tokenInfos );
				
				EscolaResponse resp = escolaBuilder.novoEscolaResponse();
				escolaBuilder.carregaEscolaResponse( resp, e ); 
				responses.add( resp );
			} catch ( TokenAutorizacaoException ex ) {
				
			}
		}
		return responses;
	}
	
	public List<EscolaResponse> filtraEscolas( Long instituicaoId, FiltraEscolasRequest request, TokenInfos tokenInfos ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni = "%" + nomeIni + "%";
		
		List<Escola> escolas = escolaRepository.filtra( instituicaoId, nomeIni );
		
		List<EscolaResponse> lista = new ArrayList<>();
		for( Escola e : escolas ) {
			try {
				tokenDAO.autorizaPorEscolaOuInstituicao( e, tokenInfos );
				
				EscolaResponse resp = escolaBuilder.novoEscolaResponse();
				escolaBuilder.carregaEscolaResponse( resp, e );
				
				lista.add( resp );
			} catch ( TokenAutorizacaoException ex ) {
				
			}
		}
		
		return lista;
	}
	
	public EscolaResponse buscaEscola( Long escolaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Escola> eop = escolaRepository.findById( escolaId );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola e = eop.get();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( e, tokenInfos );
		
		EscolaResponse resp = escolaBuilder.novoEscolaResponse();
		escolaBuilder.carregaEscolaResponse( resp, e );
		return resp;
	}
	
	public void removeEscola( Long escolaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Escola> escolaOp = escolaRepository.findById( escolaId );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola e = escolaOp.get();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( e, tokenInfos );
		
		escolaRepository.deleteById( escolaId );		
	}
	
}

