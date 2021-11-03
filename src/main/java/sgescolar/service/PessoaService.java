package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.model.Pessoa;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
				
	public void validaSeExisteCpf( String cpf ) throws ServiceException {				
		Optional<Pessoa> pessoaOp = pessoaRepository.buscaPorCpf( cpf );
		if ( pessoaOp.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );				
	}
	
}
