
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
				
	paraInstituicaoForm() {
		sistema.carregaPagina( 'instituicao-form' );
	}
	
}
export const instituicaoDetalhes = new InstituicaoDetalhesService(); 