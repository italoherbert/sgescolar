
import {sistema} from '../../../sistema/Sistema.js';

import {selectService} from '../../service/SelectService.js';

import RootFormComponent from '../../component/RootFormComponent.js';
import HorarioComponent from '../../component/horario/HorarioComponent.js';

export default class HorarioDoAlunoComponent extends RootFormComponent {
	
	horarioTabelaCampos = [ 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta' ];
	
	constructor() {
		super( 'horario_form', 'mensagem-el' );	
		
		this.horarioComponent = new HorarioComponent( '', 'horario-tabela-el', this.horarioTabelaCampos );
		
		super.addFilho( this.horarioComponent );			
	}
	
	carregouHTMLCompleto() {
		let alunoId = sistema.globalVars.entidadeId;
		
		selectService.carregaMatriculasAlunoSelect( alunoId, 'matriculas_select' );					
	}
	
	carregaJSON( horarioAulas ) {
		this.horarioComponent.carregaAulasJSON( horarioAulas );
	}
		
}