package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.MatriculaBuilder;
import sgescolar.model.Aluno;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Escola;
import sgescolar.model.Matricula;
import sgescolar.model.Turma;
import sgescolar.model.request.filtro.FiltraMatriculaRequest;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.MatriculaRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;
import sgescolar.service.filtro.MatriculasFiltro;

@Service
public class MatriculaService {

	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	public void registraMatricula( Long alunoId, Long turmaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Aluno> alunoOp = alunoRepository.findById( alunoId );
		if ( !alunoOp.isPresent() )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Aluno aluno = alunoOp.get();
		Turma turma = turmaOp.get();
		AnoLetivo al = turma.getAnoLetivo();
		Escola escola = al.getEscola();
		Long alid = al.getId();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Optional<Matricula> matriculaOp = matriculaRepository.buscaPorAnoLetivo( alunoId, alid );
		if ( matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_JA_EXISTE );
		
		Matricula matricula = matriculaBuilder.novoMatricula( aluno, turma );
		matriculaBuilder.carregaMatricula( matricula );
		
		matriculaRepository.save( matricula );
	}
	
	public void encerraMatricula( Long matriculaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Matricula> matriculaOp = matriculaRepository.findById( matriculaId );
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Matricula matricula = matriculaOp.get();
		if ( matricula.isEncerrada() )
			throw new ServiceException( ServiceErro.MATRICULA_JA_ENCERRADA );

		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();			
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		matriculaBuilder.carregaMatriculaEncerramento( matricula );
		matriculaRepository.save( matricula );
	}
	
	public void reabreMatricula( Long matriculaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Matricula> matriculaOp = matriculaRepository.findById( matriculaId );
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Matricula matricula = matriculaOp.get();
		if ( !matricula.isEncerrada() )
			throw new ServiceException( ServiceErro.MATRICULA_JA_ABERTA );

		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();			
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		matriculaBuilder.carregaMatriculaReabertura( matricula );
		matriculaRepository.save( matricula );
	}
	
	public List<MatriculaResponse> filtra( Long turmaId, FiltraMatriculaRequest request, TokenInfos tokenInfos, MatriculasFiltro filtro ) throws ServiceException {
		if ( !turmaRepository.existsById( turmaId ) )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		String nomeini = request.getNomeIni();
		if ( nomeini.equals( "*" ) )
			nomeini = "";
		nomeini += "%";
		
		List<MatriculaResponse> lista = new ArrayList<>();

		List<Matricula> matriculas = filtro.filtra( matriculaRepository, turmaId, nomeini );
		for( Matricula m : matriculas ) {
			try {
				Escola escola = m.getTurma().getAnoLetivo().getEscola();			
				tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos);
				
				MatriculaResponse resp = matriculaBuilder.novoMatriculaResponse();
				matriculaBuilder.carregaMatriculaResponse( resp, m ); 
				lista.add( resp );
			} catch ( TokenAutorizacaoException ex ) {
				
			}
		}
		return lista;
	}
			
	public MatriculaResponse buscaMatriculaPorNumero( String numero, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Matricula> matriculaOp = matriculaRepository.buscaPorNumero( numero );
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
						
		Matricula matricula = matriculaOp.get();
		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		MatriculaResponse resp = matriculaBuilder.novoMatriculaResponse();
		matriculaBuilder.carregaMatriculaResponse( resp, matricula );
		return resp;
	}
	
	public MatriculaResponse buscaMatricula( Long matriculaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Matricula> matriculaOp = matriculaRepository.findById( matriculaId );
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Matricula matricula = matriculaOp.get();
		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		MatriculaResponse resp = matriculaBuilder.novoMatriculaResponse();
		matriculaBuilder.carregaMatriculaResponse( resp, matricula );
		return resp;
	}
	
	public void deletaMatricula( Long matriculaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Matricula> matriculaOp = matriculaRepository.findById( matriculaId );
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Matricula matricula = matriculaOp.get();
		Escola escola = matricula.getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		matriculaRepository.deleteById( matriculaId ); 
	}
	
}
