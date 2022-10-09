
import {sistema} from '../../../../sistema/Sistema.js';

import AvaliacaoDetalhesComponent from './AvaliacaoDetalhesComponent.js';

export default class AvaliacaoDetalhesService {			
		
	constructor() {
		this.component = new AvaliacaoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			avaliacaoId : this.params.avaliacaoId		
		} );	
		
		this.component.carregaHTML();			
	}
		
	editaAgendamentoAvaliacao() {
		sistema.carregaPagina( 'agendamento-avaliacao-form', { avaliacaoId : this.params.avaliacaoId, op : 'editar' } );
	}
			
	paraTela() {
		sistema.carregaPagina( 'avaliacao-tela' );
	}
	
}
export const avaliacaoDetalhes = new AvaliacaoDetalhesService(); 