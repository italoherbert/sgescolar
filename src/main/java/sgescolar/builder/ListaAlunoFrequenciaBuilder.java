package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.TurnoEnumManager;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.Aula;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.Turma;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.response.AlunoFrequenciaResponse;
import sgescolar.model.response.ListaAlunoFrequenciaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class ListaAlunoFrequenciaBuilder {

	@Autowired
	private AlunoFrequenciaBuilder alunoFrequenciaBuilder;
	
	@Autowired
	private TurnoEnumManager turnoEnumManager;
			
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaListaAlunoFrequencia( ListaAlunoFrequencia laf, SaveListaAlunoFrequenciaRequest request ) {
		laf.setDataDia( conversorUtil.stringParaData( request.getDataDia() ) );		
	}
	
	public void carregaListaAlunoFrequenciaResponse( ListaAlunoFrequenciaResponse resp, ListaAlunoFrequencia laf ) {
		Turma turma = laf.getAula().getTurmaDisciplina().getTurma();
		
		resp.setId( laf.getId() );
		resp.setDataDia( conversorUtil.dataParaString( laf.getDataDia() ) ); 
		resp.setTurno( turnoEnumManager.tipoResponse( turma.getTurno() ) );
		
		List<AlunoFrequenciaResponse> frequenciaResponses = new ArrayList<>();
		List<AlunoFrequencia> frequencias = laf.getFrequencias();
		for( AlunoFrequencia f : frequencias ) {
			AlunoFrequenciaResponse fresp = alunoFrequenciaBuilder.novoAlunoFrequenciaResponse();
			alunoFrequenciaBuilder.carregaAlunoFrequenciaResponse( fresp, f ); 
			frequenciaResponses.add( fresp );
		}
		resp.setFrequencias( frequenciaResponses ); 
	}
	
	public ListaAlunoFrequencia novoListaAlunoFrequencia( Aula aula ) {
		ListaAlunoFrequencia laf = new ListaAlunoFrequencia();
		laf.setAula( aula );
		return laf;
	}
	
	public ListaAlunoFrequenciaResponse novoListaAlunoFrequenciaResponse() {
		return new ListaAlunoFrequenciaResponse();
	}
	
}


