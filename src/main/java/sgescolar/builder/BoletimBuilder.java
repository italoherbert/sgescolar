package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.manager.TurmaManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.Matricula;
import sgescolar.model.Nota;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.BoletimAvaliacaoResponse;
import sgescolar.model.response.BoletimResponse;
import sgescolar.model.response.BoletimDisciplinaResponse;

@Component
public class BoletimBuilder {

	@Autowired
	private TurmaManager turmaUtil;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public BoletimResponse novoBoletimResponse( Matricula matricula ) {				
		List<BoletimDisciplinaResponse> disciplinasBoletins = new ArrayList<>();
		
		Turma turma = matricula.getTurma();
		List<TurmaDisciplina> tds = turma.getTurmaDisciplinas();
		if ( tds != null ) {
			for( TurmaDisciplina td : tds ) {
				BoletimDisciplinaResponse dbresp = new BoletimDisciplinaResponse();
				dbresp.setDisciplinaDescricao( td.getDisciplina().getDescricao() );
				
				double media = 0;
				List<BoletimAvaliacaoResponse> avaliacoesResps = new ArrayList<>();
				
				List<Avaliacao> avaliacoes = td.getAvaliacoes();
				if ( avaliacoes != null ) {				
					int cont = 0;
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
						
						BoletimAvaliacaoResponse avaliacaoResp = new BoletimAvaliacaoResponse();
						avaliacaoResp.setDataAvaliacao( conversorUtil.dataParaString( a.getDataAgendamento() ) ); 
						avaliacaoResp.setNota( conversorUtil.doubleParaString( nota ) );
						avaliacaoResp.setPeso( conversorUtil.doubleParaString( peso ) );						
						avaliacoesResps.add( avaliacaoResp );
						
						cont++;
					}
					
					if ( cont == 0 )
						media = -1;
					else media /= cont;					
				}
				
				dbresp.setAvaliacoes( avaliacoesResps );
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
