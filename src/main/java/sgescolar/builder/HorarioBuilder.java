package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.HorarioAula;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.HorarioAulaResponse;
import sgescolar.model.response.HorarioResponse;
import sgescolar.util.ConversorUtil;

@Component
public class HorarioBuilder {

	@Autowired
	private HorarioAulaBuilder horarioAulaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public HorarioResponse geraHorarioResponse( Turma t ) {
		List<HorarioAulaResponse> aresps = new ArrayList<>();
		
		List<TurmaDisciplina> tds = t.getTurmaDisciplinas();
		for( TurmaDisciplina td : tds ) {
			List<HorarioAula> aulas = td.getHorarioAulas();			
			for( HorarioAula a : aulas ) {
				HorarioAulaResponse resp = horarioAulaBuilder.novoAulaResponse();
				horarioAulaBuilder.carregaAulaResponse( resp, a );
				aresps.add( resp );
			}
		}
		
		int quantidadeAulasDia = t.getSerie().getCurso().getQuantidadeAulasDia();
		
		HorarioResponse resp = new HorarioResponse();
		resp.setAulas( aresps ); 
		resp.setQuantidadeAulasDia( conversorUtil.inteiroParaString( quantidadeAulasDia ) ); 
		return resp;
	}
	
	public HorarioResponse geraHorarioResponse( TurmaDisciplina td ) {
		List<HorarioAulaResponse> aresps = new ArrayList<>();
		
		List<HorarioAula> aulas = td.getHorarioAulas();
		for( HorarioAula a : aulas ) {
			HorarioAulaResponse resp = horarioAulaBuilder.novoAulaResponse();
			horarioAulaBuilder.carregaAulaResponse( resp, a );
			aresps.add( resp );
		}
		
		HorarioResponse resp = new HorarioResponse();
		resp.setAulas( aresps ); 
		return resp;
	}
	
}
