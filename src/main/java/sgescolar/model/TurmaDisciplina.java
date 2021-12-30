package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="turma_disciplina")
public class TurmaDisciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="turma_id")
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="disciplina_id")
	private Disciplina disciplina;
	
	@OneToOne(mappedBy = "turmaDisciplina", cascade=CascadeType.ALL)
	private ProfessorAlocacao professorAlocacao;
	
	@OneToMany(mappedBy="turmaDisciplina", orphanRemoval = true, cascade=CascadeType.ALL)
	private List<HorarioAula> horarioAulas;
	
	@OneToMany(mappedBy="turmaDisciplina", cascade = CascadeType.ALL)
	private List<Avaliacao> avaliacoes;
		
}
