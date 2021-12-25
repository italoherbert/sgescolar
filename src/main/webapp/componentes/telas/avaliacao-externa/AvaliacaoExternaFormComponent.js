
import {conversor} from '../../../sistema/util/Conversor.js';

import {selectService} from '../../service/SelectService.js';
import {perfilService} from '../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../component/RootFormComponent.js';

export default class AvaliacaoExternaFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'avaliacao_externa_form', 'mensagem-el' );		
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
			
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId === '-1' ) {
			this.mostraErro( 'Ano letivo não selecionado.' );
			return;	
		}
		
		const instance = this;
		selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
			onload : () => {
				instance.setSelectFieldValue( 'turma', perfilService.getTurmaID() );
			},
			onchange : () => {
				let turmaId = instance.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasSelect( turmaId, 'turmas_disciplinas_select', {
					onload : () => {
						instance.setSelectFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );				
					}		
				} );
			}
		} );					
	}
			
	getJSON() {
		return {
			media : conversor.valorFloat( super.getFieldValue( 'media' ) ),
			peso : conversor.valorFloat( super.getFieldValue( 'peso' ) ),
		}
	}	
		
		
	limpaForm() {
		super.setFieldValue( 'media', '' );
		super.setFieldValue( 'peso', '' );
	}
										
}
