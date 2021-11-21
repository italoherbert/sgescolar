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
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ano_letivo")
public class AnoLetivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column
	private int ano;
	
	@ManyToOne
	@JoinColumn(name="escola_id")
	private Escola escola;
	
	@Column
	private boolean diasLetivosCalculados;
	
	@OneToMany(mappedBy="anoLetivo", cascade=CascadeType.ALL)
	private List<Periodo> periodosLetivos;
	
	@OneToMany(mappedBy="anoLetivo", cascade=CascadeType.ALL)
	private List<Feriado> feriados;
	
}
	
