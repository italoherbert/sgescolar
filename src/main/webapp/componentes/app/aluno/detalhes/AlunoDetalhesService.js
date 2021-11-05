
import {sistema} from '../../../../sistema/Sistema.js';

import AlunoDetalhesComponent from './AlunoDetalhesComponent.js';

export default class AlunoDetalhesService {			
		
	constructor() {
		this.component = new AlunoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			alunoId : this.params.alunoId,
			classes : {
				campo : 'bg-light border border-success p-2 rounded m-2',
				rotulo : 'text-dark text-capitalize field-rotulo',
				valor : 'text-primary font-weight-light'
			}
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraFormEditar() {				
		sistema.carregaPagina( 'aluno-form', { alunoId : this.params.alunoId, op : 'editar', titulo : "Edição de aluno" } );
	}
			
	paraAlunosTela() {
		sistema.carregaPagina( 'aluno-tela' );
	}
	
}
export const alunoDetalhes = new AlunoDetalhesService(); 