package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.EstadoCivilEnumManager;
import sgescolar.enums.NacionalidadeEnumManager;
import sgescolar.enums.RacaEnumManager;
import sgescolar.enums.ReligiaoEnumManager;
import sgescolar.enums.SexoEnumManager;
import sgescolar.model.request.SavePessoaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class PessoaValidator {
	
	@Autowired
	private EstadoCivilEnumManager estadoCivilEnumManager;
	
	@Autowired
	private NacionalidadeEnumManager nacionalidadeEnumManager;
	
	@Autowired
	private SexoEnumManager sexoEnumManager;
	
	@Autowired
	private RacaEnumManager racaEnumManager;
	
	@Autowired
	private ReligiaoEnumManager religiaoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SavePessoaRequest request ) throws ValidacaoException {
		if ( request.getNome() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );		
		if ( request.getNome().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
		
		if ( request.getCpf() == null )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );		
		if ( request.getCpf().isBlank() )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		
		if ( request.getRg() == null )
			throw new ValidacaoException( ValidacaoErro.RG_OBRIGATORIO );		
		if ( request.getRg().isBlank() )
			throw new ValidacaoException( ValidacaoErro.RG_OBRIGATORIO );
				
		if ( !sexoEnumManager.enumValida( request.getSexo() ) )
			throw new ValidacaoException( ValidacaoErro.SEXO_NAO_RECONHECIDO );
		if ( !estadoCivilEnumManager.enumValida( request.getEstadoCivil() ) )
			throw new ValidacaoException( ValidacaoErro.ESTADO_CIVIL_NAO_RECONHECIDO );
		if ( !nacionalidadeEnumManager.enumValida( request.getNacionalidade() ) )
			throw new ValidacaoException( ValidacaoErro.NACIONALIDADE_NAO_RECONHECIDA );
		if ( !racaEnumManager.enumValida( request.getRaca() ) )
			throw new ValidacaoException( ValidacaoErro.RACA_NAO_RECONHECIDA );
		if ( !religiaoEnumManager.enumValida( request.getReligiao() ) )
			throw new ValidacaoException( ValidacaoErro.RELIGIAO_NAO_RECONHECIDA );
				
		if ( !validatorUtil.dataValida( request.getDataNascimento() ) )
			throw new ValidacaoException( ValidacaoErro.DATA_NASCIMENTO_INVALIDA );		
	}
	
}
