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
import sgescolar.model.request.BuscaSecretariosRequest;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.SecretarioRepository;

@Service
public class SecretarioService {
	
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EscolaRepository escolaRepository;
		
	@Autowired
	private SecretarioBuilder secretarioBuilder;
	
	@Transactional
	public void registraSecretario( Long logadoEID, SaveSecretarioRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorNome( request.getFuncionario().getPessoa().getNome() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		Optional<Escola> eop = escolaRepository.findById( logadoEID );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
				
		Escola escola = eop.get();
		
		Secretario sec = secretarioBuilder.novoSecretario( escola );
		secretarioBuilder.carregaSecretario( sec, request );
		
		secretarioRepository.save( sec );						
	}
	
	public void alteraSecretario( Long secretarioId, SaveSecretarioRequest request ) throws ServiceException {		
		Optional<Secretario> secOp = secretarioRepository.findById( secretarioId );
		if ( !secOp.isPresent() )
			throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
		
		Secretario sec = secOp.get();
		
		String secretarioNomeAtual = sec.getFuncionario().getPessoa().getNome();		
		String secretarioNomeNovo = request.getFuncionario().getPessoa().getNome();		
		if ( !secretarioNomeNovo.equalsIgnoreCase( secretarioNomeAtual ) )
			if ( pessoaRepository.buscaPorNome( secretarioNomeNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
				
		secretarioBuilder.carregaSecretario( sec, request );		
		secretarioRepository.save( sec );		
	}
	
	public List<SecretarioResponse> filtraSecretarios( BuscaSecretariosRequest request ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Secretario> secretarios = secretarioRepository.filtra( nomeIni );
		
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
