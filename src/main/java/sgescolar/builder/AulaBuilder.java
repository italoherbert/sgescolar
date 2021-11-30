package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Aula;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAulaRequest;
import sgescolar.model.response.AulaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AulaBuilder {

	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaAula( Aula a, SaveAulaRequest request ) {
		a.setDia( conversorUtil.stringParaInteiro(  request.getDia() ) );
		a.setNumeroAula( conversorUtil.stringParaInteiro( request.getNumeroAula() ) ); 
	}
	
	public void carregaAulaResponse( AulaResponse resp, Aula a ) {
		resp.setId( a.getId() );
		resp.setDia( conversorUtil.inteiroParaString( a.getDia() ) );
		resp.setNumeroAula( conversorUtil.inteiroParaString( a.getNumeroAula() ) );
		resp.setDisciplinaSigla( a.getTurmaDisciplina().getDisciplina().getSigla() ); 
	}
	
	public Aula novoAula( TurmaDisciplina td) {
		Aula aula = new Aula();
		aula.setTurmaDisciplina( td );
		return aula;
	}
	
	public AulaResponse novoAulaResponse() {
		return new AulaResponse();
	}
	
}

