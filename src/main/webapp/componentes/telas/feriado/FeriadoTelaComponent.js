
import RootFormComponent from '../../component/RootFormComponent.js';

import {selectService} from '../../service/SelectService.js';

export default class FeriadoTelaFormComponent extends RootFormComponent {
			
	onChangeAnoLetivo = () => {};		
				
	constructor( formNome, compELIDSufixo ) {
		super( formNome, '', 'anoletivo_select_mensagem_el' );
	}
				
	onHTMLCarregado() {
		selectService.carregaEscolasSelect( 'escolas_select', { onchange : (e) => this.onChangeEscola( e ) } );		
	}
	
	onChangeEscola( e ) {
		let escolaId = super.getFieldValue( 'escola' );
		selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', { onchange : (e) => this.onChangeAnoLetivo( e ) } );				
	}
	
	getEscolaID() {
		return super.getFieldValue( 'escola' );
	}
	
	getAnoLetivoID() {
		return super.getFieldValue( 'anoletivo' );
	}
	
}