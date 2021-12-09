package sgescolar.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	private String id;
	
	@Lob
	private String habilidade;
	
}
