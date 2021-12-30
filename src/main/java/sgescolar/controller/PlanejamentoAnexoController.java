package sgescolar.controller;

import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import sgescolar.logica.util.ArquivoUtil;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.FilePlanejamentoAnexoResponse;
import sgescolar.msg.SistemaErro;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.PlanejamentoAnexoService;
import sgescolar.service.ServiceException;

@RestController
@RequestMapping(value="/api/planejamento/anexo")
public class PlanejamentoAnexoController {
	
	@Autowired
	private PlanejamentoAnexoService planejamentoAnexoService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private ArquivoUtil arquivoUtil;
	
	@GetMapping(value="/download/{planAnexoId}/{auth}")
	public ResponseEntity<StreamingResponseBody> downloadAnexo(
			@PathVariable Long planAnexoId,
			@PathVariable String token ) {
		
		try {
			jwtTokenUtil.validaToken( token );
			
			TokenInfos tokenInfos = jwtTokenUtil.getTokenInfos( token );
			FilePlanejamentoAnexoResponse resp = planejamentoAnexoService.getAnexoFile( planAnexoId, tokenInfos );	
							
			try {					
				InputStream in = resp.getArquivo().getBinaryStream();												
				
				StreamingResponseBody stream = ( out ) -> {
					arquivoUtil.copia( in, out );
				};
				
				HttpHeaders headers = new HttpHeaders();
				headers.add( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resp.getArquivoNome()+"\"" );
				
				return ResponseEntity.ok()
						.headers( headers )
						.contentType( MediaType.APPLICATION_OCTET_STREAM )
						.contentLength( resp.getArquivo().length() )
						.body( stream );
			} catch ( SQLException e ) {
				e.printStackTrace();
				throw new SistemaException( SistemaErro.BLOB_SQL_ERRO );
			}
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().build(); 
		}
	}

	@PreAuthorize("hasAuthority('planejamentoDELETE')")
	@DeleteMapping( "/deleta/{anexoId}")
	public ResponseEntity<Object> deletaAnexo( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anexoId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			planejamentoAnexoService.deleteAnexo( anexoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) ); 			
		}		
	}
	
}
