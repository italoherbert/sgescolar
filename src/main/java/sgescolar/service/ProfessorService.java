package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ProfessorBuilder;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.Pessoa;
import sgescolar.model.Professor;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.FiltraProfessoresRequest;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.model.response.ProfessorResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.UsuarioGrupoRepository;

@Service
public class ProfessorService {
	
	private final String PERFIL = UsuarioPerfil.PROFESSOR.name();
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
		
	@Autowired
	private ProfessorBuilder professorBuilder;
	
	public void verificaSeDono( Long logadoUID, Long professorId ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		boolean ehDono = professorRepository.verificaSeDono( logadoUID );
		if ( !ehDono )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
	
	public void registraProfessor( SaveProfessorRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getFuncionario().getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
				
		Optional<UsuarioGrupo> ugOp = usuarioGrupoRepository.buscaPorPerfil( PERFIL );	
		if ( !ugOp.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
		
		UsuarioGrupo ugrupo = ugOp.get();
		
		Professor pr = professorBuilder.novoProfessor( ugrupo );
		professorBuilder.carregaProfessor( pr, request );
		
		professorRepository.save( pr );						
	}
	
	public void alteraProfessor( Long professorId, SaveProfessorRequest request ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor pr = prOp.get();
		
		String professorCpfAtual = pr.getFuncionario().getPessoa().getCpf();		
		String professorCpfNovo = request.getFuncionario().getPessoa().getCpf();
		
		if ( !professorCpfNovo.equalsIgnoreCase( professorCpfAtual ) )
			if ( pessoaRepository.buscaPorNome( professorCpfNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
				
		professorBuilder.carregaProfessor( pr, request );		
		professorRepository.save( pr );		
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
