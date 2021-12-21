package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.Matricula;
import sgescolar.model.Nota;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveNotaRequest;
import sgescolar.model.response.NotaResponse;

@Component
public class NotaBuilder {

	@Autowired
	private MatriculaBuilder matriculaBuilder;	
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaNota( Nota n, SaveNotaRequest request ) {
		n.setNota( conversorUtil.stringParaDouble( request.getNota() ) );
	}
	
	public void carregaNotaResponse( NotaResponse resp, Nota n ) {
		resp.setId( n.getId() );
		resp.setNota( conversorUtil.doubleParaString( n.getNota() ) );
		matriculaBuilder.carregaMatriculaResponse( resp.getMatricula(), n.getMatricula() ); 
	}
	
	public Nota novoNota( Matricula matricula, Avaliacao avaliacao ) {
		Nota nota = new Nota();
		nota.setMatricula( matricula );
		nota.setAvaliacao( avaliacao );
		return nota;
	}
	
	public Nota novoNota( Matricula matricula, TurmaDisciplina turmaDisciplina ) {
		Nota nota = new Nota();
		nota.setMatricula( matricula );
		nota.setTurmaDisciplina( turmaDisciplina );
		return nota;
	}
	
	public NotaResponse novoNotaResponse() {
		NotaResponse resp = new NotaResponse();
		resp.setMatricula( matriculaBuilder.novoMatriculaResponse() );
		return resp;
	}
	
	
}
