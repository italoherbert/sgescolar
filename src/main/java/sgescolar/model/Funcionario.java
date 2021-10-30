package sgescolar.model;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.model.enums.Escolaridade;

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
	private int cargaHoraria;
	
	@Column
	private boolean escolaFunc;
	
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name="pessoa_id")
	private Pessoa pessoa;
	
	@OneToOne(optional = true)
	@JoinColumn(name="escola_id")
	private Escola escola;
	
}
