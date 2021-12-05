package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.FrequenciaTipoEnumManager;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.response.HorarioAulaResponse;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.model.response.TipoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class GrupoListaAlunoFrequenciaBuilder {
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;
	
	@Autowired
	private HorarioAulaBuilder aulaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	@Autowired
	private FrequenciaTipoEnumManager frequenciaTipoEnumManager;
	
	public void carregaGrupoListaAlunoFrequenciaResponse( GrupoListaAlunoFrequenciaResponse resp, List<ListaAlunoFrequencia> listas ) {				
		if ( !listas.isEmpty() ) {		
			List<MatriculaResponse> matriculas = new ArrayList<>();
			List<HorarioAulaResponse> horarioAulas = new ArrayList<>();
			
			ListaAlunoFrequencia laf0 = listas.get( 0 );
			List<AlunoFrequencia> frequencias0 = laf0.getFrequencias();
			
			int alunosQuant = frequencias0.size();			
			int horarioAulasQuant = listas.size();
			
			TipoResponse[] frequenciaTiposHorarioAula0 = new TipoResponse[ alunosQuant ];
			String[][] estevePresenteMatriz = new String[ horarioAulasQuant ][ alunosQuant ];
			for( int i = 0; i < horarioAulasQuant; i++ ) {
				ListaAlunoFrequencia laf = listas.get( i );
				
				for( int j = 0; j < alunosQuant; j++ ) {
					AlunoFrequencia f = laf.getFrequencias().get( j );
					
					estevePresenteMatriz[ i ][ j ] = conversorUtil.booleanParaString( f.isEstevePresente() );
					
					if ( i == 0 ) {
						frequenciaTiposHorarioAula0[ j ] = frequenciaTipoEnumManager.tipoResponse( f.getFrequenciaTipo() );
						
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
			resp.setFrequenciaTiposHorarioAula0( frequenciaTiposHorarioAula0 );
			resp.setDataDia( conversorUtil.dataParaString( laf0.getDataDia() ) ); 
		}
		
		resp.setTemUmaOuMais( conversorUtil.booleanParaString( !listas.isEmpty() ) ); 		
	}
	
	public GrupoListaAlunoFrequenciaResponse novoGrupoListaAlunoFrequenciaResponse() {
		return new GrupoListaAlunoFrequenciaResponse();
	}
	
}
