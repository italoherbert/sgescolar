package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoConceitoEnumManager;
import sgescolar.enums.AvaliacaoMetodoEnumManager;
import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.tipos.AvaliacaoMetodo;
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
	private AvaliacaoMetodoEnumManager avaliacaoMetodoEnumManager;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private AvaliacaoConceitoEnumManager avaliacaoConceitoEnumManager;
	
	public void carregaAvaliacaoResultado( AvaliacaoResultado r, SaveAvaliacaoResultadoRequest request, AvaliacaoMetodo avMetodo ) {		
		switch( avMetodo ) {
			case NUMERICA:
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
		
		Avaliacao a = r.getAvaliacao();
		AvaliacaoTipo avTipo = a.getAvaliacaoTipo();
		AvaliacaoMetodo avMetodo = a.getAvaliacaoMetodo();
		
		resp.setAvaliacaoMetodo( avaliacaoMetodoEnumManager.tipoResponse( avMetodo ) );
		resp.setAvaliacaoTipo( avaliacaoTipoEnumManager.tipoResponse( avTipo ) ); 
		
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
