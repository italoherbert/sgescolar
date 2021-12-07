package sgescolar.model;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="horario_aula")
public class HorarioAula {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int semanaDia;
		
	@Column
	private int numeroAula;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="turma_disciplina_id")
	private TurmaDisciplina turmaDisciplina;
	
	@OneToMany(mappedBy = "horarioAula", cascade = CascadeType.REMOVE)
	private List<ListaFrequencia> listaFrequencia;
	
}
