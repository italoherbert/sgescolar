package sgescolar.builder;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Escola;
import sgescolar.model.Professor;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.ProfessorAlocacaoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class ProfessorAlocacaoBuilder {
		
	@Autowired
	private TurmaDisciplinaBuilder turmaDisciplinaBuilder;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaProfessorAlocacao( ProfessorAlocacao pa ) {
		pa.setAtivo( true );
		pa.setDataInicio( new Date() );
		pa.setDataFim( null );
	}
	
	public void carregaProfessorAlocacaoResponse( ProfessorAlocacaoResponse resp, ProfessorAlocacao pa ) {
		resp.setId( pa.getId() );
		resp.setAtivo( conversorUtil.booleanParaString( pa.isAtivo() ) );
		resp.setDataInicio( conversorUtil.dataParaString( pa.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( pa.getDataFim() ) );

		Professor p = pa.getProfessor();
		resp.setProfessorId( p.getId() );
		resp.setProfessorNome( p.getFuncionario().getPessoa().getNome() ); 
		
		turmaDisciplinaBuilder.carregaTurmaDisciplinaResponse( resp.getTurmaDisciplina(), pa.getTurmaDisciplina() );
	}
	
	public ProfessorAlocacao novoProfessorAlocacao( TurmaDisciplina td, Professor p, Escola e ) {
		ProfessorAlocacao pa = new ProfessorAlocacao();
		pa.setTurmaDisciplina( td );
		pa.setProfessor( p );			
		pa.setEscola( e );
		return pa;
	}
	
	public ProfessorAlocacaoResponse novoProfessorAlocacaoResponse() {
		ProfessorAlocacaoResponse resp = new ProfessorAlocacaoResponse();
		resp.setTurmaDisciplina( turmaDisciplinaBuilder.novoTurmaDisciplinaResponse() ); 
		return resp;
	}
	
}
