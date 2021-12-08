
import {sistema} from '../../../../sistema/Sistema.js';

import DisciplinaDetalhesComponent from './DisciplinaDetalhesComponent.js';

export default class DisciplinaDetalhesService {			
		
	constructor() {
		this.component = new DisciplinaDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			disciplinaId : this.params.disciplinaId		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'disciplina-form', { disciplinaId : this.params.disciplinaId, op : 'editar', titulo : "Edição de disciplina" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'disciplina-tela' );
	}
	
}
export const disciplinaDetalhes = new DisciplinaDetalhesService(); 