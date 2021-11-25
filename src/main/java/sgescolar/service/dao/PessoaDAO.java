package sgescolar.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Pessoa;
import sgescolar.model.request.SavePessoaRequest;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PessoaRepository;
import sgescolar.service.ServiceException;

@Component
public class PessoaDAO {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public void validaAlteracao( Pessoa p, SavePessoaRequest request ) throws ServiceException {
		String cpf = p.getCpf();
		String cpfNovo = request.getCpf();
		
		if ( !cpf.equalsIgnoreCase( cpfNovo ) )
			if ( pessoaRepository.buscaPorCpf( cpfNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
	}
	
}
