package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.exception.PermissaoEscritaException;
import sgescolar.exception.PermissaoGrupoNaoEncontradoException;
import sgescolar.exception.PermissaoLeituraException;
import sgescolar.exception.PermissaoRemocaoException;
import sgescolar.exception.PermissaoNaoReconhecidoException;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.service.PermissaoGrupoService;

@RestController
@RequestMapping(value="/api/permissao")
public class PermissaoGrupoController {

	@Autowired
	private PermissaoGrupoService permissaoGrupoService;
	
	@PreAuthorize("hasAuthority('permissaoGrupoWRITE')")
	@PostMapping(value="/grupo/salva/{id}")
	public ResponseEntity<Object> salvaPermissaoGrupo( @PathVariable Long id, @RequestBody SavePermissaoGrupoRequest request ) {
		try {
			permissaoGrupoService.salvaPermissaoGrupo( id, request );
			return 	ResponseEntity.ok().build();
		} catch (PermissaoGrupoNaoEncontradoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_GRUPO_NAO_ENCONTRADO ) );
		} catch (PermissaoLeituraException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_LEITURA_INVALIDA ) );
		} catch (PermissaoEscritaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_ESCRITA_INVALIDA ) );
		} catch (PermissaoRemocaoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_REMOCAO_INVALIDA ) );
		}
	}
	
	@PreAuthorize("hasAuthority('permissaoGrupoWRITE')")
	@PatchMapping(value="/salva/{id}")
	public ResponseEntity<Object> salvaPermissao( @PathVariable Long id, @RequestBody SavePermissaoRequest request ) {
		try {
			permissaoGrupoService.salvaPermissao( id, request );
			return 	ResponseEntity.ok().build();
		} catch (PermissaoGrupoNaoEncontradoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_GRUPO_NAO_ENCONTRADO ) );
		} catch (PermissaoLeituraException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_LEITURA_INVALIDA ) );
		} catch (PermissaoEscritaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_ESCRITA_INVALIDA ) );
		} catch (PermissaoRemocaoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_REMOCAO_INVALIDA ) );
		} catch (PermissaoNaoReconhecidoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PERMISSAO_TIPO_NAO_RECONHECIDO ) );
		}
	}
	
}
