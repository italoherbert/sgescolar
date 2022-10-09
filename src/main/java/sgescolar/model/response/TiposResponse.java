package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TiposResponse {
	private TipoArrayResponse cursoModalidades;
	private TipoArrayResponse escolaridades;
	private TipoArrayResponse estadosCivis;
	private TipoArrayResponse nacionalidades;
	private TipoArrayResponse sexos;
	private TipoArrayResponse usuarioPerfis;
	private TipoArrayResponse funcionarioFuncoes;
	private TipoArrayResponse racas;
	private TipoArrayResponse religioes;
	private TipoArrayResponse periodos;
}
