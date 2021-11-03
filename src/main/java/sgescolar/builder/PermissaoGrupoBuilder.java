package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PermissaoEnumManager;
import sgescolar.enums.tipos.TipoPermissao;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Recurso;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.model.response.PermissaoGrupoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PermissaoGrupoBuilder {

	@Autowired
	private PermissaoEnumManager permissaoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaPermissao( PermissaoGrupo pg, SavePermissaoRequest req ) { 				
		TipoPermissao tipoPerm = permissaoEnumManager.getEnum( req.getTipo() );				
		switch( tipoPerm ) {
			case LEITURA:				
				pg.setLeitura( conversorUtil.stringParaBoolean( req.getValor() ) );
				break;
			case ESCRITA:
				pg.setEscrita( conversorUtil.stringParaBoolean( req.getValor() ) );				
				break;
			case REMOCAO:
				pg.setRemocao( conversorUtil.stringParaBoolean( req.getValor() ) );				
				break;
		}
	}
		
	public void carregaPermissaoGrupo( PermissaoGrupo pg, SavePermissaoGrupoRequest req ) {		
		pg.setLeitura( conversorUtil.stringParaBoolean( req.getLeitura() ) );
		pg.setEscrita( conversorUtil.stringParaBoolean( req.getEscrita() ) );
		pg.setRemocao( conversorUtil.stringParaBoolean( req.getRemocao() ) );					
	}
	
	public void carregaPermissaoGrupoResponse( PermissaoGrupoResponse resp, PermissaoGrupo pg ) {
		resp.setId( pg.getId() );
		resp.setRecurso( pg.getRecurso().getNome() );
			
		resp.setLeitura( conversorUtil.booleanParaString( pg.isLeitura() ) );
		resp.setEscrita( conversorUtil.booleanParaString( pg.isEscrita() ) );
		resp.setRemocao( conversorUtil.booleanParaString( pg.isRemocao() ) );			
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
