package sgescolar.model;

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
@Table(name="instituicao")
public class Instituicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String cnpj;
	
	@Column
	private String razaoSocial;
	
	@OneToOne
	@JoinColumn(name="endereco_id")	
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name="contatoinfo_id")
	private ContatoInfo contatoInfo;
	
}
