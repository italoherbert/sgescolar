package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.TurmaManager;
import sgescolar.model.Avaliacao;
import sgescolar.model.Matricula;
import sgescolar.model.Nota;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.BoletimResponse;
import sgescolar.model.response.DisciplinaBoletimResponse;
import sgescolar.util.ConversorUtil;

@Component
public class BoletimBuilder {

	@Autowired
	private TurmaManager turmaUtil;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public BoletimResponse novoBoletimResponse( Matricula matricula ) {				
		List<DisciplinaBoletimResponse> disciplinasBoletins = new ArrayList<>();
		
		Turma turma = matricula.getTurma();
		List<TurmaDisciplina> tds = turma.getTurmaDisciplinas();
		if ( tds != null ) {
			for( TurmaDisciplina td : tds ) {
				DisciplinaBoletimResponse dbresp = new DisciplinaBoletimResponse();
				dbresp.setDisciplinaDescricao( td.getDisciplina().getDescricao() );
				
				double media = 0;
				List<String> notas = new ArrayList<>();
				List<String> pesos = new ArrayList<>();
				List<Avaliacao> avaliacoes = td.getAvaliacoes();
				if ( avaliacoes != null ) {					
					for( Avaliacao a : avaliacoes ) {
						if ( !a.isNotasDisponiveis() )
							continue;
						
						double peso = a.getPeso();
						double nota = 0;
						
						List<Nota> nts = a.getNotas();
						if ( nts != null ) {
							int size = a.getNotas().size();
							boolean achou = false;
							for( int i = 0; !achou && i < size; i++ ) {
								Nota n = nts.get( i );
								Long mid = n.getMatricula().getId();
								if ( mid == matricula.getId() ) {
									nota = n.getNota();
									achou = true;
								}
							}
						}
						
						media += peso * nota;
						
						notas.add( conversorUtil.doubleParaString( nota ) );
						pesos.add( conversorUtil.doubleParaString( peso ) );
					}
					
					media /= avaliacoes.size();					
				}
				dbresp.setNotas( notas );
				dbresp.setPesos( pesos );
				dbresp.setMedia( conversorUtil.doubleParaString( media ) );								
				disciplinasBoletins.add( dbresp );
			}
		}
		
		BoletimResponse boletim = new BoletimResponse();
		boletim.setTurmaDescricaoDetalhada( turmaUtil.getDescricaoDetalhada( turma ) );
		boletim.setDisciplinasBoletins( disciplinasBoletins );
		return boletim;
	}
	
}
