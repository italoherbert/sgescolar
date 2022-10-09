
import {sistema} from '../../../../sistema/Sistema.js';

import TurmaDetalhesComponent from './TurmaDetalhesComponent.js';

export default class TurmaDetalhesService {			
		
	constructor() {
		this.component = new TurmaDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			turmaId : this.params.turmaId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'turma-form', { turmaId : this.params.turmaId, op : 'editar', titulo : "Edição de turma" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'turma-tela' );
	}
	
}
export const turmaDetalhes = new TurmaDetalhesService(); 