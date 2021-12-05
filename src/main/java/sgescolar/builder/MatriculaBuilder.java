package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Aluno;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Matricula;
import sgescolar.model.Serie;
import sgescolar.model.Turma;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class MatriculaBuilder {

	@Autowired
	private TurmaBuilder turmaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaMatricula( Matricula matricula ) {
		Turma t = matricula.getTurma();
		Aluno a = matricula.getAluno();
		Serie s = t.getSerie();
		AnoLetivo al = t.getAnoLetivo();
				
		matricula.setNumero( "" + al.getAno() + a.getId() + t.getId() + s.getId() );
	}
	
	public void carregaMatriculaResponse( MatriculaResponse resp, Matricula m ) {
		resp.setId( m.getId() );
		
		Aluno a = m.getAluno();
		resp.setAlunoId( a.getId() );
		resp.setAlunoNome( a.getPessoa().getNome() );
		
		AnoLetivo al = m.getTurma().getAnoLetivo();
		resp.setAnoLetivoAno( conversorUtil.inteiroParaString( al.getAno() ) ); 
		
		resp.setNumero( m.getNumero() );
		
		turmaBuilder.carregaTurmaResponse( resp.getTurma(), m.getTurma() );
	}
	
	public Matricula novoMatricula( Aluno aluno, Turma turma ) {
		Matricula matricula = new Matricula();
		matricula.setAluno( aluno );
		matricula.setTurma( turma );
		return matricula;
	}
	
	public MatriculaResponse novoMatriculaResponse() {
		MatriculaResponse resp = new MatriculaResponse();
		resp.setTurma( turmaBuilder.novoTurmaResponse() );
		return resp;
	}
	
}
