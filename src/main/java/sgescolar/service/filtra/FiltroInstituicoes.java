package sgescolar.service.filtra;

import java.util.List;

import sgescolar.model.Instituicao;
import sgescolar.repository.InstituicaoRepository;

public interface FiltroInstituicoes {

	public List<Instituicao> filtra( InstituicaoRepository repository, String cnpj, String razaoSocialIni );	
	
}
