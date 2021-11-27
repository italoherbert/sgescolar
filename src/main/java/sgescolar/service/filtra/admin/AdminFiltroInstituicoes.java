package sgescolar.service.filtra.admin;

import java.util.List;

import sgescolar.model.Instituicao;
import sgescolar.repository.InstituicaoRepository;
import sgescolar.service.filtra.FiltroInstituicoes;

public class AdminFiltroInstituicoes implements FiltroInstituicoes {
	
	@Override
	public List<Instituicao> filtra(InstituicaoRepository repository, String cnpj, String razaoSocialIni) {
		return repository.filtra( cnpj, razaoSocialIni);
	}
	

}
