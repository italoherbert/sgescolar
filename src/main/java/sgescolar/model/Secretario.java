package sgescolar.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="aluno") 
public class Secretario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
			
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="funcionario_id")
	private Funcionario funcionario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="escola_id")
	private Escola escola;
	
}
