package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.manager.TurmaManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.AvaliacaoExterna;
import sgescolar.model.Matricula;
import sgescolar.model.Nota;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.AvaliacaoExternaResponse;
import sgescolar.model.response.BoletimAvaliacaoResponse;
import sgescolar.model.response.BoletimDisciplinaResponse;
import sgescolar.model.response.BoletimResponse;

@Component
public class BoletimBuilder {

	@Autowired
	private TurmaManager turmaUtil;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public BoletimResponse novoBoletimResponse( Matricula matricula ) {				
		List<BoletimDisciplinaResponse> disciplinasBoletins = new ArrayList<>();
				
		List<AvaliacaoExterna> avaliacoesExternas = matricula.getAvaliacoesExternas();		
		int avextSize = avaliacoesExternas.size();
		
		Turma turma = matricula.getTurma();
		List<TurmaDisciplina> tds = turma.getTurmaDisciplinas();
		if ( tds != null ) {
			for( TurmaDisciplina td : tds ) {
				BoletimDisciplinaResponse dbresp = new BoletimDisciplinaResponse();
				dbresp.setDisciplinaDescricao( td.getDisciplina().getDescricao() );
				
				double media = 0;
				double pesosSoma = 0;
				
				boolean temAvaliacaoExterna = false;
				if ( !avaliacoesExternas.isEmpty() ) {
					AvaliacaoExterna avext = null;					
					for( int i = 0; avext == null && i < avextSize; i++ ) {
						AvaliacaoExterna avext2 = avaliacoesExternas.get( i ); 
						Long tdid2 = avext2.getTurmaDisciplina().getId();
						if ( td.getId() == tdid2 )
							avext = avext2;
					}
					
					if ( avext != null ) {						
						AvaliacaoExternaResponse avextResp = new AvaliacaoExternaResponse();
						avextResp.setMedia( conversorUtil.doubleParaString( avext.getMedia() ) );
						avextResp.setPeso( conversorUtil.doubleParaString( avext.getPeso() ) );
						
						dbresp.setAvaliacaoExterna( avextResp );
						
						temAvaliacaoExterna = true;
						
						media += avext.getMedia() * avext.getPeso();
						pesosSoma += avext.getPeso();
					}
					
				} 
				
				List<BoletimAvaliacaoResponse> avaliacoesResps = new ArrayList<>();
				
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
												
						BoletimAvaliacaoResponse avaliacaoResp = new BoletimAvaliacaoResponse();
						avaliacaoResp.setDataAvaliacao( conversorUtil.dataParaString( a.getDataAgendamento() ) ); 
						avaliacaoResp.setNota( conversorUtil.doubleParaString( nota ) );
						avaliacaoResp.setPeso( conversorUtil.doubleParaString( peso ) );						
						avaliacoesResps.add( avaliacaoResp );
						
						media += peso * nota;
						pesosSoma += peso;
					}
					
					if ( pesosSoma == 0 )
						media = -1;
					else media /= pesosSoma;					
				}
				
				dbresp.setAvaliacoes( avaliacoesResps );
				dbresp.setTemAvaliacaoExterna( conversorUtil.booleanParaString( temAvaliacaoExterna ) ); 
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
