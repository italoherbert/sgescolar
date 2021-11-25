package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ProfessorBuilder;
import sgescolar.model.Pessoa;
import sgescolar.model.Professor;
import sgescolar.model.Usuario;
import sgescolar.model.request.FiltraProfessoresRequest;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.model.response.ProfessorResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.service.dao.PessoaDAO;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class ProfessorService {
		
	@Autowired
	private ProfessorRepository professorRepository;
	
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
	
	public void verificaSeDono( Long logadoUID, Long professorId ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor p = prOp.get();
		Long uid = p.getFuncionario().getUsuario().getId();
		
		if ( logadoUID != uid )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
	
	@Transactional
	public void registraProfessor( SaveProfessorRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getFuncionario().getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );

		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getFuncionario().getUsuario().getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
		
		Professor pr = professorBuilder.novoProfessor();
		professorBuilder.carregaProfessor( pr, request );		
		professorRepository.save( pr );
		
		usuarioDAO.salvaUsuarioGrupoMaps( pr.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}
	
	@Transactional
	public void alteraProfessor( Long professorId, SaveProfessorRequest request ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor pr = prOp.get();
		
		pessoaDAO.validaAlteracao( pr.getFuncionario().getPessoa(), request.getFuncionario().getPessoa() );
		usuarioDAO.validaAlteracao( pr.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 

		professorBuilder.carregaProfessor( pr, request );		
		professorRepository.save( pr );

		usuarioDAO.salvaUsuarioGrupoMaps( pr.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 		
	}
	
	public List<ProfessorResponse> filtraProfessores( FiltraProfessoresRequest request ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Professor> professores = professorRepository.filtra( nomeIni );
		
		List<ProfessorResponse> lista = new ArrayList<>();
		for( Professor pr : professores ) {
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
	
}
