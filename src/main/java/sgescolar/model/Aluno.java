package sgescolar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@OneToOne(optional = true)
	@JoinColumn(name="pessoa_pai_id")
	private PessoaMaeOuPai pai;
	
	@OneToOne(optional = true)
	@JoinColumn(name="pessoa_mae_id")
	private PessoaMaeOuPai mae;
	
	@OneToOne
	@JoinColumn(name="usuario_id") 
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name="pessoa_id")
	private Pessoa pessoa;
	
}