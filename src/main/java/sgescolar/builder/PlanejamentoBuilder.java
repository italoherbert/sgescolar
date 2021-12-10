package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PlanejamentoTipoEnumManager;
import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.PlanejamentoConteudo;
import sgescolar.model.PlanejamentoObjetivo;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.model.response.PlanejamentoAnexoResponse;
import sgescolar.model.response.PlanejamentoConteudoResponse;
import sgescolar.model.response.PlanejamentoObjetivoResponse;
import sgescolar.model.response.PlanejamentoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PlanejamentoBuilder {

	@Autowired
	private PlanejamentoObjetivoBuilder planejamentoObjetivoBuilder;
	
	@Autowired
	private PlanejamentoConteudoBuilder planejamentoConteudoBuilder;
	
	@Autowired
	private PlanejamentoAnexoBuilder planejamentoAnexoBuilder;
	
	@Autowired
	private PlanejamentoTipoEnumManager planejamentoTipoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaPlanejamento( Planejamento p, SavePlanejamentoRequest request ) {
		p.setDescricao( request.getDescricao() );
		p.setMetodologia( request.getMetodologia() );
		p.setMetodosAvaliacao( request.getMetodosAvaliacao() );
		p.setRecursos( request.getRecursos() );
		p.setReferencias( request.getReferencias() );
		p.setDataInicio( conversorUtil.stringParaData( request.getDataInicio() ) );
		p.setDataFim( conversorUtil.stringParaData( request.getDataFim() ) );
		p.setTipo( planejamentoTipoEnumManager.getEnum( request.getTipo() ) );
	}
	
	public void carregaPlanejamentoResponse( PlanejamentoResponse resp, Planejamento p ) {
		resp.setId( p.getId() );
		resp.setDescricao( p.getDescricao() );
		resp.setMetodologia( p.getMetodologia() );
		resp.setMetodosAvaliacao( p.getMetodosAvaliacao() );
		resp.setRecursos( p.getRecursos() );
		resp.setReferencias( p.getReferencias() );
		resp.setDataInicio( conversorUtil.dataParaString( p.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( p.getDataFim() ) ); 
		resp.setTipo( planejamentoTipoEnumManager.tipoResponse( p.getTipo() ) ); 
		
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
	
	public Planejamento novoPlanejamento( ProfessorAlocacao aloc ) {
		Planejamento planejamento = new Planejamento();
		planejamento.setProfessorAlocacao( aloc ); 
		return planejamento;
	}
	
	public PlanejamentoResponse novoPlanejamentoResponse() {
		return new PlanejamentoResponse();		
	}
	
}
