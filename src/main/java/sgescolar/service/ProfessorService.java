package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sgescolar.builder.ProfessorBuilder;
import sgescolar.builder.ProfessorDiplomaBuilder;
import sgescolar.logica.IDUnicoValidator;
import sgescolar.model.Pessoa;
import sgescolar.model.Professor;
import sgescolar.model.ProfessorDiploma;
import sgescolar.model.Usuario;
import sgescolar.model.request.SaveProfessorDiplomaRequest;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.model.request.filtro.FiltraProfessoresRequest;
import sgescolar.model.response.ProfessorResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.ProfessorDiplomaRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.service.dao.PessoaDAO;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class ProfessorService {
		
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private ProfessorDiplomaRepository professorDiplomaRepository;
	
	@Autowired
	private ProfessorDiplomaBuilder professorDiplomaBuilder;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
			
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private PessoaDAO pessoaDAO;
	
	@Autowired
	private ProfessorBuilder professorBuilder;
			
	@Transactional
	public void registraProfessor( SaveProfessorRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getFuncionario().getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );

		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getFuncionario().getUsuario().getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
							
		Professor pr = professorBuilder.novoProfessor();		
		this.salvaProfessor( pr, request );
	}
	
	@Transactional
	public void alteraProfessor( Long professorId, SaveProfessorRequest request ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor pr = prOp.get();
		
		pessoaDAO.validaAlteracao( pr.getFuncionario().getPessoa(), request.getFuncionario().getPessoa() );
		usuarioDAO.validaAlteracao( pr.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 

		this.salvaProfessor( pr, request );		
	}
	
	@Transactional
	public void salvaProfessor( Professor pr, SaveProfessorRequest request ) throws ServiceException {
		professorBuilder.carregaProfessor( pr, request );		
		professorRepository.save( pr );
		
		List<ProfessorDiploma> diplomas = pr.getDiplomas();
		if ( diplomas != null )
			diplomas.clear();
					
		List<SaveProfessorDiplomaRequest> reqDiplomas = request.getDiplomas(); 
		for( SaveProfessorDiplomaRequest reqdip : reqDiplomas ) {
			ProfessorDiploma diploma = professorDiplomaBuilder.novoProfessorDiploma( pr );
			professorDiplomaBuilder.carregaProfessorDiploma( diploma, reqdip );
			professorDiplomaRepository.save( diploma );
		}
		
		usuarioDAO.salvaUsuarioGrupoMaps( pr.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() );
	}
	
	public List<ProfessorResponse> filtraProfessores( FiltraProfessoresRequest request, Pageable p ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Professor> professores = professorRepository.filtra( nomeIni, p );
		
		List<ProfessorResponse> lista = new ArrayList<>();
		for( Professor pr : professores ) {
			ProfessorResponse resp = professorBuilder.novoProfessorResponse();
			professorBuilder.carregaProfessorResponse( resp, pr );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<ProfessorResponse> listaProfessoresPorTurma( Long turmaId ) {	
		List<Professor> professores = professorRepository.listaPorTurma( turmaId );
		
		List<ProfessorResponse> lista = new ArrayList<>();
		
		IDUnicoValidator unicoValidator = new IDUnicoValidator();
		
		for( Professor pr : professores ) {
			if ( !unicoValidator.verificaSeUnico( pr.getId() ) )
				continue;
			
			ProfessorResponse resp = professorBuilder.novoProfessorResponse();
			professorBuilder.carregaProfessorResponse( resp, pr );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public ProfessorResponse buscaProfessor( Long professorId ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor pr = prOp.get();
		
		ProfessorResponse resp = professorBuilder.novoProfessorResponse();
		professorBuilder.carregaProfessorResponse( resp, pr );		
		return resp;
	}
	
	public void deletaProfessor( Long professorId )  throws ServiceException {
		boolean existe = professorRepository.existsById( professorId );
		if ( !existe )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		professorRepository.deleteById( professorId ); 
	}
	
	public void verificaSeProfessorAlocado( Long professorId ) throws ServiceException {
		Optional<Professor> profOp = professorRepository.findById( professorId );
		if ( !profOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor p = profOp.get();
		if ( p.getProfessorAlocacoes().isEmpty() )
			throw new ServiceException( ServiceErro.PROFESSOR_SEM_VINCULO_COM_DISCIPLINAS );
	}
	
}
