package sgescolar.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ListaAlunoFrequenciaBuilder;
import sgescolar.model.Aula;
import sgescolar.model.Escola;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.request.BuscaListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.response.ListaAlunoFrequenciaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AulaRepository;
import sgescolar.repository.ListaAlunoFrequenciaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;
import sgescolar.util.ConversorUtil;

@Service
public class ListaAlunoFrequenciaService {

	@Autowired
	private ListaAlunoFrequenciaRepository listaAlunoFrequenciaRepository;
		
	@Autowired
	private ListaAlunoFrequenciaBuilder listaAlunoFrequenciaBuilder;
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	public void salvaLAF( Long aulaId, SaveListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Aula> aulaOp = aulaRepository.findById( aulaId );
		if ( !aulaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Aula aula = aulaOp.get();
		Escola escola = aula.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		
		Optional<ListaAlunoFrequencia> lafOp = listaAlunoFrequenciaRepository.busca( aulaId, dataDia );
		
		ListaAlunoFrequencia laf;
		if ( lafOp.isPresent() ) {
			laf = lafOp.get();
		} else {
			laf = listaAlunoFrequenciaBuilder.novoListaAlunoFrequencia( aula ); 
		}
		
		listaAlunoFrequenciaBuilder.carregaListaAlunoFrequencia( laf, request );
		listaAlunoFrequenciaRepository.save( laf );
	}
	
	public ListaAlunoFrequenciaResponse buscaLAF( Long aulaId, BuscaListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Aula> aulaOp = aulaRepository.findById( aulaId );
		if ( !aulaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Aula aula = aulaOp.get();
		Escola escola = aula.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		Optional<ListaAlunoFrequencia> lafOp = listaAlunoFrequenciaRepository.busca( aulaId, dataDia );
		if ( !lafOp.isPresent() )
			throw new ServiceException( ServiceErro.LISTA_ALUNO_FREQUENCIA_NAO_ENCONTRADA );
		
		ListaAlunoFrequencia laf = lafOp.get();
		
		ListaAlunoFrequenciaResponse resp = listaAlunoFrequenciaBuilder.novoListaAlunoFrequenciaResponse();
		listaAlunoFrequenciaBuilder.carregaListaAlunoFrequenciaResponse( resp, laf );
		return resp;
	}
	
	public ListaAlunoFrequenciaResponse buscaLAF( Long lafId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ListaAlunoFrequencia> lafOp = listaAlunoFrequenciaRepository.findById( lafId );
		if ( !lafOp.isPresent() )
			throw new ServiceException( ServiceErro.LISTA_ALUNO_FREQUENCIA_NAO_ENCONTRADA );
		
		ListaAlunoFrequencia laf = lafOp.get();
		
		Escola escola = laf.getAula().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		ListaAlunoFrequenciaResponse resp = listaAlunoFrequenciaBuilder.novoListaAlunoFrequenciaResponse();
		listaAlunoFrequenciaBuilder.carregaListaAlunoFrequenciaResponse( resp, laf );
		return resp;
	}
	
	public void deletaLAF( Long lafId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ListaAlunoFrequencia> lafOp = listaAlunoFrequenciaRepository.findById( lafId );
		if ( !lafOp.isPresent() )
			throw new ServiceException( ServiceErro.LISTA_ALUNO_FREQUENCIA_NAO_ENCONTRADA );
		
		ListaAlunoFrequencia laf = lafOp.get();
		
		Escola escola = laf.getAula().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		listaAlunoFrequenciaRepository.deleteById( lafId );
	}
	
}
