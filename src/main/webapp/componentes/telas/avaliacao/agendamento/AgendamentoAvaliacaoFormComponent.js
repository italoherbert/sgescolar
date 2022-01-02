
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
			
		let professorId = this.globalParams.professorId;
		
		const instance = this;
		selectService.carregaTurmasPorProfessorSelect( professorId, 'turmas_select', {
			onload : () => {
				instance.setSelectFieldValue( 'turma', perfilService.getTurmaID() );
			},
			onchange : () => {
				let turmaId = instance.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasPorTurmaEProfessorSelect( turmaId, professorId, 'turmas_disciplinas_select', {
					onload : () => {
						instance.setSelectFieldValue( 'turma_disciplina', perfilService.getTurmaDisciplinaID() );				
					}		
				} );
			}
		} );
		
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId === '-1' || anoLetivoId === undefined || anoLetivoId === null ) {
			super.mostraErro( 'Ano letivo não selecionado.' );
			return;
		}	
		
		selectService.carregaPeriodosSelect( anoLetivoId, 'periodos_select' );					
	}
			
	getJSON() {
		return {
			peso : conversor.valorFloat( super.getFieldValue( 'peso' ) ),
			dataAgendamento : conversor.formataData( super.getFieldValue( 'data_agendamento' ) )
		}
	}	
		
										
}
