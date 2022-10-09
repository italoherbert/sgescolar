package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PlanejamentoAnexoBuilder;
import sgescolar.model.Escola;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.response.FilePlanejamentoAnexoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PlanejamentoAnexoRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class PlanejamentoAnexoService {

	@Autowired
	private PlanejamentoAnexoRepository planejamentoAnexoRepository;
	
	@Autowired
	private PlanejamentoAnexoBuilder planejamentoAnexoBuilder;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	public FilePlanejamentoAnexoResponse getAnexoFile( Long planAnexoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<PlanejamentoAnexo> anexoOp = planejamentoAnexoRepository.findById( planAnexoId );
		if ( !anexoOp.isPresent() )
			throw new ServiceException( ServiceErro.PLANEJAMENTO_ANEXO_NAO_ENCONTRADO );
		
		PlanejamentoAnexo anexo = anexoOp.get();
		
		Escola escola = anexo.getPlanejamento().getProfessorAlocacao().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		FilePlanejamentoAnexoResponse resp = planejamentoAnexoBuilder.novoFilePlanejamentoAnexoResponse();
		planejamentoAnexoBuilder.carregaFilePlanejamentoAnexoResponse( resp, anexo );		
		return resp;
	}
	
	public void deleteAnexo( Long planAnexoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<PlanejamentoAnexo> anexoOp = planejamentoAnexoRepository.findById( planAnexoId );
		if ( !anexoOp.isPresent() )
			throw new ServiceException( ServiceErro.PLANEJAMENTO_ANEXO_NAO_ENCONTRADO );
		
		PlanejamentoAnexo anexo = anexoOp.get();
		
		ProfessorAlocacao profAloc = anexo.getPlanejamento().getProfessorAlocacao();
		
		Long uid = profAloc.getProfessor().getFuncionario().getUsuario().getId();		
		if ( tokenInfos.getLogadoUID() != uid )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
				
		Escola escola = profAloc.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		planejamentoAnexoRepository.deleteById( planAnexoId ); 
	}
	
}
