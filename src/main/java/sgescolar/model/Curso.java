package sgescolar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.model.enums.CursoModalidade;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="curso")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private String nome;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CursoModalidade modalidade;
	
	@ManyToOne
	@JoinColumn(name="escola_id")
	private Escola escola;
	
}
