package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.exception.BooleanFormatoException;
import sgescolar.exception.PermissaoEscritaException;
import sgescolar.exception.PermissaoLeituraException;
import sgescolar.exception.PermissaoNaoReconhecidoException;
import sgescolar.exception.PermissaoRemocaoException;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Recurso;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.enums.TipoPermissao;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.model.response.PermissaoGrupoResponse;
import sgescolar.util.ConversorUtil;
import sgescolar.util.enums.PermissaoEnumConversor;

@Component
public class PermissaoGrupoBuilder {

	@Autowired
	private PermissaoEnumConversor permissaoEnumConversor;
	
	@Autowired
	private ConversorUtil booleanUtil;
	
	public void carregaPermissao( PermissaoGrupo pg, SavePermissaoRequest req ) 
			throws PermissaoLeituraException, 
				PermissaoEscritaException,
				PermissaoRemocaoException,
				PermissaoNaoReconhecidoException {
				
		TipoPermissao tipoPerm = permissaoEnumConversor.getEnum( req.getTipo() );				
		switch( tipoPerm ) {
			case LEITURA:
				try {
					pg.setLeitura( booleanUtil.stringParaBoolean( req.getValor() ) );
				} catch( BooleanFormatoException e ) {
					throw new PermissaoLeituraException();
				}
				break;
			case ESCRITA:
				try {
					pg.setEscrita( booleanUtil.stringParaBoolean( req.getValor() ) );
				} catch( BooleanFormatoException e ) {
					throw new PermissaoEscritaException();
				}
				break;
			case REMOCAO:
				try {
					pg.setRemocao( booleanUtil.stringParaBoolean( req.getValor() ) );
				} catch( BooleanFormatoException e ) {
					throw new PermissaoRemocaoException();
				}
				break;
		}
	}
		
	public void carregaPermissaoGrupo( PermissaoGrupo pg, SavePermissaoGrupoRequest req ) 
			throws PermissaoLeituraException, 
				PermissaoEscritaException,
				PermissaoRemocaoException {
				
		try {
			pg.setLeitura( booleanUtil.stringParaBoolean( req.getLeitura() ) );
		} catch( BooleanFormatoException e ) {
			throw new PermissaoLeituraException();
		}
		
		try {
			pg.setEscrita( booleanUtil.stringParaBoolean( req.getEscrita() ) );
		} catch( BooleanFormatoException e ) {
			throw new PermissaoEscritaException();
		}
		
		try {
			pg.setRemocao( booleanUtil.stringParaBoolean( req.getRemocao() ) );
		} catch( BooleanFormatoException e ) {
			throw new PermissaoRemocaoException();
		}		
	}
	
	public void carregaPermissaoGrupoResponse( PermissaoGrupoResponse resp, PermissaoGrupo pg ) {
		resp.setId( pg.getId() );
		resp.setRecurso( pg.getRecurso().getNome() );
			
		resp.setLeitura( String.valueOf( pg.isLeitura() ) );
		resp.setEscrita( String.valueOf( pg.isEscrita() ) );
		resp.setRemocao( String.valueOf( pg.isRemocao() ) );			
	}
	
	public PermissaoGrupoResponse novoPermissaoGrupoResponse() {
		return new PermissaoGrupoResponse();
	}
	
	public PermissaoGrupo novoPermissaoGrupo() {
		return new PermissaoGrupo();
	}
	
	public PermissaoGrupo novoINIPermissaoGrupo( UsuarioGrupo g, Recurso r ) {
		PermissaoGrupo pg = new PermissaoGrupo();
		pg.setGrupo( g ); 
		pg.setRecurso( r );
		pg.setLeitura( false );
		pg.setEscrita( false );
		pg.setRemocao( false );
		return pg;
	}

}
