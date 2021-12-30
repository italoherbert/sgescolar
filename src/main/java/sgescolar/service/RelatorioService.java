package sgescolar.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import sgescolar.logica.manager.TurmaManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Escola;
import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoConteudo;
import sgescolar.model.PlanejamentoObjetivo;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.msg.ServiceErro;
import sgescolar.relatorio.jrdatasource.PlanejamentoConteudoJRDataSource;
import sgescolar.relatorio.jrdatasource.PlanejamentoObjetivoJRDataSource;
import sgescolar.repository.PlanejamentoRepository;
import sgescolar.security.jwt.TokenInfos;

@Service
public class RelatorioService {
	
	public final static String PLANEJAMENTO_JASPER_FILE = "/planejamento.jasper";
	public final static String PLANEJAMENTO_CONTEUDOS_JASPER_FILE = "/planejamento-conteudos.jasper";
	public final static String PLANEJAMENTO_OBJETIVOS_JASPER_FILE = "/planejamento-objetivos.jasper";
	public final static String LOGO_ICONE = "/sje.png";
	
	@Autowired
	private PlanejamentoRepository planejamentoRepository;
	
	@Autowired
	private TurmaManager turmaManager;
	
	@Autowired
	private ConversorUtil conversorUtil;

	public void geraEnviaPlanejamentoRelatorio( Long planejamentoId, TokenInfos tokenInfos, HttpServletResponse response ) throws ServiceException {
		try {			
			JasperReport report = (JasperReport)JRLoader.loadObject( RelatorioService.class.getResourceAsStream( PLANEJAMENTO_JASPER_FILE ) );
			JasperReport conteudosReport = (JasperReport)JRLoader.loadObject( RelatorioService.class.getResourceAsStream( PLANEJAMENTO_CONTEUDOS_JASPER_FILE ) );
			JasperReport objetivosReport = (JasperReport)JRLoader.loadObject( RelatorioService.class.getResourceAsStream( PLANEJAMENTO_OBJETIVOS_JASPER_FILE ) );
			
			InputStream logoFile = RelatorioService.class.getResourceAsStream( LOGO_ICONE );
			
			Optional<Planejamento> planOp = planejamentoRepository.findById( planejamentoId );
			if ( !planOp.isPresent() )
				throw new ServiceException( ServiceErro.PLANEJAMENTO_NAO_ENCONTRADO );
			
			Planejamento plan = planOp.get();
			
			List<PlanejamentoConteudo> conteudos = plan.getConteudos();
			List<PlanejamentoObjetivo> objetivos = plan.getObjetivos();
			
			ProfessorAlocacao paloc = plan.getProfessorAlocacao();
			TurmaDisciplina tdis = paloc.getTurmaDisciplina();
			Turma turma = tdis.getTurma();
			Escola escola = paloc.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();			
			
			String instituicaoP = escola.getInstituicao().getRazaoSocial();
			String escolaP = escola.getNome();
			String professorP = paloc.getProfessor().getFuncionario().getPessoa().getNome();
			String disciplinaP = tdis.getDisciplina().getDescricao();
			String turmaP = turmaManager.getDescricaoDetalhada( turma );
			String dataInicialP = conversorUtil.dataParaString( paloc.getDataInicio() );
			String dataFinalP = conversorUtil.dataParaString( paloc.getDataFim() );
			
			String descricaoP = plan.getDescricao();
			String metodologiaP = plan.getMetodologia();
			String recursosP = plan.getRecursos();
			String metodosAvaliacaoP = plan.getMetodosAvaliacao();
			String referenciasP = plan.getReferencias();
			
			Map<String, Object> map = new HashMap<>();
			map.put( "logo", logoFile );
			map.put( "instituicao", instituicaoP );
			map.put( "escola", escolaP );
			map.put( "professor", professorP );
			map.put( "disciplina", disciplinaP );
			map.put( "turma", turmaP );
			map.put( "dataInicial", dataInicialP );
			map.put( "dataFinal", ( dataFinalP == null ? "" : dataFinalP ) );
			
			map.put( "conteudos_report", conteudosReport );
			map.put( "conteudos_jrdatasource", new PlanejamentoConteudoJRDataSource( conteudos ) );
			
			map.put( "objetivos_report", objetivosReport );
			map.put( "objetivos_jrdatasource", new PlanejamentoObjetivoJRDataSource( objetivos ) );
			
			map.put( "descricao", descricaoP );
			map.put( "metodologia", metodologiaP );
			map.put( "recursos", recursosP );
			map.put( "metodos_avaliacao", metodosAvaliacaoP );
			map.put( "referencias", referenciasP );
			
			JasperPrint jrprint = JasperFillManager.fillReport( report, map, new JREmptyDataSource() );
			
			OutputStream out = response.getOutputStream();
			
			response.setHeader( "Content-Disposition", "inline" );
			response.setContentType( "application/pdf" );
						
			JasperExportManager.exportReportToPdfStream( jrprint, out );
			
			out.flush();
			out.close();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}