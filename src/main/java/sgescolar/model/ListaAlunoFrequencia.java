package sgescolar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.Turno;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="lista_aluno_frequencia")
public class ListaAlunoFrequencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
		
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataDia;
	
	@Column
	private int numeroAula;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Turno turno;
			
	@OneToMany(mappedBy="listaFrequencia", cascade=CascadeType.ALL)
	private List<AlunoFrequencia> frequencias;
	
	@ManyToOne
	@JoinColumn(name="turma_id")
	private Turma turma;
	
}
