package sgescolar.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="pessoa_pai_ou_mae")
public class PessoaPaiOuMae {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column	
	private boolean desconhecido;
	
	@Column
	private boolean falecido;
			
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="pessoa_id") 
	private Pessoa pessoa;
	
}