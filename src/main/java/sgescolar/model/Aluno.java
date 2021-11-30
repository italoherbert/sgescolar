package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="aluno") 
public class Aluno {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pessoa_pai_id")
	private PessoaPaiOuMae pai;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pessoa_mae_id")
	private PessoaPaiOuMae mae;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usuario_id") 
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pessoa_id")
	private Pessoa pessoa;
	
	@OneToMany(mappedBy="aluno", cascade=CascadeType.REMOVE)
	private List<Matricula> matriculas;
	
}