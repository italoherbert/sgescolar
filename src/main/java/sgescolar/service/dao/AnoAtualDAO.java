package sgescolar.service.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Matricula;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.MatriculaRepository;
import sgescolar.repository.ProfessorAlocacaoRepository;
import sgescolar.service.ServiceException;

@Component
public class AnoAtualDAO {

	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private ProfessorAlocacaoRepository professorAlocacaoRepository;
	
	public Matricula buscaMatriculaPorAnoAtual( Long alunoId ) throws ServiceException {		
		int anoAtual = this.getAnoAtual();
		
		Optional<Matricula> matriculaOp = matriculaRepository.buscaPorAno( alunoId, anoAtual ); 
				
		if ( !matriculaOp.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		return matriculaOp.get();		
	}
	
	public List<ProfessorAlocacao> buscaProfessorAlocacoesPorAno( Long professorId ) {
		int anoAtual = this.getAnoAtual();		
		return professorAlocacaoRepository.buscaPorAno( professorId, anoAtual );
	}
		
	private int getAnoAtual() {
		Calendar c = Calendar.getInstance();
		return c.get( Calendar.YEAR );
	}
	
}
