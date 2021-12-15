package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.SemanaDiaEnumManager;
import sgescolar.enums.tipos.SemanaDia;
import sgescolar.model.HorarioAula;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveHorarioAulaRequest;
import sgescolar.model.response.HorarioAulaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class HorarioAulaBuilder {

	@Autowired
	private ConversorUtil conversorUtil;
		
	@Autowired
	private SemanaDiaEnumManager semanaDiaEnumManager;
	
	public void carregaAula( HorarioAula a, SaveHorarioAulaRequest request ) {
		a.setSemanaDia( conversorUtil.stringParaInteiro(  request.getSemanaDia() ) );
		a.setNumeroAula( conversorUtil.stringParaInteiro( request.getNumeroAula() ) );
	}
	
	public void carregaAulaResponse( HorarioAulaResponse resp, HorarioAula a ) {
		SemanaDia sdia = semanaDiaEnumManager.getPorID( a.getSemanaDia() ); 
				
		resp.setId( a.getId() );
		resp.setSemanaDiaLabel( sdia.label() ); 
		resp.setSemanaDia( conversorUtil.inteiroParaString( a.getSemanaDia() ) );
		resp.setNumeroAula( conversorUtil.inteiroParaString( a.getNumeroAula() ) );
		resp.setDisciplinaSigla( a.getTurmaDisciplina().getDisciplina().getSigla() ); 
		resp.setAtiva( conversorUtil.booleanParaString( a.isAtiva() ) ); 
	}
	
	public HorarioAula novoAula( TurmaDisciplina td ) {
		HorarioAula aula = new HorarioAula();
		aula.setTurmaDisciplina( td );
		aula.setAtiva( true ); 
		return aula;
	}
	
	public HorarioAulaResponse novoAulaResponse() {
		return new HorarioAulaResponse();
	}
	
}

