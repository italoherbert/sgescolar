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
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.FiltraSecretariosRequest;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.SecretarioRepository;
import sgescolar.repository.UsuarioGrupoRepository;
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
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	@Autowired
	private SecretarioBuilder secretarioBuilder;
		
	public void verificaSeDono( Long logadoUID, Long secretarioId ) throws ServiceException {
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		boolean ehDono = secretarioRepository.verificaSeDono( logadoUID );
		if ( !ehDono )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
	
	@Transactional
	public void registraSecretario( Long logadoEID, SaveSecretarioRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getFuncionario().getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		Optional<Escola> eop = escolaRepository.findById( logadoEID );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
				
		Escola escola = eop.get();
		
		Optional<UsuarioGrupo> ugOp = usuarioGrupoRepository.buscaPorPerfil( request.getFuncionario().getUsuario().getPerfil() );	
		if ( !ugOp.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
		
		UsuarioGrupo ugrupo = ugOp.get();
		
		Secretario sec = secretarioBuilder.novoSecretario( escola, ugrupo );
		secretarioBuilder.carregaSecretario( sec, request );
		
		secretarioRepository.save( sec );						
	}
	
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
				
		secretarioBuilder.carregaSecretario( sec, request );		
		secretarioRepository.save( sec );		
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
