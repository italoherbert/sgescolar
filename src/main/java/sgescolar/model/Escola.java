package sgescolar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="escola")
public class Escola {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column
	private String nome;
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name="endereco_local_id")
	private EnderecoLocal enderecoLocal;	
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name="contato_info_id")	 
	private ContatoInfo contatoInfo;
	
	@ManyToOne
	@JoinColumn(name="instituicao_id")
	private Instituicao instituicao;
	
	@OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
	private List<AnoLetivo> anosLetivos;
	
	@OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
	private List<Curso> cursos;
	
	@OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
	private List<Secretario> secretarios;
		
}
