package sgescolar.service.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.builder.PermissaoGrupoBuilder;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Recurso;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SaveUsuarioGrupoRequest;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PermissaoGrupoRepository;
import sgescolar.repository.RecursoRepository;
import sgescolar.service.ServiceException;

@Component
public class UsuarioGrupoDAO {

	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private PermissaoGrupoRepository permissaoGrupoRepository;
			
	@Autowired
	private PermissaoGrupoBuilder permissaoGrupoBuilder;

	@Transactional
	public void savePermissaoGrupos( UsuarioGrupo ug, SaveUsuarioGrupoRequest request ) throws ServiceException {
		List<PermissaoGrupo> pgrupos = ug.getPermissaoGrupos();
		int permGruposSize = ( pgrupos == null ? 0 : pgrupos.size() );
				
		List<SavePermissaoGrupoRequest> permissoes = request.getPermissaoGrupos();
		for( SavePermissaoGrupoRequest pgreq : permissoes ) {
			PermissaoGrupo pg = null;
			for( int i = 0; pg == null && i < permGruposSize; i++ ) {
				PermissaoGrupo pg2 = pgrupos.get( i );
				Recurso r = pg2.getRecurso();
				if ( r.getNome().equalsIgnoreCase( request.getNome() ) )
					pg = pg2;
			}
				
			if ( pg == null ) {
				Optional<Recurso> rop = recursoRepository.buscaPorNome( pgreq.getRecurso() ); 
				if ( !rop.isPresent() )
					throw new ServiceException( ServiceErro.RECURSO_NOME_NAO_ENCONTRADO, pgreq.getRecurso() );
				
				Recurso r = rop.get();				
				pg = permissaoGrupoBuilder.novoINIPermissaoGrupo( ug, r );
			}
			
			permissaoGrupoBuilder.carregaPermissaoGrupo( pg, pgreq );			
			permissaoGrupoRepository.save( pg );			
		}
	}
	
}
