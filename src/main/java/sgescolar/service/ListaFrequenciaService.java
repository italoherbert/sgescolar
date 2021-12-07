package sgescolar.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AlunoFrequenciaBuilder;
import sgescolar.builder.GrupoListaAlunoFrequenciaBuilder;
import sgescolar.builder.ListaAlunoFrequenciaBuilder;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.Escola;
import sgescolar.model.HorarioAula;
import sgescolar.model.ListaFrequencia;
import sgescolar.model.Matricula;
import sgescolar.model.request.FiltraListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.model.request.SaveGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoFrequenciaRepository;
import sgescolar.repository.HorarioAulaRepository;
import sgescolar.repository.ListaAlunoFrequenciaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;
import sgescolar.util.ConversorUtil;

@Service
public class ListaFrequenciaService {

	@Autowired
	private GrupoListaAlunoFrequenciaBuilder grupoListaAlunoFrequenciaBuilder;
	
	@Autowired
	private ListaAlunoFrequenciaRepository listaAlunoFrequenciaRepository;
	
	@Autowired
	private AlunoFrequenciaRepository alunoFrequenciaRepository;
	
	@Autowired
	private HorarioAulaRepository aulaRepository;
	
	@Autowired
	private ListaAlunoFrequenciaBuilder listaAlunoFrequenciaBuilder;
		
	@Autowired
	private AlunoFrequenciaBuilder alunoFrequenciaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	@Transactional
	public void salvaLAFs( SaveGrupoListaAlunoFrequenciaRequest grequest, TokenInfos tokenInfos ) throws ServiceException {
		SaveListaAlunoFrequenciaRequest[] listas = grequest.getFrequenciaListas();
		for( SaveListaAlunoFrequenciaRequest lreq : listas )
			this.salvaLAF( lreq, tokenInfos );		
	}
	
	@Transactional
	public void salvaLAF( SaveListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		Long aulaId = conversorUtil.stringParaLong( request.getHorarioAulaId() );
		
		Optional<HorarioAula> aulaOp = aulaRepository.findById( aulaId );
		if ( !aulaOp.isPresent() )
			throw new ServiceException( ServiceErro.AULA_NAO_ENCONTRADA );
		
		HorarioAula aula = aulaOp.get();		
		Escola escola = aula.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );		
		
		Optional<ListaFrequencia> lafOp = listaAlunoFrequenciaRepository.busca( aulaId, dataDia );
		
		ListaFrequencia laf;
		if ( lafOp.isPresent() ) {
			laf = lafOp.get();
		} else {
			laf = listaAlunoFrequenciaBuilder.novoListaAlunoFrequencia( aula );
		}
				
		listaAlunoFrequenciaBuilder.carregaListaAlunoFrequencia( laf, request );
		listaAlunoFrequenciaRepository.save( laf );
		
		List<SaveAlunoFrequenciaRequest> freqs = request.getFrequencias();
		for( SaveAlunoFrequenciaRequest freq : freqs ) {						
			Optional<AlunoFrequencia> afOp = alunoFrequenciaRepository.buscaPorIDs( laf.getId(), freq.getMatriculaId() );
			
			AlunoFrequencia af;
			if ( afOp.isPresent() ) {
				af = afOp.get();
			} else {
				Matricula mat = new Matricula();
				mat.setId( freq.getMatriculaId() ); 
				
				af = alunoFrequenciaBuilder.novoAlunoFrequencia( laf, mat );
			}

			alunoFrequenciaBuilder.carregaAlunoFrequencia( af, freq );
			alunoFrequenciaRepository.save( af );
		}		
	}
	
	public GrupoListaAlunoFrequenciaResponse buscaGrupoLAFs( Long turmaDisciplinaId, FiltraListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
						
		List<ListaFrequencia> listas = listaAlunoFrequenciaRepository.listaPorTDiscEDataDia( turmaDisciplinaId, dataDia );					
			
		int size = listas.size();
		for( int i = 0; i < size; i++ ) {
			try {
				ListaFrequencia laf = listas.get( i );
				
				Escola esc = laf.getHorarioAula().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
				tokenDAO.autorizaPorEscolaOuInstituicao( esc, tokenInfos ); 								
			} catch ( TokenAutorizacaoException e ) {
				listas.remove( i );
				i--;
				size--;
			}
		}
				
		GrupoListaAlunoFrequenciaResponse resp = grupoListaAlunoFrequenciaBuilder.novoGrupoListaAlunoFrequenciaResponse();
		grupoListaAlunoFrequenciaBuilder.carregaGrupoListaAlunoFrequenciaResponse( resp, listas ); 
		return resp;
	}
	
	
	
}
