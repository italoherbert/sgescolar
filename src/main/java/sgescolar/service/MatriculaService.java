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
import sgescolar.model.response.MatriculaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.MatriculaRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;

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
		
		int ano = turma.getAnoLetivo().getAno();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Optional<Matricula> matriculaOp = matriculaRepository.buscaPorAnoLetivo( alunoId, ano );
		if ( matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_JA_EXISTE );
		
		Matricula matricula = matriculaBuilder.novoMatricula( aluno, turma );
		matriculaBuilder.carregaMatricula( matricula );
		
		matriculaRepository.save( matricula );
	}
	
	public List<MatriculaResponse> listaMatriculasPorAlunoID( Long alunoId, TokenInfos tokenInfos ) throws ServiceException {
		if ( !alunoRepository.existsById( alunoId ) )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		List<MatriculaResponse> lista = new ArrayList<>();

		List<Matricula> matriculas = matriculaRepository.listaMatriculasPorAlunoID( alunoId );
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
