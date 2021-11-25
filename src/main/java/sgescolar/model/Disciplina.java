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
@Table(name="disciplina")
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="serie_id")
	private Serie serie;
	
	@OneToMany(mappedBy="disciplina", cascade = CascadeType.ALL)
	private List<TurmaDisciplina> turmaDisciplinas;
	
}
