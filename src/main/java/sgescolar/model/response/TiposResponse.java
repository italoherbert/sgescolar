package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TiposResponse {
	private TipoResponse cursoModalidades;
	private TipoResponse escolaridades;
	private TipoResponse estadosCivis;
	private TipoResponse nacionalidades;
	private TipoResponse sexos;
	private TipoResponse usuarioPerfis;
	private TipoResponse funcionarioFuncoes;
	private TipoResponse racas;
	private TipoResponse religioes;
	private TipoResponse periodos;
}
