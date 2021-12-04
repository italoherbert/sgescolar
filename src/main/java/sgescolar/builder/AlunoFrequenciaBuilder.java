package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.FrequenciaTipoEnumManager;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.ListaAlunoFrequencia;
import sgescolar.model.Matricula;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.model.response.AlunoFrequenciaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AlunoFrequenciaBuilder {

	@Autowired
	private FrequenciaTipoEnumManager frequenciaTipoEnumManager;
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaAlunoFrequencia( AlunoFrequencia dla, SaveAlunoFrequenciaRequest request ) {
		dla.setEstevePresente( conversorUtil.stringParaBoolean( request.getEstevePresente() ) );
		dla.setFrequenciaTipo( frequenciaTipoEnumManager.getEnum( request.getFrequenciaTipo() ) ); 
	}
	
	public void carregaAlunoFrequenciaResponse( AlunoFrequenciaResponse resp, AlunoFrequencia dla ) {
		resp.setId( dla.getId() );
		resp.setEstevePresente( conversorUtil.booleanParaString( dla.isEstevePresente() ) );
		resp.setFrequenciaTipo( frequenciaTipoEnumManager.tipoResponse( dla.getFrequenciaTipo() ) );
		
		matriculaBuilder.carregaMatriculaResponse( resp.getMatricula(), dla.getMatricula() );
	}
	
	public AlunoFrequencia novoAlunoFrequencia( ListaAlunoFrequencia laf, Matricula matricula ) {
		AlunoFrequencia dla = new AlunoFrequencia();
		dla.setListaFrequencia( laf ); 
		dla.setMatricula( matricula );
		return dla;
	}
	
	public AlunoFrequenciaResponse novoAlunoFrequenciaResponse() {
		AlunoFrequenciaResponse resp = new AlunoFrequenciaResponse();
		resp.setMatricula( matriculaBuilder.novoMatriculaResponse() ); 
		return resp;
	}
	
}

