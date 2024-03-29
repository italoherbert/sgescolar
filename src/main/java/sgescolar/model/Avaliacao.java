package sgescolar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import sgescolar.enums.tipos.AvaliacaoMetodo;
import sgescolar.enums.tipos.AvaliacaoTipo;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="avaliacao")
public class Avaliacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	  
	@Column
	private double peso;
	
	@Column
	private Date dataAgendamento;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AvaliacaoMetodo avaliacaoMetodo;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AvaliacaoTipo avaliacaoTipo;
	
	@Column
	private boolean resultadoDisponivel;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="turma_disciplina_id")
	private TurmaDisciplina turmaDisciplina;
	
	@OneToMany(mappedBy="avaliacao", orphanRemoval = true, cascade=CascadeType.ALL)
	private List<AvaliacaoResultado> resultados;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
}