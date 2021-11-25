
import {selectService} from '../../service/SelectService.js';

import RootFormComponent from '../../component/RootFormComponent.js';

export default class TurmaDisciplinaListagemFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'turma_disciplina_listagem_form', 'lista-mensagem-el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		const instance = this;
		selectService.carregaEscolasSelect( 'lst_escolas_select', { 
			onchange : () => {
				let escolaId = instance.getFieldValue( 'lst_escola' );
				selectService.carregaAnosLetivosSelect( escolaId, 'lst_anosletivos_select', {
					onchange : () => {
						let anoLetivoId = instance.getFieldValue( 'lst_anoletivo' );
						selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'lst_turmas_select' );
					}
				} );
			}
		} );						
	}
			
	limpaForm() {
		super.setFieldValue( 'lst_escola', "0" );		
		super.setFieldValue( 'lst_anoletivo', "0" );		
		super.setFieldValue( 'lst_turma', "0" );		
	}		
}
