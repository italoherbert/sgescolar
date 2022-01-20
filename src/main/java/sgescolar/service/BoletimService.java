package sgescolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.BoletimBuilder;
import sgescolar.model.Escola;
import sgescolar.model.Matricula;
import sgescolar.model.response.BoletimResponse;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.AnoAtualDAO;
import sgescolar.service.dao.TokenDAO;

@Service
public class BoletimService {
					
	@Autowired
	private BoletimBuilder boletimBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private AnoAtualDAO anoAtualDAO;
	
	public BoletimResponse geraBoletim( Long alunoId, TokenInfos tokenInfos ) throws ServiceException {
		Matricula matricula = anoAtualDAO.buscaMatriculaPorAnoAtual( alunoId );		
		
		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		return boletimBuilder.novoBoletimResponse( matricula );
	}
	
}
