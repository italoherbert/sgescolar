package sgescolar.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.PlanejamentoTipo;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="planejamento")
public class Planejamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String metodologia;
	
	@Column
	private String metodosAvaliacao;

	@Column
	private String recursos;

	@Column
	private String referencias;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PlanejamentoTipo tipo;
		
	@OneToMany(mappedBy="planejamento", cascade=CascadeType.ALL)
	private List<PlanejamentoObjetivo> objetivos;

	@OneToMany(mappedBy="planejamento", cascade=CascadeType.ALL)
	private List<PlanejamentoConteudo> conteudos;
	
	@OneToMany(mappedBy="planejamento", cascade=CascadeType.ALL)
	private List<PlanejamentoAnexo> anexos;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="professor_alocacao_id")
	private ProfessorAlocacao professorAlocacao;
	
}
