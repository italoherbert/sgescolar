package sgescolar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="professor_alocacao")
public class ProfessorAlocacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private boolean ativo;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataFim;
		
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="turma_disciplina_id")
	private TurmaDisciplina turmaDisciplina;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="professor_id")
	private Professor professor;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="escola_id")
	private Escola escola;
		
	@OneToMany(mappedBy="professorAlocacao", cascade=CascadeType.ALL)
	private List<Planejamento> planejamentos;
	
}
