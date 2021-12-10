package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.PlanejamentoConteudo;
import sgescolar.model.PlanejamentoObjetivo;
import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.model.response.PlanejamentoAnexoResponse;
import sgescolar.model.response.PlanejamentoConteudoResponse;
import sgescolar.model.response.PlanejamentoObjetivoResponse;
import sgescolar.model.response.PlanejamentoResponse;

@Component
public class PlanejamentoBuilder {

	@Autowired
	private PlanejamentoObjetivoBuilder planejamentoObjetivoBuilder;
	
	@Autowired
	private PlanejamentoConteudoBuilder planejamentoConteudoBuilder;
	
	@Autowired
	private PlanejamentoAnexoBuilder planejamentoAnexoBuilder;
	
	public void carregaPlanejamento( Planejamento p, SavePlanejamentoRequest request ) {
		p.setMetodologia( request.getMetodologia() );
		p.setMetodosAvaliacao( request.getMetodosAvaliacao() );
		p.setRecursos( request.getRecursos() );
		p.setReferencias( request.getReferencias() );				
	}
	
	public void carregaPlanejamentoResponse( PlanejamentoResponse resp, Planejamento p ) {
		resp.setId( p.getId() );
		resp.setMetodologia( p.getMetodologia() );
		resp.setMetodosAvaliacao( p.getMetodosAvaliacao() );
		resp.setRecursos( p.getRecursos() );
		resp.setReferencias( p.getReferencias() );
		
		List<PlanejamentoObjetivoResponse> objResps = new ArrayList<>();
		List<PlanejamentoConteudoResponse> conResps = new ArrayList<>();
		List<PlanejamentoAnexoResponse> anexoResps = new ArrayList<>();
		
		List<PlanejamentoObjetivo> objetivos = p.getObjetivos();
		List<PlanejamentoConteudo> conteudos = p.getConteudos();
		List<PlanejamentoAnexo> anexos = p.getAnexos();
		
		for( PlanejamentoObjetivo obj : objetivos ) {
			PlanejamentoObjetivoResponse objResp = planejamentoObjetivoBuilder.novoPlanejamentoObjetivoResponse();
			planejamentoObjetivoBuilder.carregaPlanejamentoObjetivoResponse( objResp, obj );
			objResps.add( objResp );
		}
		
		for( PlanejamentoConteudo conteudo : conteudos ) {
			PlanejamentoConteudoResponse conteudoResp = planejamentoConteudoBuilder.novoPlanejamentoConteudoResponse();
			planejamentoConteudoBuilder.carregaPlanejamentoConteudoResponse( conteudoResp, conteudo );
			conResps.add( conteudoResp );
		}
		
		for( PlanejamentoAnexo anexo : anexos ) {
			PlanejamentoAnexoResponse anexoResp = planejamentoAnexoBuilder.novoPlanejamentoAnexoResponse();
			planejamentoAnexoBuilder.carregaPlanejamentoAnexoResponse( anexoResp, anexo );
			anexoResps.add( anexoResp );
		}
		
		resp.setObjetivos( objResps );
		resp.setConteudos( conResps );
		resp.setAnexos( anexoResps );
	}
	
	public Planejamento novoPlanejamento() {
		return new Planejamento();		
	}
	
	public PlanejamentoResponse novoPlanejamentoResponse() {
		return new PlanejamentoResponse();		
	}
	
}
