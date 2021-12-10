package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.InstituicaoBuilder;
import sgescolar.model.Instituicao;
import sgescolar.model.request.SaveInstituicaoRequest;
import sgescolar.model.request.filtro.FiltraInstituicaoRequest;
import sgescolar.model.response.InstituicaoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.InstituicaoRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepository instituicaoRepository;
	
	@Autowired
	private InstituicaoBuilder instituicaoBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
		
	public void registraInstituicao( SaveInstituicaoRequest request ) throws ServiceException {
		Optional<Instituicao> instituicaoOp = instituicaoRepository.buscaPorCNPJ( request.getCnpj() );
		if ( instituicaoOp.isPresent() )
			throw new ServiceException( ServiceErro.INSTITUICAO_JA_EXISTE );
		
		Instituicao instituicao = instituicaoBuilder.novoInstituicao();
		instituicaoBuilder.carregaInstituicao( instituicao, request );
		instituicaoRepository.save( instituicao );		
	}
	
	public void alteraInstituicao( Long instituicaoId, SaveInstituicaoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Instituicao> instituicaoOp = instituicaoRepository.findById( instituicaoId );
		if ( !instituicaoOp.isPresent() )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		Instituicao instituicao = instituicaoOp.get();
						
		tokenDAO.autorizaPorInstituicao( instituicao, tokenInfos );
		
		instituicaoBuilder.carregaInstituicao( instituicao, request );
		instituicaoRepository.save( instituicao );
	}
	
	public InstituicaoResponse buscaInstituicao( Long instituicaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Instituicao> instituicaoOp = instituicaoRepository.findById( instituicaoId );
		if ( !instituicaoOp.isPresent() )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		Instituicao inst = instituicaoOp.get();	
		tokenDAO.autorizaPorInstituicao( inst, tokenInfos ); 
				
		InstituicaoResponse resp = instituicaoBuilder.novoInstituicaoResponse();
		instituicaoBuilder.carregaInstituicaoResponse( resp, inst );		
		return resp;
	}
	
	public List<InstituicaoResponse> filtraInstituicoes( FiltraInstituicaoRequest request, TokenInfos tokenInfos ) throws ServiceException {
		String cnpj = request.getCnpj();		
		
		String razaoSocialIni = request.getRazaoSocialIni();
		if ( razaoSocialIni.equals( "*" ) )
			razaoSocialIni = "";
		razaoSocialIni = "%" + razaoSocialIni + "%";
		
		List<InstituicaoResponse> responses = new ArrayList<>();
				
		List<Instituicao> instituicoes = instituicaoRepository.filtra( cnpj, razaoSocialIni );
		for( Instituicao inst : instituicoes ) {
			try {
				tokenDAO.autorizaPorInstituicao( inst, tokenInfos );
				
				InstituicaoResponse resp = instituicaoBuilder.novoInstituicaoResponse();
				instituicaoBuilder.carregaInstituicaoResponse( resp, inst ); 
				responses.add( resp );
			} catch ( TokenAutorizacaoException e ) {
				
			}
		}
		
		return responses;
	}
	
	public List<InstituicaoResponse> listaInstituicoes( TokenInfos tokenInfos ) throws ServiceException {
		List<InstituicaoResponse> responses = new ArrayList<>();
		
		List<Instituicao> instituicoes = instituicaoRepository.findAll();
		for( Instituicao inst : instituicoes ) {
			try {
				tokenDAO.autorizaPorInstituicao( inst, tokenInfos );
				
				InstituicaoResponse resp = instituicaoBuilder.novoInstituicaoResponse();
				instituicaoBuilder.carregaInstituicaoResponse( resp, inst ); 
				responses.add( resp );
			} catch ( TokenAutorizacaoException e ) {
				
			}
		}
		
		return responses;
	}
	
	public void deletaInstituicao( Long instituicaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Instituicao> instituicaoOp = instituicaoRepository.findById( instituicaoId );
		if ( !instituicaoOp.isPresent() )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		Instituicao inst = instituicaoOp.get();		
		tokenDAO.autorizaPorInstituicao( inst, tokenInfos );
		
		instituicaoRepository.deleteById( instituicaoId );		
	}
	
}
