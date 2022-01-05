package sgescolar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.AvaliacaoConceito;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="avaliacao_resultado")
public class AvaliacaoResultado {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	@Column
	private double nota;
	
	@Column
	private AvaliacaoConceito conceito;
	
	@Column
	private String descricao;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="matricula_id")
	private Matricula matricula;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="avaliacao_id")
	private Avaliacao avaliacao;
		
}
