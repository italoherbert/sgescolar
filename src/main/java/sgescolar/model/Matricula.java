package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="matricula")
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String numero;
	
	@ManyToOne
	@JoinColumn(name="turma_id")
	private Turma turma;
	
	@ManyToOne
	@JoinColumn(name="aluno_id")
	private Aluno aluno;
	
	@OneToMany(mappedBy="matricula", cascade=CascadeType.REMOVE)
	private List<AlunoFrequencia> diasLetivosAulas;
}
