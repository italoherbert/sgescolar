package sgescolar.model;

import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.AvaliacaoMetodo;
import sgescolar.enums.tipos.CursoModalidade;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="curso")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private String descricao;
			
	@Column
	private int quantidadeAulasDia;
	
	@Column
	private int cargaHoraria;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CursoModalidade modalidade;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AvaliacaoMetodo avaliacaoMetodo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="escola_id")
	private Escola escola;
	
	@OneToMany(mappedBy="curso", cascade=CascadeType.ALL)
	private List<Serie> series;
	
}
