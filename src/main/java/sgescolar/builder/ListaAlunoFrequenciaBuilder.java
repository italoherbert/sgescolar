package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.TurnoEnumManager;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.Aula;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.Matricula;
import sgescolar.model.Turma;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
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
	
	public void carregaListaAlunoFrequencia( ListaAlunoFrequencia dla, SaveListaAlunoFrequenciaRequest request ) {
		dla.setDataDia( conversorUtil.stringParaData( request.getDataDia() ) );
		List<AlunoFrequencia> frequencias = new ArrayList<>();

		List<SaveAlunoFrequenciaRequest> freqs = request.getFrequencias();
		for( SaveAlunoFrequenciaRequest freq : freqs ) {
			Matricula mat = new Matricula();
			mat.setId( freq.getMatriculaId() ); 
			
			AlunoFrequencia af = alunoFrequenciaBuilder.novoAlunoFrequencia( mat );
			alunoFrequenciaBuilder.carregaAlunoFrequencia( af, freq );
			frequencias.add( af );
		}
		dla.setFrequencias( frequencias ); 
	}
	
	public void carregaListaAlunoFrequenciaResponse( ListaAlunoFrequenciaResponse resp, ListaAlunoFrequencia dla ) {
		Turma turma = dla.getAula().getTurmaDisciplina().getTurma();
		
		resp.setId( dla.getId() );
		resp.setDataDia( conversorUtil.dataParaString( dla.getDataDia() ) ); 
		resp.setTurno( turnoEnumManager.tipoResponse( turma.getTurno() ) );
		
		List<AlunoFrequenciaResponse> frequenciaResponses = new ArrayList<>();
		List<AlunoFrequencia> frequencias = dla.getFrequencias();
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


