package sgescolar.builder;

import java.util.Date;

import org.springframework.stereotype.Component;

import sgescolar.model.Professor;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.TurmaDisciplina;

@Component
public class ProfessorAlocacaoBuilder {
		
	public ProfessorAlocacao novoEInicializaProfessorAlocacao( TurmaDisciplina td, Professor p ) {
		ProfessorAlocacao pa = new ProfessorAlocacao();
		pa.setTurmaDisciplina( td );
		pa.setProfessor( p );
		
		pa.setAtivo( true );
		pa.setDataInicio( new Date() );
		pa.setDataFim( null );
		return pa;
	}
	
}
