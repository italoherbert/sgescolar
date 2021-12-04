package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.FrequenciaTipoEnumManager;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.response.AulaResponse;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.model.response.TipoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class GrupoListaAlunoFrequenciaBuilder {
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;
	
	@Autowired
	private AulaBuilder aulaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	@Autowired
	private FrequenciaTipoEnumManager frequenciaTipoEnumManager;
	
	public void carregaGrupoListaAlunoFrequenciaResponse( GrupoListaAlunoFrequenciaResponse resp, List<ListaAlunoFrequencia> listas ) {				
		if ( !listas.isEmpty() ) {		
			List<MatriculaResponse> matriculas = new ArrayList<>();
			List<AulaResponse> aulas = new ArrayList<>();
			
			ListaAlunoFrequencia laf0 = listas.get( 0 );
			List<AlunoFrequencia> frequencias0 = laf0.getFrequencias();
			
			int alunosQuant = frequencias0.size();			
			int aulasQuant = listas.size();
			
			TipoResponse[] frequenciaTiposAula0 = new TipoResponse[ alunosQuant ];
			String[][] estevePresenteMatriz = new String[ aulasQuant ][ alunosQuant ];
			for( int i = 0; i < aulasQuant; i++ ) {
				ListaAlunoFrequencia laf = listas.get( i );
				
				for( int j = 0; j < alunosQuant; j++ ) {
					AlunoFrequencia f = laf.getFrequencias().get( j );
					
					estevePresenteMatriz[ i ][ j ] = conversorUtil.booleanParaString( f.isEstevePresente() );
					
					if ( i == 0 ) {
						frequenciaTiposAula0[ j ] = frequenciaTipoEnumManager.tipoResponse( f.getFrequenciaTipo() );
						
						MatriculaResponse mresp = matriculaBuilder.novoMatriculaResponse();
						matriculaBuilder.carregaMatriculaResponse( mresp, f.getMatricula() );
						matriculas.add( mresp );
					}					
				}
				
				AulaResponse aresp = aulaBuilder.novoAulaResponse();
				aulaBuilder.carregaAulaResponse( aresp, laf.getAula() );
				aulas.add( aresp );
			}
			
			resp.setAulasQuant( conversorUtil.inteiroParaString( aulasQuant ) ); 
			resp.setMatriculasQuant( conversorUtil.inteiroParaString( alunosQuant ) );

			resp.setMatriculas( matriculas );
			resp.setAulas( aulas );
			resp.setEstevePresenteMatriz( estevePresenteMatriz ); 
			resp.setFrequenciaTiposAula0( frequenciaTiposAula0 );
			resp.setDataDia( conversorUtil.dataParaString( laf0.getDataDia() ) ); 
		}
		
		resp.setTemUmaOuMais( conversorUtil.booleanParaString( !listas.isEmpty() ) ); 		
	}
	
	public GrupoListaAlunoFrequenciaResponse novoGrupoListaAlunoFrequenciaResponse() {
		return new GrupoListaAlunoFrequenciaResponse();
	}
	
}
