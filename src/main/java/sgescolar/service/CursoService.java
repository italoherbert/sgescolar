package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.CursoBuilder;
import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.enums.tipos.CursoModalidade;
import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.request.FiltraCursosRequest;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.CursoRepository;
import sgescolar.repository.EscolaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class CursoService {
	
	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private EscolaRepository escolaRepository;
	
	@Autowired
	private CursoModalidadeEnumManager cursoModalidadeEnumManager;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private CursoBuilder cursoBuilder;
	
	public void registraCurso( Long escolaId, SaveCursoRequest request, TokenInfos infos ) throws ServiceException {		
		if ( cursoRepository.buscaPorDescricao( escolaId, request.getDescricao() ).isPresent() )
			throw new ServiceException( ServiceErro.CURSO_JA_EXISTE );
		
		Optional<Escola> escolaOp = escolaRepository.findById( escolaId );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola escola = escolaOp.get();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos ); 
		
		Curso c = cursoBuilder.novoCurso( escolaOp.get() );
		cursoBuilder.carregaCurso( c, request );			
		cursoRepository.save( c );
	}
	
	public void atualizaCurso( Long cursoId, SaveCursoRequest request, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola escola = c.getEscola();
		Long escolaId = escola.getId();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		if ( !c.getDescricao().equalsIgnoreCase( request.getDescricao() ) )
			if ( cursoRepository.buscaPorDescricao( escolaId, request.getDescricao() ).isPresent() )
				throw new ServiceException( ServiceErro.CURSO_JA_EXISTE ); 
		
		cursoBuilder.carregaCurso( c, request );		
		cursoRepository.save( c ); 
	}
	
	public List<CursoResponse> filtraCursos( Long escolaId, FiltraCursosRequest request, TokenInfos infos ) throws ServiceException {
		Optional<Escola> escolaOp = escolaRepository.findById( escolaId );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		Escola escola = escolaOp.get();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		CursoModalidade modalidade = cursoModalidadeEnumManager.getEnum( request.getModalidade() );

		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni = "%" + descricaoIni + "%";
		
		List<Curso> cursos = cursoRepository.filtra( escolaId, descricaoIni, modalidade );
		
		List<CursoResponse> lista = new ArrayList<>();
		for( Curso c : cursos ) {
			CursoResponse resp = cursoBuilder.novoCursoResponse();
			cursoBuilder.carregaCursoResponse( resp, c );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<CursoResponse> lista( Long escolaId, TokenInfos infos ) throws ServiceException {		
		Optional<Escola> eop = escolaRepository.findById( escolaId );
		if ( !eop.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Escola escola = eop.get();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		List<Curso> cursos = cursoRepository.lista( escolaId );
		
		List<CursoResponse> lista = new ArrayList<>();
		for( Curso c : cursos ) {
			CursoResponse resp = cursoBuilder.novoCursoResponse();
			cursoBuilder.carregaCursoResponse( resp, c );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public CursoResponse buscaCurso( Long cursoId, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola escola = c.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		CursoResponse resp = cursoBuilder.novoCursoResponse();
		cursoBuilder.carregaCursoResponse( resp, c );
		return resp;
	}
	
	public void removeCurso( Long cursoId, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola escola = c.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos ); 
		
		cursoRepository.deleteById( cursoId );		
	}
			
}
