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
import sgescolar.enums.PermissaoEnumManager;
import sgescolar.enums.RacaEnumManager;
import sgescolar.enums.ReligiaoEnumManager;
import sgescolar.enums.SexoEnumManager;
import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
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
	private PermissaoEnumManager permissaoEnumManager;
	
	@Autowired
	private RacaEnumManager racaEnumManager;
	
	@Autowired
	private ReligiaoEnumManager religiaoEnumManager;
	
	@Autowired
	private SexoEnumManager sexoEnumManager;		
	
	@GetMapping(value="/todos")
	public ResponseEntity<Object> listaTipos() {
		TiposResponse resp = new TiposResponse();
		resp.setCursoModalidades( cursoModalidadeEnumManager.valores() );
		resp.setEscolaridades( escolaridadeEnumManager.valores() );
		resp.setEstadosCivis( estadoCivilEnumManager.valores() );
		resp.setNacionalidades( nacionalidadeEnumManager.valores() );
		resp.setPermissoes( permissaoEnumManager.valores() );
		resp.setRacas( racaEnumManager.valores() );
		resp.setReligioes( religiaoEnumManager.valores() );
		resp.setSexos( sexoEnumManager.valores() );
		resp.setUsuarioPerfis( usuarioPerfilEnumManager.valores() );
		resp.setFuncionarioFuncoes( funcionarioFuncaoEnumManager.valores() );
		return ResponseEntity.ok( resp ); 
	}
	
	@GetMapping(value="/perfis")
	public ResponseEntity<Object> listaUsuarioPerfis() {
		return ResponseEntity.ok( usuarioPerfilEnumManager.valores() );
	}
	
	@GetMapping(value="/funcionario-funcoes")
	public ResponseEntity<Object> listaFuncionarioFuncoes() {
		return ResponseEntity.ok( funcionarioFuncaoEnumManager.valores() );
	}
	
	@GetMapping(value="/curso-modalidades")
	public ResponseEntity<Object> listaCursoModalidades() {
		return ResponseEntity.ok( cursoModalidadeEnumManager.valores() );
	}
	
	@GetMapping(value="/escolaridades")
	public ResponseEntity<Object> listaEscolaridades() {
		return ResponseEntity.ok( escolaridadeEnumManager.valores() );
	}
	
	@GetMapping(value="/estados-civis")
	public ResponseEntity<Object> listaEstadosCivis() {
		return ResponseEntity.ok( estadoCivilEnumManager.valores() );
	}
	
	@GetMapping(value="/permissoes")
	public ResponseEntity<Object> listaPermissoes() {
		return ResponseEntity.ok( permissaoEnumManager.valores() );
	}
	
	@GetMapping(value="/sexos")
	public ResponseEntity<Object> listaSexos() {
		return ResponseEntity.ok( sexoEnumManager.valores() );
	}
	
	@GetMapping(value="/nacionalidades")
	public ResponseEntity<Object> listaNacionalidades() {
		return ResponseEntity.ok( nacionalidadeEnumManager.valores() );
	}
	
	@GetMapping(value="/racas")
	public ResponseEntity<Object> listaRacas() {
		return ResponseEntity.ok( racaEnumManager.valores() );
	}
	
	@GetMapping(value="/religioes")
	public ResponseEntity<Object> listaReligioes() {
		return ResponseEntity.ok( religiaoEnumManager.valores() );
	}
	
	@GetMapping(value="/perfis/secretario")
	public ResponseEntity<Object> listaSecretarioPerfis() {
		String[] lista = {
			UsuarioPerfil.SECRETARIO.name(),
		};
		return ResponseEntity.ok( lista );
	}
	
	@GetMapping(value="/perfis/aluno")
	public ResponseEntity<Object> listaAlunoPerfis() {
		String[] lista = {
			UsuarioPerfil.ALUNO.name(),
		};
		return ResponseEntity.ok( lista );
	}
	
	@GetMapping(value="/perfis/professor")
	public ResponseEntity<Object> listaProfessorPerfis() {
		String[] lista = {
			UsuarioPerfil.PROFESSOR.name(),
		};
		return ResponseEntity.ok( lista );
	}
	
	@GetMapping(value="/perfis/admin")
	public ResponseEntity<Object> listaAdminPerfis() {
		String[] lista = {
			UsuarioPerfil.ADMIN.name(),
		};
		return ResponseEntity.ok( lista );
	}	
	
}
