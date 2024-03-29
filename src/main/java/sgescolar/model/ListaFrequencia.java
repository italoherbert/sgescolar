package sgescolar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="lista_frequencia")
public class ListaFrequencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
		
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataDia;
					
	@OneToMany(mappedBy="listaFrequencia", cascade=CascadeType.ALL)
	private List<AlunoFrequencia> frequencias;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="horario_aula_id")
	private HorarioAula horarioAula;
	
}
