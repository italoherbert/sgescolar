package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.AlunoFrequencia;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.response.AulaResponse;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.model.response.ListaAlunoFrequenciaResponse;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class GrupoListaAlunoFrequenciaBuilder {

	@Autowired
	private ListaAlunoFrequenciaBuilder listaAlunoFrequenciaBuilder;
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;
	
	@Autowired
	private AulaBuilder aulaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaGrupoListaAlunoFrequenciaResponse( GrupoListaAlunoFrequenciaResponse resp, List<ListaAlunoFrequencia> listas ) {
		if ( !listas.isEmpty() ) {		
			List<MatriculaResponse> matriculas = new ArrayList<>();
			List<AulaResponse> aulas = new ArrayList<>();
			
			List<ListaAlunoFrequenciaResponse> lafResps = new ArrayList<>();		
			for( ListaAlunoFrequencia laf : listas ) {			
				ListaAlunoFrequenciaResponse lafResp = listaAlunoFrequenciaBuilder.novoListaAlunoFrequenciaResponse();
				listaAlunoFrequenciaBuilder.carregaListaAlunoFrequenciaResponse( lafResp, laf ); 
				lafResps.add( lafResp );			
				
				AulaResponse aresp = aulaBuilder.novoAulaResponse();
				aulaBuilder.carregaAulaResponse( aresp, laf.getAula() );
				aulas.add( aresp );
			}
						
			ListaAlunoFrequencia laf = listas.get( 0 );
			List<AlunoFrequencia> frequencias = laf.getFrequencias();
			for( AlunoFrequencia f : frequencias ) {
				MatriculaResponse mresp = matriculaBuilder.novoMatriculaResponse();
				matriculaBuilder.carregaMatriculaResponse( mresp, f.getMatricula() );
				matriculas.add( mresp );
			}
			
			resp.setMatriculas( matriculas );
			resp.setAulas( aulas );
			resp.setFrequenciaListas( lafResps );
		}
		
		resp.setTemUmaOuMais( conversorUtil.booleanParaString( !listas.isEmpty() ) );
	}
	
	public GrupoListaAlunoFrequenciaResponse novoGrupoListaAlunoFrequenciaResponse() {
		return new GrupoListaAlunoFrequenciaResponse();
	}
	
}
