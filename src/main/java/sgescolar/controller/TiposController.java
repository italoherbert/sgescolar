package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.enums.EscolaridadeEnumManager;
import sgescolar.enums.EstadoCivilEnumManager;
import sgescolar.enums.FuncionarioFuncaoEnumManager;
import sgescolar.enums.NacionalidadeEnumManager;
import sgescolar.enums.PeriodoEnumManager;
import sgescolar.enums.RacaEnumManager;
import sgescolar.enums.ReligiaoEnumManager;
import sgescolar.enums.SexoEnumManager;
import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.response.TipoResponse;
import sgescolar.model.response.TiposResponse;

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
	
	@GetMapping(value="/todos")
	public ResponseEntity<Object> listaTipos() {
		TiposResponse resp = new TiposResponse();
		resp.setCursoModalidades( cursoModalidadeEnumManager.tipoResponse() );
		resp.setEscolaridades( escolaridadeEnumManager.tipoResponse() );
		resp.setEstadosCivis( estadoCivilEnumManager.tipoResponse() );
		resp.setNacionalidades( nacionalidadeEnumManager.tipoResponse() );
		resp.setRacas( racaEnumManager.tipoResponse() );
		resp.setReligioes( religiaoEnumManager.tipoResponse() );
		resp.setSexos( sexoEnumManager.tipoResponse() );
		resp.setUsuarioPerfis( usuarioPerfilEnumManager.tipoResponse() );
		resp.setFuncionarioFuncoes( funcionarioFuncaoEnumManager.tipoResponse() );
		resp.setPeriodos( periodoEnumManager.tipoResponse() );
		return ResponseEntity.ok( resp ); 
	}
	
	@GetMapping(value="/perfis")
	public ResponseEntity<Object> listaUsuarioPerfis() {				
		return ResponseEntity.ok( usuarioPerfilEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/funcionario-funcoes")
	public ResponseEntity<Object> listaFuncionarioFuncoes() {
		return ResponseEntity.ok( funcionarioFuncaoEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/curso-modalidades")
	public ResponseEntity<Object> listaCursoModalidades() {
		return ResponseEntity.ok( cursoModalidadeEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/escolaridades")
	public ResponseEntity<Object> listaEscolaridades() {
		return ResponseEntity.ok( escolaridadeEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/estados-civis")
	public ResponseEntity<Object> listaEstadosCivis() {
		return ResponseEntity.ok( estadoCivilEnumManager.tipoResponse() );
	}
		
	@GetMapping(value="/sexos")
	public ResponseEntity<Object> listaSexos() {
		return ResponseEntity.ok( sexoEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/nacionalidades")
	public ResponseEntity<Object> listaNacionalidades() {
		return ResponseEntity.ok( nacionalidadeEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/racas")
	public ResponseEntity<Object> listaRacas() {
		return ResponseEntity.ok( racaEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/religioes")
	public ResponseEntity<Object> listaReligioes() {
		return ResponseEntity.ok( religiaoEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/periodos")
	public ResponseEntity<Object> listaPeriodos() {		
		return ResponseEntity.ok( periodoEnumManager.tipoResponse() );
	}
	
	@GetMapping(value="/perfis/secretario")
	public ResponseEntity<Object> listaSecretarioPerfis() {
		TipoResponse resp = new TipoResponse();
		resp.setValores( new String[] { UsuarioPerfil.SECRETARIO.name() } );
		resp.setDescricoes( new String[] { UsuarioPerfil.SECRETARIO.descricao() } );		 
		return ResponseEntity.ok( resp );
	}
	
	@GetMapping(value="/perfis/aluno")
	public ResponseEntity<Object> listaAlunoPerfis() {
		TipoResponse resp = new TipoResponse();
		resp.setValores( new String[] { UsuarioPerfil.ALUNO.name() } );
		resp.setDescricoes( new String[] { UsuarioPerfil.ALUNO.descricao() } );		 
		return ResponseEntity.ok( resp );		
	}
	
	@GetMapping(value="/perfis/professor")
	public ResponseEntity<Object> listaProfessorPerfis() {
		TipoResponse resp = new TipoResponse();
		resp.setValores( new String[] { UsuarioPerfil.PROFESSOR.name() } );
		resp.setDescricoes( new String[] { UsuarioPerfil.PROFESSOR.descricao() } );		 
		return ResponseEntity.ok( resp );	
	}
	
	@GetMapping(value="/perfis/admin")
	public ResponseEntity<Object> listaAdminPerfis() {
		TipoResponse resp = new TipoResponse();
		resp.setValores( new String[] { UsuarioPerfil.ADMIN.name() } );
		resp.setDescricoes( new String[] { UsuarioPerfil.ADMIN.descricao() } );		 
		return ResponseEntity.ok( resp );	
	}
		
}
