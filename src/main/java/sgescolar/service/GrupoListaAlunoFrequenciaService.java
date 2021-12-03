package sgescolar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ListaAlunoFrequenciaBuilder;
import sgescolar.model.Aula;
import sgescolar.model.Escola;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.request.BuscaGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.model.response.ListaAlunoFrequenciaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AulaRepository;
import sgescolar.repository.ListaAlunoFrequenciaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;
import sgescolar.util.ConversorUtil;

@Service
public class GrupoListaAlunoFrequenciaService {

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
	
	@Transactional
	public void salvaLAFs( SaveGrupoListaAlunoFrequenciaRequest grequest, TokenInfos tokenInfos ) throws ServiceException {
		SaveListaAlunoFrequenciaRequest[] listas = grequest.getListas();
		for( SaveListaAlunoFrequenciaRequest lreq : listas )
			this.salvaLAF( lreq, tokenInfos );		
	}
	
	@Transactional
	public void salvaLAF( SaveListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		Long aulaId = conversorUtil.stringParaLong( request.getAulaId() );
		
		Optional<Aula> aulaOp = aulaRepository.findById( aulaId );
		if ( !aulaOp.isPresent() )
			throw new ServiceException( ServiceErro.AULA_NAO_ENCONTRADA );
		
		Aula aula = aulaOp.get();		
		Escola escola = aula.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );		
		
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
	
	public GrupoListaAlunoFrequenciaResponse buscaGrupoLAFs( BuscaGrupoListaAlunoFrequenciaRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		return this.buscaGrupoLAFs( dataDia, tokenInfos );
	}
		
	public GrupoListaAlunoFrequenciaResponse buscaGrupoLAFs( Date dataDia, TokenInfos tokenInfos ) throws ServiceException {		
		List<ListaAlunoFrequenciaResponse> lafResps = new ArrayList<>();
		
		List<ListaAlunoFrequencia> listas = listaAlunoFrequenciaRepository.listaPorData( dataDia );
		for( ListaAlunoFrequencia laf : listas ) {
			try {
				Escola esc = laf.getAula().getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
				tokenDAO.autorizaPorEscolaOuInstituicao( esc, tokenInfos ); 
				
				ListaAlunoFrequenciaResponse lafResp = listaAlunoFrequenciaBuilder.novoListaAlunoFrequenciaResponse();
				listaAlunoFrequenciaBuilder.carregaListaAlunoFrequenciaResponse( lafResp, laf ); 
				lafResps.add( lafResp );
			} catch ( TokenAutorizacaoException e ) {
				
			}
		}
		
		GrupoListaAlunoFrequenciaResponse resp = new GrupoListaAlunoFrequenciaResponse();
		resp.setFrequenciaListas( lafResps );
		return resp;
	}
		
}
