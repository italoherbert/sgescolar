
import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class SerieTelaComponent extends RootFormComponent {
			
	onChangeCurso = () => {};	
										
	constructor() {
		super( 'serie_filtro_form', 'mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'instituicao' );
				selectService.carregaEscolasSelect( instituicaoId, 'escolas_select', { 
					onchange : () => {
						let escolaId = instance.getFieldValue( 'escola' );
						selectService.carregaCursosSelect( escolaId, 'cursos_select', { 
							onchange : instance.onChangeCurso
						} );
					} 
				} );				
			}
		} );							
	}
			
	getJSON() {
		return {
			descricaoIni : super.getFieldValue( 'descricaoini' )
		}
	}		
				
}
