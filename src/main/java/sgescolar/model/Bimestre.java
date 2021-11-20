package sgescolar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="bimestre")
public class Bimestre {

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
	
	@OneToOne
	@JoinColumn( name = "ano_letivo_id")
	private AnoLetivo anoLetivo;
	
	@OneToMany(mappedBy="bimestre", cascade=CascadeType.ALL)
	private List<DiaLetivo> diasLetivos;
	
}
