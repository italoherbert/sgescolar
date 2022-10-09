package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoMetodoEnumManager;
import sgescolar.enums.tipos.AvaliacaoConceito;
import sgescolar.enums.tipos.AvaliacaoMetodo;
import sgescolar.logica.manager.TurmaManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.AvaliacaoExterna;
import sgescolar.model.AvaliacaoResultado;
import sgescolar.model.Matricula;
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
	private AvaliacaoMetodoEnumManager avaliacaoTipoEnumManager;
	
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
				
				double avSoma = 0;
				double avPesosSoma = 0;
				
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
						
						avSoma += avext.getMedia() * avext.getPeso();
						avPesosSoma += avext.getPeso();						
					}
					
				} 
				
				List<BoletimAvaliacaoResponse> avaliacoesResps = new ArrayList<>();
				
				List<Avaliacao> avaliacoes = td.getAvaliacoes();
				if ( avaliacoes != null ) {				
					for( Avaliacao a : avaliacoes ) {
						if ( !a.isResultadoDisponivel() )
							continue;
						
						AvaliacaoResultado avR = null;
						
						List<AvaliacaoResultado> resultados = a.getResultados();
						if ( resultados != null ) {
							int size = resultados.size();
							for( int i = 0; avR == null && i < size; i++ ) {
								AvaliacaoResultado result = resultados.get( i );								
								Long mid = result.getMatricula().getId();
								if ( mid == matricula.getId() )
									avR = result;								
							}
						}
						
						double peso = a.getPeso();
						double nota = 0;
						String resultado = "";						
						
						AvaliacaoMetodo avMetodo = a.getTurmaDisciplina().getTurma().getSerie().getCurso().getAvaliacaoMetodo();
						if ( avR != null ) {
							switch( avMetodo ) {
								case NUMERICA:
									peso = a.getPeso();
									nota = avR.getNota();
									resultado = conversorUtil.doubleParaString( nota );								
									break;
								case CONCEITUAL:
									peso = 10;
									nota = ( avR.getConceito() == AvaliacaoConceito.PC ? 10 : 0 );											
									resultado = avR.getConceito().name();
									break;
								case DESCRITIVA:
									peso = 10;
									nota = 10;
									resultado = avR.getDescricao();
									break;
							}
						}
						
						BoletimAvaliacaoResponse avaliacaoResp = new BoletimAvaliacaoResponse();
						avaliacaoResp.setDataAvaliacao( conversorUtil.dataParaString( a.getDataAgendamento() ) ); 
						avaliacaoResp.setResultado( resultado );
						avaliacaoResp.setPeso( conversorUtil.doubleParaString( peso ) );
						avaliacaoResp.setAvaliacaoTipo( avaliacaoTipoEnumManager.tipoResponse( avMetodo ) ); 
						avaliacoesResps.add( avaliacaoResp );

						avPesosSoma += peso;
						avSoma += nota;				
					}														
				}
				
				double media = -1;
				if( avPesosSoma > 0 )
					media = ( avSoma / avPesosSoma ) * 10.0d;
				
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
