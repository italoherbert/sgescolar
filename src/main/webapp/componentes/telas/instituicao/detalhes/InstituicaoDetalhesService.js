
import {sistema} from '../../../../sistema/Sistema.js';

import InstituicaoDetalhesComponent from './InstituicaoDetalhesComponent.js';

export default class InstituicaoDetalhesService {			
		
	constructor() {
		this.component = new InstituicaoDetalhesComponent();
	}	
		
	onCarregado() {
		this.component.configura( {
			instituicaoId : this.params.instituicaoId,
			op : this.params.op
		} );			
		this.component.carregaHTML();			
	}
				
	paraEdicaoForm() {
		sistema.carregaPagina( 'instituicao-form', { op : 'editar', instituicaoId : this.params.instituicaoId, titulo : 'Edição de instituição' } );
	}
	
	paraTela() {
		sistema.carregaPagina( 'instituicao-tela' );
	}
	
}
export const instituicaoDetalhes = new InstituicaoDetalhesService(); 