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
import sgescolar.model.request.FiltraSecretariosRequest;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.SecretarioRepository;
import sgescolar.service.dao.UsuarioDAO;
import sgescolar.service.filtro.FiltroSecretarios;

@Service
public class SecretarioService {
			
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EscolaRepository escolaRepository;		
		
	@Autowired
	private UsuarioDAO usuarioDAO;
	
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
	public void registraSecretario( Long escolaId, SaveSecretarioRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getFuncionario().getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		Optional<Escola> eop = escolaRepository.findById( escolaId );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
				
		Escola escola = eop.get();
				
		Secretario sec = secretarioBuilder.novoSecretario( escola );
		secretarioBuilder.carregaSecretario( sec, request );		
		secretarioRepository.save( sec );

		usuarioDAO.salvaUsuarioGrupoMaps( sec.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}

	@Transactional		
	public void alteraSecretario( Long secretarioId, SaveSecretarioRequest request ) throws ServiceException {		
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario sec = secOp.get();
		
		String secretarioCpfAtual = sec.getFuncionario().getPessoa().getCpf();		
		String secretarioCpfNovo = request.getFuncionario().getPessoa().getCpf();		
		if ( !secretarioCpfNovo.equalsIgnoreCase( secretarioCpfAtual ) )
			if ( pessoaRepository.buscaPorNome( secretarioCpfNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
				
		usuarioDAO.validaAlteracaoPerfil( sec.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
		
		secretarioBuilder.carregaSecretario( sec, request );		
		secretarioRepository.save( sec );		

		usuarioDAO.salvaUsuarioGrupoMaps( sec.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}
		
	public List<SecretarioResponse> filtraSecretarios( FiltraSecretariosRequest request, FiltroSecretarios filtro ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Secretario> secretarios = filtro.filtra( secretarioRepository, nomeIni );
		
		List<SecretarioResponse> lista = new ArrayList<>();
		for( Secretario sec : secretarios ) {
			SecretarioResponse resp = secretarioBuilder.novoSecretarioResponse();
			secretarioBuilder.carregaSecretarioResponse( resp, sec );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public SecretarioResponse buscaSecretario( Long secretarioId ) throws ServiceException {
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario sec = secOp.get();
		
		SecretarioResponse resp = secretarioBuilder.novoSecretarioResponse();
		secretarioBuilder.carregaSecretarioResponse( resp, sec );		
		return resp;
	}
	
	public void deletaSecretario( Long secretarioId ) throws ServiceException {
		boolean existe = secretarioRepository.existsById( secretarioId );
		if ( !existe )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		secretarioRepository.deleteById( secretarioId ); 
	}
	
}
