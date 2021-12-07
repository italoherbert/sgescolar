package sgescolar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.FrequenciaModalidade;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="aluno_frequencia")
public class AlunoFrequencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private boolean estevePresente;
		
	@Column
	@Enumerated(EnumType.STRING)
	private FrequenciaModalidade modalidade;
		
	@ManyToOne
	@JoinColumn(name="matricula_id")
	private Matricula matricula;
	
	@ManyToOne
	@JoinColumn(name="lista_frequencia_id")
	private ListaAlunoFrequencia listaFrequencia;
	
}
