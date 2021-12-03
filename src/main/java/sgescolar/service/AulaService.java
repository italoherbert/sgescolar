package sgescolar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AulaBuilder;
import sgescolar.model.Aula;
import sgescolar.model.Escola;
import sgescolar.model.request.FiltraAulasRequest;
import sgescolar.model.response.AulaResponse;
import sgescolar.repository.AulaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;
import sgescolar.util.ConversorUtil;
import sgescolar.util.DataUtil;

@Service
public class AulaService {

	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private AulaBuilder aulaBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	@Autowired
	private DataUtil dataUtil;
	
	public List<AulaResponse> filtraPorSemanaDia( Long turmaDisciplinaId, FiltraAulasRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		
		int semanaDia = dataUtil.getSemanaDia( dataDia );

		return this.filtraPorTDisESemanaDia( turmaDisciplinaId, semanaDia, tokenInfos ); 
	}
	
	public List<AulaResponse> filtraPorTDisESemanaDia( Long turmaDisciplinaId, int semanaDia, TokenInfos tokenInfos ) throws ServiceException {
		List<AulaResponse> responses = new ArrayList<>();
		
		List<Aula> aulas = aulaRepository.filtraAulasPorTDisESemanaDia( turmaDisciplinaId, semanaDia );
		for( Aula a : aulas ) {
			Escola escola = a.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
			
			tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
			
			AulaResponse resp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( resp, a ); 
			responses.add( resp );
		}
		return responses;
	}
	
}
