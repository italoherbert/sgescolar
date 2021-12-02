package sgescolar.builder.util;

import org.springframework.stereotype.Component;

import sgescolar.model.Curso;
import sgescolar.model.Serie;
import sgescolar.model.Turma;

@Component
public class TurmaUtil {

	public String getDescricaoDetalhada( Turma t ) {
		Serie s = t.getSerie();
		Curso c = s.getCurso();
		
		return s.getDescricao() + ", " + t.getDescricao() + " - " + c.getDescricao();
	}
	
}
