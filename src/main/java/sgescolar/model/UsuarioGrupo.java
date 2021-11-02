package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.model.enums.UsuarioPerfil;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private UsuarioPerfil perfil;
	
	@OneToMany(mappedBy="grupo")
	private List<Usuario> usuario;
	
	@OneToMany(mappedBy="grupo", cascade=CascadeType.ALL)	
	private List<PermissaoGrupo> permissaoGrupos;
	
}
