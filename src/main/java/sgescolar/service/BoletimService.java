package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.BoletimBuilder;
import sgescolar.model.Escola;
import sgescolar.model.Matricula;
import sgescolar.model.response.BoletimResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.MatriculaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class BoletimService {
				
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private BoletimBuilder boletimBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	public BoletimResponse geraBoletim( Long alunoId, Long anoLetivoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Matricula> matriculaOp = matriculaRepository.buscaPorAnoLetivo( alunoId, anoLetivoId );
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Matricula matricula = matriculaOp.get();
		
		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		return boletimBuilder.novoBoletimResponse( matricula );
	}
	
}
