package sgescolar.model;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgescolar.enums.tipos.PeriodoTipo;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="periodo")
public class Periodo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String descricao;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Temporal(TemporalType.DATE)
	private Date lancamentoDataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date lancamentoDataFim;
	
	@Enumerated(EnumType.STRING)
	private PeriodoTipo tipo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ano_letivo_id")
	private AnoLetivo anoLetivo;
		
}
