package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.exception.CursoModalidadeNaoReconhecidaException;
import sgescolar.model.Curso;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.util.enums.CursoModalidadeEnumConversor;

@Component
public class CursoBuilder {

	@Autowired
	private CursoModalidadeEnumConversor modalidadeEnumConversor;
	
	public void carregaCurso( Curso c, SaveCursoRequest request ) throws CursoModalidadeNaoReconhecidaException {
		c.setNome( request.getNome() );
		c.setModalidade( modalidadeEnumConversor.getEnum( request.getModalidade() ) );
	}
	
	public void carregaCursoResponse( CursoResponse resp, Curso c ) {
		resp.setId( c.getId() );
		resp.setNome( c.getNome() );
		resp.setModalidade( modalidadeEnumConversor.getString( c.getModalidade() ) );
	}
	
	public Curso novoCurso() {
		return new Curso();
	}
	
	public CursoResponse novoCursoResponse() {
		return new CursoResponse();
	}
	
}
