
import {sistema} from '../../../../sistema/Sistema.js';

import AlunoDetalhesComponent from './AlunoDetalhesComponent.js';

export default class AlunoDetalhesService {			
		
	constructor() {
		this.component = new AlunoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			alunoId : this.params.alunoId,
			
			pai_detalhes_titulo: 'Detalhes do pai do aluno',
			mae_detalhes_titulo: 'Detalhes da mãe do aluno',	
			responsavel_detalhes_titulo: 'Detalhes do responsável do aluno'		
		} );	
		
		this.component.carregaHTML();			
	}
	
	paraEdicaoForm() {				
		sistema.carregaPagina( 'aluno-form', { alunoId : this.params.alunoId, op : 'editar', titulo : "Edição de aluno" } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'aluno-tela' );
	}
	
}
export const alunoDetalhes = new AlunoDetalhesService(); 