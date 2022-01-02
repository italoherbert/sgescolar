package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.enums.EscolaridadeEnumManager;
import sgescolar.enums.EstadoCivilEnumManager;
import sgescolar.enums.FrequenciaModalidadeEnumManager;
import sgescolar.enums.FuncionarioFuncaoEnumManager;
import sgescolar.enums.NacionalidadeEnumManager;
import sgescolar.enums.PeriodoEnumManager;
import sgescolar.enums.PlanejamentoTipoEnumManager;
import sgescolar.enums.RacaEnumManager;
import sgescolar.enums.ReligiaoEnumManager;
import sgescolar.enums.SexoEnumManager;
import sgescolar.enums.TurnoEnumManager;
import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.response.TipoArrayResponse;

@RestController
@RequestMapping(value="/api/tipos") 
public class TiposController {

	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	@Autowired
	private FuncionarioFuncaoEnumManager funcionarioFuncaoEnumManager;
	
	@Autowired
	private CursoModalidadeEnumManager cursoModalidadeEnumManager;
	
	@Autowired
	private EscolaridadeEnumManager escolaridadeEnumManager;
	
	@Autowired
	private EstadoCivilEnumManager estadoCivilEnumManager;
	
	@Autowired
	private NacionalidadeEnumManager nacionalidadeEnumManager;
		
	@Autowired
	private RacaEnumManager racaEnumManager;
	
	@Autowired
	private ReligiaoEnumManager religiaoEnumManager;
	
	@Autowired
	private SexoEnumManager sexoEnumManager;		
	
	@Autowired
	private PeriodoEnumManager periodoEnumManager;
	
	@Autowired
	private TurnoEnumManager turnoEnumManager;
		
	@Autowired
	private FrequenciaModalidadeEnumManager frequenciaTipoEnumManager;
	
	@Autowired
	private PlanejamentoTipoEnumManager planejamentoTipoEnumManager;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@GetMapping(value="/perfis")
	public ResponseEntity<Object> listaUsuarioPerfis() {				
		return ResponseEntity.ok( usuarioPerfilEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/funcionario-funcoes")
	public ResponseEntity<Object> listaFuncionarioFuncoes() {
		return ResponseEntity.ok( funcionarioFuncaoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/curso-modalidades")
	public ResponseEntity<Object> listaCursoModalidades() {
		return ResponseEntity.ok( cursoModalidadeEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/escolaridades")
	public ResponseEntity<Object> listaEscolaridades() {
		return ResponseEntity.ok( escolaridadeEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/estados-civis")
	public ResponseEntity<Object> listaEstadosCivis() {
		return ResponseEntity.ok( estadoCivilEnumManager.tipoArrayResponse() );
	}
		
	@GetMapping(value="/sexos")
	public ResponseEntity<Object> listaSexos() {
		return ResponseEntity.ok( sexoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/nacionalidades")
	public ResponseEntity<Object> listaNacionalidades() {
		return ResponseEntity.ok( nacionalidadeEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/racas")
	public ResponseEntity<Object> listaRacas() {
		return ResponseEntity.ok( racaEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/religioes")
	public ResponseEntity<Object> listaReligioes() {
		return ResponseEntity.ok( religiaoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/periodos")
	public ResponseEntity<Object> listaPeriodos() {		
		return ResponseEntity.ok( periodoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/turnos")
	public ResponseEntity<Object> listaTurnos() {		
		return ResponseEntity.ok( turnoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/frequencia-tipos")
	public ResponseEntity<Object> listaFrequenciaTipos() {		
		return ResponseEntity.ok( frequenciaTipoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/planejamento-tipos")
	public ResponseEntity<Object> listaPlanejamentoTipos() {		
		return ResponseEntity.ok( planejamentoTipoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/avaliacao-tipos")
	public ResponseEntity<Object> listaAvaliacaoTipos() {
		return ResponseEntity.ok( avaliacaoTipoEnumManager.tipoArrayResponse() );
	}
	
	@GetMapping(value="/perfis/secretario")
	public ResponseEntity<Object> listaSecretarioPerfis() {
		TipoArrayResponse resp = new TipoArrayResponse();
		resp.setNames( new String[] { UsuarioPerfil.SECRETARIO.name() } );
		resp.setLabels( new String[] { UsuarioPerfil.SECRETARIO.label() } );		 
		return ResponseEntity.ok( resp );
	}
	
	@GetMapping(value="/perfis/aluno")
	public ResponseEntity<Object> listaAlunoPerfis() {
		TipoArrayResponse resp = new TipoArrayResponse();
		resp.setNames( new String[] { UsuarioPerfil.ALUNO.name() } );
		resp.setLabels( new String[] { UsuarioPerfil.ALUNO.label() } );		 
		return ResponseEntity.ok( resp );		
	}
	
	@GetMapping(value="/perfis/professor")
	public ResponseEntity<Object> listaProfessorPerfis() {
		TipoArrayResponse resp = new TipoArrayResponse();
		resp.setNames( new String[] { UsuarioPerfil.PROFESSOR.name() } );
		resp.setLabels( new String[] { UsuarioPerfil.PROFESSOR.label() } );		 
		return ResponseEntity.ok( resp );	
	}
	
	@GetMapping(value="/perfis/admin")
	public ResponseEntity<Object> listaAdminPerfis() {
		TipoArrayResponse resp = new TipoArrayResponse();
		resp.setNames( new String[] { UsuarioPerfil.ADMIN.name() } );
		resp.setLabels( new String[] { UsuarioPerfil.ADMIN.label() } );		 
		return ResponseEntity.ok( resp );	
	}
		
}
