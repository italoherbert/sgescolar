
import {sistema} from "../../../../../sistema/Sistema.js";

export default class RelatorioTelaService {
			
	paraPlanejamentoRelatorioForm() {
		sistema.carregaPagina( 'planejamento-relatorio-form' );
	}
		
}
export const relatorioTela = new RelatorioTelaService();