package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class CursoBuilder {

	@Autowired
	private CursoModalidadeEnumManager modalidadeEnumManager;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaCurso( Curso c, SaveCursoRequest request ) {		
		c.setNome( request.getNome() );
		c.setModalidade( modalidadeEnumManager.getEnum( request.getModalidade() ) );
		c.setCargaHoraria( conversorUtil.stringParaInteiro( request.getCargaHoraria() ) ); 
	}
	
	public void carregaCursoResponse( CursoResponse resp, Curso c ) {
		resp.setId( c.getId() );
		resp.setNome( c.getNome() );
		resp.setModalidade( modalidadeEnumManager.getString( c.getModalidade() ) );
		resp.setCargaHoraria( conversorUtil.inteiroParaString( c.getCargaHoraria() ) );
		
		Escola e = c.getEscola();
		resp.setEscolaId( e.getId() );
		resp.setEscolaNome( e.getNome() );	
	}
	
	public Curso novoCurso( Escola escola ) {
		Curso c = new Curso();
		c.setEscola( escola );
		return c;
	}
	
	public CursoResponse novoCursoResponse() {
		return new CursoResponse();
	}
	
}
