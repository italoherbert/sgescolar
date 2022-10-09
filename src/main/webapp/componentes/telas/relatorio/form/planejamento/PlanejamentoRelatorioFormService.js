
import {sistema} from "../../../../../../sistema/Sistema.js";

import PlanejamentoRelatorioFormComponent from './PlanejamentoRelatorioFormComponent.js';

export default class PlanejamentoRelatorioFormService {
	
	constructor() {
		this.planejamentoRelatorioFormComponent = new PlanejamentoRelatorioFormComponent();
	}
	
	onCarregado() {
		this.planejamentoRelatorioFormComponent.configura( {} );
		this.planejamentoRelatorioFormComponent.carregaHTML();
	}
	
	geraRelatorio() {
		this.planejamentoRelatorioFormComponent.limpaMensagem();

		let planejamentoId = this.planejamentoRelatorioFormComponent.getFieldValue( 'planejamento' );
						
		let link = document.createElement( 'a' );
		link.href = "/api/relatorio/planejamento/"+planejamentoId+"/"+sistema.globalVars.token;
		link.target = '_BLANK';
		
		link.click();		
	}
	
	paraTela() {
		sistema.carregaPagina( 'relatorio-tela' );
	}
		
}
export const planejamentoRelatorioForm = new PlanejamentoRelatorioFormService();