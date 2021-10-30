package sgescolar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.model.enums.EstadoCivil;
import sgescolar.model.enums.Nacionalidade;
import sgescolar.model.enums.Raca;
import sgescolar.model.enums.Religiao;
import sgescolar.model.enums.Sexo;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
		
	@Column
	private String nomeSocial;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column
	private String cpf;
	
	@Column
	private String rg;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Column
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Nacionalidade nacionalidade;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Raca raca;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Religiao religiao;
	
	@OneToOne
	@JoinColumn(name="endereco_id")
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name="contatoinfo_id")
	private ContatoInfo contatoInfo; 
	
}
