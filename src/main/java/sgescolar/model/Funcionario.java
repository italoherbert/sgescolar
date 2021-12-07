package sgescolar.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.Escolaridade;
import sgescolar.enums.tipos.FuncionarioFuncao;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="funcionario") 
public class Funcionario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String codigoInep;
	
	@Column
	@Enumerated(EnumType.STRING)	
	private Escolaridade escolaridade;
	
	@Column
	@Enumerated(EnumType.STRING)
	private FuncionarioFuncao funcao;
	
	@Column
	private int cargaHoraria;
	
	@Column
	private boolean escolaFunc;
		
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="pessoa_id")
	private Pessoa pessoa;
		
}
