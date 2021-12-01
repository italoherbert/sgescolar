package sgescolar.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ListaAlunoFrequenciaBuilder;
import sgescolar.enums.TurnoEnumManager;
import sgescolar.enums.tipos.Turno;
import sgescolar.model.Escola;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.Turma;
import sgescolar.model.request.BuscaListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.response.ListaAlunoFrequenciaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.ListaAlunoFrequenciaRepository;
import sgescolar.repository.TurmaRepository;
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
	private TurmaRepository turmaRepository;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	@Autowired
	private TurnoEnumManager turnoEnumManager;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	public void salvaLAF( Long turmaId, SaveListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = turmaOp.get();
		Escola escola = turma.getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		Turno turno = turnoEnumManager.getEnum( request.getTurno() );
		int numeroAula = conversorUtil.stringParaInteiro( request.getNumeroAula() );
		
		Optional<ListaAlunoFrequencia> lafOp = listaAlunoFrequenciaRepository.busca( turmaId, dataDia, turno, numeroAula );
		
		ListaAlunoFrequencia laf;
		if ( lafOp.isPresent() ) {
			laf = lafOp.get();
		} else {
			laf = listaAlunoFrequenciaBuilder.novoListaAlunoFrequencia( turma ); 
		}
		
		listaAlunoFrequenciaBuilder.carregaListaAlunoFrequencia( laf, request );
		listaAlunoFrequenciaRepository.save( laf );
	}
	
	public ListaAlunoFrequenciaResponse buscaLAF( Long turmaId, BuscaListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = turmaOp.get();
		Escola escola = turma.getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		Turno turno = turnoEnumManager.getEnum( request.getTurno() );
		int numeroAula = conversorUtil.stringParaInteiro( request.getNumeroAula() );
		Optional<ListaAlunoFrequencia> lafOp = listaAlunoFrequenciaRepository.busca( turmaId, dataDia, turno, numeroAula );
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
		
		Escola escola = laf.getTurma().getAnoLetivo().getEscola();		
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
		
		Escola escola = laf.getTurma().getAnoLetivo().getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		listaAlunoFrequenciaRepository.deleteById( lafId );
	}
	
}
