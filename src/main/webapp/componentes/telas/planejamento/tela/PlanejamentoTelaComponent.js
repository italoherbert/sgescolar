
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

export default class PlanejamentoTelaComponent extends RootFormComponent {
		
	constructor() {
		super( 'planejamento_filtro_form', 'mensagem-el' );
	}
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		if ( sistema.globalVars.perfil.name !== 'PROFESSOR') {
			super.mostraErro( 'Para acessar esse recurso é necessário estar logado com usuário que tenha perfil de professor.' );
			return;
		}
		
		let professorId = sistema.globalVars.entidadeId;
		
		const instance = this;
		selectService.carregaTurmasPorProfessorSelect( professorId, 'turmas_select', {
			onload : () => {
				let turmaId = perfilService.getTurmaID();
				if ( turmaId != '-1' )
					instance.setSelectFieldValue( 'turma', turmaId );
			},
			onchange : () => {
				let turmaId = super.getFieldValue( 'turma' );
				selectService.carregaTurmaDisciplinasPorTurmaEProfessorSelect( turmaId, professorId, 'turmas_disciplinas_select', {
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
			descricaoIni : super.getFieldValue( 'descricaoini' )
		}
	}
		
}