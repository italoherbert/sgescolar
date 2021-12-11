
import {conversor} from '../../../../sistema/util/Conversor.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

export default class PlanejamentoTelaComponent extends RootFormComponent {
		
	constructor() {
		super( 'planejamento_filtro_form', 'mensagem-el' );
	}
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		let anoLetivoId = perfilService.getAnoLetivoID();
		if ( anoLetivoId == '-1' ) {
			super.mostraErro( 'Ano letivo não selecionado.' );
			return;
		}
		
		const instance = this;
		selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
			onload : () => {
				let turmaId = perfilService.getTurmaID();
				if ( turmaId != '-1' )
					instance.setSelectFieldValue( 'turma', turmaId );
			},
			onchange : () => {
				let turmaId = super.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasSelect( turmaId, 'turmas_disciplinas_select', {
					onload : () => {
						let turmaDisciplinaId = perfilService.getTurmaDisciplinaID();
						if ( turmaDisciplinaId != '-1' )
							instance.setSelectFieldValue( 'turma_disciplina', turmaDisciplinaId );
					},
					onchange : () => {
						let turmaDisciplinaId = super.getFieldValue( 'turma_disciplina' );
						selectService.carregaProfessorAlocacaoPorTurmaDisciplinaSelect( turmaDisciplinaId, 'professores_alocacoes_select' );
					}
				} );
			}
		} );
		
	}		
			
	getJSON() {
		return {
			intervaloData : conversor.formataData( super.getFieldValue( 'intervalo_data' ) ),
			descricaoIni : super.getFieldValue( 'descricaoini' )
		}
	}
		
}