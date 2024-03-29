package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoMetodoEnumManager;
import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.Instituicao;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;

@Component
public class CursoBuilder {

	@Autowired
	private CursoModalidadeEnumManager modalidadeEnumManager;
	
	@Autowired
	private AvaliacaoMetodoEnumManager avaliacaoMetodoEnumManager;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaCurso( Curso c, SaveCursoRequest request ) {		
		c.setDescricao( request.getDescricao() );
		c.setCargaHoraria( conversorUtil.stringParaInteiro( request.getCargaHoraria() ) );
		c.setQuantidadeAulasDia( conversorUtil.stringParaInteiro( request.getQuantidadeAulasDia() ) );
		c.setModalidade( modalidadeEnumManager.getEnum( request.getModalidade() ) );
		c.setAvaliacaoMetodo( avaliacaoMetodoEnumManager.getEnum( request.getAvaliacaoMetodo() ) );
		
	}
	
	public void carregaCursoResponse( CursoResponse resp, Curso c ) {
		resp.setId( c.getId() );
		resp.setDescricao( c.getDescricao() );
		resp.setCargaHoraria( conversorUtil.inteiroParaString( c.getCargaHoraria() ) );
		resp.setQuantidadeAulasDia( conversorUtil.inteiroParaString( c.getQuantidadeAulasDia() ) ); 
		resp.setModalidade( modalidadeEnumManager.tipoResponse( c.getModalidade() ) );
		resp.setAvaliacaoMetodo( avaliacaoMetodoEnumManager.tipoResponse( c.getAvaliacaoMetodo() ) );

		Escola e = c.getEscola();
		resp.setEscolaId( e.getId() );
		resp.setEscolaNome( e.getNome() );
		
		Instituicao inst = e.getInstituicao();
		resp.setInstituicaoId( inst.getId() );
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
