package sgescolar.model;

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
import sgescolar.enums.tipos.Turno;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="turma")
public class Turma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private String descricao;
		
	@Column
	@Enumerated(EnumType.STRING)
	private Turno turno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="serie_id")
	private Serie serie;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ano_letivo_id")
	private AnoLetivo anoLetivo;
	
	@OneToMany(mappedBy="turma", cascade=CascadeType.REMOVE)
	private List<TurmaDisciplina> turmaDisciplinas;

	@OneToMany(mappedBy="turma", cascade=CascadeType.REMOVE)
	private List<Matricula> matriculas;		
	
}
