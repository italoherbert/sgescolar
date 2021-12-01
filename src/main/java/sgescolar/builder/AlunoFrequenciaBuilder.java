package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.FrequenciaTipoEnumManager;
import sgescolar.model.AlunoFrequencia;
import sgescolar.model.Matricula;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.model.response.AlunoFrequenciaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AlunoFrequenciaBuilder {

	@Autowired
	private FrequenciaTipoEnumManager frequenciaTipoEnumManager;
	
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
	}
	
	public AlunoFrequencia novoAlunoFrequencia( Matricula matricula ) {
		AlunoFrequencia dla = new AlunoFrequencia();
		dla.setMatricula( matricula );
		return dla;
	}
	
	public AlunoFrequenciaResponse novoAlunoFrequenciaResponse() {
		return new AlunoFrequenciaResponse();
	}
	
}

