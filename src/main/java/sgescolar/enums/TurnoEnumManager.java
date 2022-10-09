package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.Turno;

@Component
public class TurnoEnumManager  extends AbstractEnumManager<Turno> { 

	@Override
	public Turno[] values() {
		return Turno.values();
	}

	@Override
	protected String label( Turno valor ) {
		return valor.label();
	}
		
}