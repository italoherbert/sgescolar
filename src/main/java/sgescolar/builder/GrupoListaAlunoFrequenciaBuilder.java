package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.FrequenciaModalidadeEnumManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.ListaFrequencia;
import sgescolar.model.response.HorarioAulaResponse;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.model.response.TipoResponse;

@Component
public class GrupoListaAlunoFrequenciaBuilder {
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;
	
	@Autowired
	private HorarioAulaBuilder aulaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	@Autowired
	private FrequenciaModalidadeEnumManager frequenciaModalidadeEnumManager;
	
	public void carregaGrupoListaAlunoFrequenciaResponse( GrupoListaAlunoFrequenciaResponse resp, List<ListaFrequencia> listas ) {
		if ( !listas.isEmpty() ) {		
			List<MatriculaResponse> matriculas = new ArrayList<>();
			List<HorarioAulaResponse> horarioAulas = new ArrayList<>();
			
			ListaFrequencia laf0 = listas.get( 0 );
			List<AlunoFrequencia> frequencias0 = laf0.getFrequencias();
			
			int alunosQuant = frequencias0.size();			
			int horarioAulasQuant = listas.size();
			
			TipoResponse[] horarioAula0Modalidades = new TipoResponse[ alunosQuant ];
			String[][] estevePresenteMatriz = new String[ horarioAulasQuant ][ alunosQuant ];
			for( int i = 0; i < horarioAulasQuant; i++ ) {
				ListaFrequencia laf = listas.get( i );
				
				for( int j = 0; j < alunosQuant; j++ ) {
					AlunoFrequencia f = laf.getFrequencias().get( j );
					
					estevePresenteMatriz[ i ][ j ] = conversorUtil.booleanParaString( f.isEstevePresente() );
					
					if ( i == 0 ) {
						horarioAula0Modalidades[ j ] = frequenciaModalidadeEnumManager.tipoResponse( f.getModalidade() );
						
						MatriculaResponse mresp = matriculaBuilder.novoMatriculaResponse();
						matriculaBuilder.carregaMatriculaResponse( mresp, f.getMatricula() );
						matriculas.add( mresp );
					}					
				}
				
				HorarioAulaResponse aresp = aulaBuilder.novoAulaResponse();
				aulaBuilder.carregaAulaResponse( aresp, laf.getHorarioAula() );
				horarioAulas.add( aresp );
			}
			
			resp.setHorarioAulasQuant( conversorUtil.inteiroParaString( horarioAulasQuant ) ); 
			resp.setMatriculasQuant( conversorUtil.inteiroParaString( alunosQuant ) );

			resp.setMatriculas( matriculas );
			resp.setHorarioAulas( horarioAulas );
			resp.setEstevePresenteMatriz( estevePresenteMatriz ); 
			resp.setHorarioAula0Modalidades( horarioAula0Modalidades );
			resp.setDataDia( conversorUtil.dataParaString( laf0.getDataDia() ) ); 
		}
		
		resp.setTemUmaOuMais( conversorUtil.booleanParaString( !listas.isEmpty() ) ); 		
	}
	
	public GrupoListaAlunoFrequenciaResponse novoGrupoListaAlunoFrequenciaResponse() {
		return new GrupoListaAlunoFrequenciaResponse();
	}
	
}
