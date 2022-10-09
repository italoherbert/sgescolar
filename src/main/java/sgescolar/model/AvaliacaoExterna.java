package sgescolar.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="avaliacao_externa")
public class AvaliacaoExterna {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	private Long id;
		
	@Autowired
	private double media;
	
	@Autowired
	private double peso;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="matricula_id")
	private Matricula matricula;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="turma_disciplina_id")
	private TurmaDisciplina turmaDisciplina;

}