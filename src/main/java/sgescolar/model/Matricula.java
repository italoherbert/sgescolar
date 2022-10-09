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
@Table(name="matricula")
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String numero;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataEncerramento;
	
	@Column
	private boolean encerrada;
	
	@ManyToOne
	@JoinColumn(name="turma_id")
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="aluno_id")
	private Aluno aluno;		
	
	@OneToMany(mappedBy="matricula", cascade=CascadeType.REMOVE)
	private List<AlunoFrequencia> alunoFrequencias;
	
	@OneToMany(mappedBy="matricula", cascade=CascadeType.REMOVE)	
	private List<AvaliacaoExterna> avaliacoesExternas;
}
