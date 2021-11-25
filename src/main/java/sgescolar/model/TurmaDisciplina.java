package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="turma_disciplina")
public class TurmaDisciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="turma_id")
	private Turma turma;
	
	@ManyToOne
	@JoinColumn(name="disciplina_id")
	private Disciplina disciplina;
	
	@OneToMany(mappedBy="turmaDisciplina", cascade=CascadeType.ALL)
	private List<ProfessorAlocacao> professorAlocacoes;
	
}
