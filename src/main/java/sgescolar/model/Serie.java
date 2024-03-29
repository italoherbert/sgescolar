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
@Table(name="serie")
public class Serie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private String descricao;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="curso_id")
	private Curso curso;
	
	@OneToMany(mappedBy="serie", cascade=CascadeType.ALL)
	private List<Turma> turmas;
	
	@OneToMany(mappedBy="serie", cascade=CascadeType.ALL)
	private List<Disciplina> disciplinas;
	
}
