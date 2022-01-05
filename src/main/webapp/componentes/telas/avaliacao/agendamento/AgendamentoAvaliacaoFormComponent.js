
import {sistema} from '../../../../sistema/Sistema.js';
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
		
		const instance = this;
		if ( this.globalParams.op === 'editar' ) {
			sistema.ajax( 'GET', '/api/avaliacao/get/'+this.globalParams.avaliacaoId, {
				sucesso : ( resultado ) => {
					let dados = JSON.parse( resultado );
					instance.carregaJSON( dados );		
				},
				erro : ( msg ) => {
					instance.mostraErro( msg );
				}
			} );
		} else {					
			let professorId = this.globalParams.professorId;
			
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
			selectService.carregaAvaliacaoTiposSelect( 'avaliacao_tipos_select' );
		}
	}
	
	carregaJSON( dados ) {
		selectService.carregaUmaOptionSelect( 'turmas_select', dados.turmaDisciplina.turmaId, dados.turmaDisciplina.turmaDescricaoDetalhada );
		selectService.carregaUmaOptionSelect( 'turmas_disciplinas_select', dados.turmaDisciplina.disciplinaId, dados.turmaDisciplina.disciplinaDescricao );
		selectService.carregaUmaOptionSelect( 'periodos_select', dados.periodo.id, dados.periodo.descricao );
		selectService.carregaUmaOptionSelect( 'avaliacao_tipos_select', dados.avaliacaoTipo.name, dados.avaliacaoTipo.label );
		
		super.setFieldValue( 'data_agendamento', conversor.valorData( dados.dataAgendamento ) );
		super.setFieldValue( 'peso', conversor.formataFloat( dados.peso ) );
	}
			
	getJSON() {				
		return {
			dataAgendamento : conversor.formataData( super.getFieldValue( 'data_agendamento' ) ),
			avaliacaoTipo : super.getFieldValue( 'avaliacao_tipo' ),
			peso : conversor.valorFloat( super.getFieldValue( 'peso' ) )
		}
	}	
		
	limpaForm() {
		super.setFieldValue( 'data_agendamento', '' );
		super.setFieldValue( 'peso', '10' );
	}
										
}
