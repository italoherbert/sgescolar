package sgescolar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import sgescolar.enums.tipos.PeriodoLetivoTipo;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="periodo_letivo")
public class PeriodoLetivo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Temporal(TemporalType.DATE)
	private Date lancamentoDataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date lancamentoDataFim;
	
	@Enumerated(EnumType.STRING)
	private PeriodoLetivoTipo tipo;
	
	@ManyToOne
	@JoinColumn( name = "ano_letivo_id")
	private AnoLetivo anoLetivo;
	
	@OneToMany(mappedBy="bimestre", cascade=CascadeType.ALL)
	private List<DiaLetivo> diasLetivos;
	
}
