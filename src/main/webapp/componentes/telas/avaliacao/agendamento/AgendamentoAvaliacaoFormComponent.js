
import {conversor} from '../../../../sistema/util/Conversor.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class AgendamentoAvaliacaoFormComponent extends RootFormComponent {
										
	constructor() {
		super( 'agendamento_avaliacao_form', 'mensagem-el' );						
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId === '-1' ) {
			super.mostraErro( 'Selecione um ano letivo.' );
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
			peso : super.getFieldValue( 'peso' ),
			dataAgendamento : conversor.formataData( super.getFieldValue( 'data_agendamento' ) )
		}
	}	
		
										
}