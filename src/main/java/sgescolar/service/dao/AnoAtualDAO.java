package sgescolar.service.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.AvaliacaoExterna;
import sgescolar.model.Matricula;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.TurmaDisciplina;
import sgescolar.repository.AvaliacaoExternaRepository;
import sgescolar.repository.MatriculaRepository;
import sgescolar.repository.ProfessorAlocacaoRepository;
import sgescolar.repository.TurmaDisciplinaRepository;

@Component
public class AnoAtualDAO {

	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private AvaliacaoExternaRepository avaliacaoExternaRepository;
	
	@Autowired
	private ProfessorAlocacaoRepository professorAlocacaoRepository;
	
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	public Optional<Matricula> buscaMatriculaPorAnoAtual( Long alunoId ) {		
		int anoAtual = this.getAnoAtual();		
		return matriculaRepository.buscaPorAno( alunoId, anoAtual ); 					
	}
	
	public Optional<AvaliacaoExterna> buscaAvaliacaoExternaPorAnoAtual( Long alunoId, Long turmaDisciplinaId ) {
		int anoAtual = this.getAnoAtual();
		return avaliacaoExternaRepository.buscaAvaliacaoExternaAnoAtual( alunoId, turmaDisciplinaId, anoAtual );		
	}
	
	public List<ProfessorAlocacao> buscaProfessorAlocacoesPorAnoAtual( Long professorId ) {
		int anoAtual = this.getAnoAtual();		
		return professorAlocacaoRepository.buscaPorAno( professorId, anoAtual );
	}
	
	public List<TurmaDisciplina> listaTurmaDisciplinasPorProfessorPorAnoAtual( Long professorId ) {
		int anoAtual = this.getAnoAtual();		
		return turmaDisciplinaRepository.listaPorProfessor( professorId, anoAtual );
	}
	
	public List<TurmaDisciplina> listaTurmaDisciplinasPorAlunoPorAnoAtual( Long aluno ) {
		int anoAtual = this.getAnoAtual();		
		return turmaDisciplinaRepository.listaPorAluno( aluno, anoAtual );
	}
	
	public List<TurmaDisciplina> listaTurmaDisciplinasPorTurmaEProfessorPorAnoAtual( Long turmaId, Long professorId ) {
		int anoAtual = this.getAnoAtual();		
		return turmaDisciplinaRepository.listaPorTurmaEProfessor( turmaId, professorId, anoAtual );
	}
	
	private int getAnoAtual() {
		Calendar c = Calendar.getInstance();
		return c.get( Calendar.YEAR );
	}
	
}
