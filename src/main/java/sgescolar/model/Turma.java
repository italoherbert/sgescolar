package sgescolar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
	@ManyToOne
	@JoinColumn(name="serie_id")
	private Serie serie;
	
	@ManyToOne
	@JoinColumn(name="ano_letivo_id")
	private AnoLetivo anoLetivo;

}
