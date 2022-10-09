package sgescolar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="bncc_habilidade")
public class BNCCHabilidade {
	
	@Id
	private String codigo;
	
	@Column(columnDefinition = "text")		
	private String habilidade;
	
}
