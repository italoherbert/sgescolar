package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TiposResponse {
	private String[] cursoModalidades;
	private String[] escolaridades;
	private String[] estadosCivis;
	private String[] nacionalidades;
	private String[] permissoes;
	private String[] sexos;
	private String[] usuarioPerfis;
	private String[] racas;
	private String[] religioes;
}
