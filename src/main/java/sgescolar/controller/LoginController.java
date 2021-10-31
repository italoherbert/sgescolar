package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.LoginRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.LoginResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.LoginService;
import sgescolar.validacao.LoginValidator;

@RestController
@RequestMapping(value="/api/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LoginValidator loginValidator;
	
	@PostMapping(value="/entrar")
	public ResponseEntity<Object> entrar( @RequestBody LoginRequest request ) {		
		try {
			loginValidator.validaRequest( request );
			LoginResponse resp = loginService.login( request );		
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );						
		}
	}
	
}
