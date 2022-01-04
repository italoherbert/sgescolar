package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoConceitoEnumManager;
import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.tipos.AvaliacaoTipo;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.AvaliacaoResultado;
import sgescolar.model.Matricula;
import sgescolar.model.request.SaveAvaliacaoResultadoRequest;
import sgescolar.model.response.AvaliacaoResultadoResponse;

@Component
public class AvaliacaoResultadoBuilder {
	
	@Autowired
	private MatriculaBuilder matriculaBuilder;	
	
	@Autowired
	private ConversorUtil conversorUtil;

	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private AvaliacaoConceitoEnumManager avaliacaoConceitoEnumManager;
	
	public void carregaAvaliacaoResultado( AvaliacaoResultado r, SaveAvaliacaoResultadoRequest request, AvaliacaoTipo atipo ) {
		r.setAvaliacaoTipo( atipo ); 
		
		switch( atipo ) {
			case NOTA:
				r.setNota( conversorUtil.stringParaDouble( request.getResultado() ) );
				break;
			case CONCEITUAL:
				r.setConceito( avaliacaoConceitoEnumManager.getEnum( request.getResultado() ) );
				break;
			case DESCRITIVA:
				r.setDescricao( request.getResultado() );
				break;
		}
	}
	
	public void carregaAvaliacaoResultadoResponse( AvaliacaoResultadoResponse resp, AvaliacaoResultado r ) {
		resp.setId( r.getId() );
		
		resp.setAvaliacaoTipo( avaliacaoTipoEnumManager.tipoResponse( r.getAvaliacaoTipo() ) );		
		resp.setNota( conversorUtil.doubleParaString( r.getNota() ) );
		resp.setConceito( avaliacaoConceitoEnumManager.tipoResponse( r.getConceito() ) );
		resp.setDescricao( r.getDescricao() );
						
		matriculaBuilder.carregaMatriculaResponse( resp.getMatricula(), r.getMatricula() ); 
	}
	
	public AvaliacaoResultado novoAvaliacaoResultado( Matricula matricula, Avaliacao avaliacao ) {
		AvaliacaoResultado resultado = new AvaliacaoResultado();
		resultado.setMatricula( matricula );
		resultado.setAvaliacao( avaliacao );
		return resultado;
	}
		
	public AvaliacaoResultadoResponse novoAvaliacaoResultadoResponse() {
		AvaliacaoResultadoResponse resp = new AvaliacaoResultadoResponse();
		resp.setMatricula( matriculaBuilder.novoMatriculaResponse() );
		return resp;
	}
	
	
}
