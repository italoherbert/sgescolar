
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

export default class MatriculaTelaComponent extends RootFormComponent {
		
	constructor() {
		super( 'matricula_filtro_form', 'mensagem-el' );		
	}
	
	carregouHTMLCompleto() {
		let escolaId = perfilService.getEscolaID();
		if ( escolaId === '-1' ) {
			super.mostraErro( "Escola não selecionada." );
			return;
		}
		
		const instance = this;
		selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select', {
			onload : () => {
				instance.setSelectFieldValue( 'anoletivo', perfilService.getAnoLetivoID() );
			},
			onchange : () => {
				let anoLetivoId = instance.getFieldValue( 'anoletivo' );
				selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'turmas_select', {
					onload : () => {
						instance.setSelectFieldValue( 'turma', perfilService.getTurmaID() );
					}
				} );
			}
		} );
	}
	
	getJSON() {
		return {
			nomeIni : super.getFieldValue( 'nomeini' ),
		}
	}
		
}