package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.SecretarioBuilder;
import sgescolar.model.Escola;
import sgescolar.model.Pessoa;
import sgescolar.model.Secretario;
import sgescolar.model.Usuario;
import sgescolar.model.request.FiltraSecretariosRequest;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.SecretarioRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.PessoaDAO;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class SecretarioService {
			
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EscolaRepository escolaRepository;		
			
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private PessoaDAO pessoaDAO;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private SecretarioBuilder secretarioBuilder;
			
	public void verificaSeDono( Long logadoUID, Long secretarioId ) throws ServiceException {
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario s = secOp.get();
		Long uid = s.getFuncionario().getUsuario().getId();
		
		if ( logadoUID != uid ) 
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
	
	public Long buscaSecretarioIDPorUID( Long uid ) throws ServiceException {
		Optional<Secretario> sop = secretarioRepository.buscaPorUID( uid );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		return sop.get().getId();
	}
	
	@Transactional	
	public void registraSecretario( Long escolaId, SaveSecretarioRequest request, TokenInfos tokenInfos ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getFuncionario().getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getFuncionario().getUsuario().getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
		
		Optional<Escola> eop = escolaRepository.findById( escolaId );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
				
		Escola escola = eop.get();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
				
		Secretario sec = secretarioBuilder.novoSecretario( escola );
		secretarioBuilder.carregaSecretario( sec, request );		
		secretarioRepository.save( sec );

		usuarioDAO.salvaUsuarioGrupoMaps( sec.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}

	@Transactional		
	public void alteraSecretario( Long secretarioId, SaveSecretarioRequest request, TokenInfos tokenInfos ) throws ServiceException {		
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario sec = secOp.get();
		Escola escola = sec.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 

		pessoaDAO.validaAlteracao( sec.getFuncionario().getPessoa(), request.getFuncionario().getPessoa() );
		usuarioDAO.validaAlteracao( sec.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
		
		secretarioBuilder.carregaSecretario( sec, request );		
		secretarioRepository.save( sec );		

		usuarioDAO.salvaUsuarioGrupoMaps( sec.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}
		
	public List<SecretarioResponse> filtraSecretarios( FiltraSecretariosRequest request, TokenInfos tokenInfos ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Secretario> secretarios = secretarioRepository.filtra( nomeIni );
		
		List<SecretarioResponse> lista = new ArrayList<>();
		for( Secretario sec : secretarios ) {
			try {
				Escola escola = sec.getEscola();
				tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
				
				SecretarioResponse resp = secretarioBuilder.novoSecretarioResponse();
				secretarioBuilder.carregaSecretarioResponse( resp, sec );
				
				lista.add( resp );
			} catch ( TokenAutorizacaoException ex ) {
				
			}
		}
		
		return lista;
	}
	
	public SecretarioResponse buscaSecretario( Long secretarioId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario sec = secOp.get();
		Escola escola = sec.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		SecretarioResponse resp = secretarioBuilder.novoSecretarioResponse();
		secretarioBuilder.carregaSecretarioResponse( resp, sec );		
		return resp;
	}
	
	public void deletaSecretario( Long secretarioId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario sec = secOp.get();
		Escola escola = sec.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
				
		secretarioRepository.deleteById( secretarioId ); 
	}
	
}
