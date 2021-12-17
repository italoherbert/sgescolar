package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="professor") 
public class Professor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
			
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="funcionario_id")
	private Funcionario funcionario;
		
	@OneToMany(mappedBy="professor", cascade=CascadeType.ALL)
	private List<ProfessorAlocacao> professorAlocacoes;
		
	@OneToMany(mappedBy="professor", orphanRemoval = true, cascade=CascadeType.ALL) 
	private List<ProfessorDiploma> diplomas;
	
}
