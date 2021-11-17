
import {sistema} from '../../../sistema/Sistema.js';
import {conversor} from '../../../sistema/util/Conversor.js';

export default class AnoLetivoFormService {
	
	onCarregado() {
		sistema.carregaComponente( "calendario-mes", "calendario-m1-el", {
			prefixo : 'm1_',
			sucesso : ( xmlhttp ) => {
				let week = moment( '2022-01-01' ).weekday();
				let ndias = moment( "2022-01" ).daysInMonth();
				
				let html = "";
				let d = 1;
				let dia = 1;
				for( let i = 0; i < 6; i++ ) {
					html += "<tr>";
					for( let j = 0; j < 7; j++ ) {						
						if ( d >= week && dia <= ndias ) {
							let classe = ( j > 0 && j < 6 ? 'diautil' : 'fimdesemana')
							html += "<td class=\"" + classe + "\" onclick=\"alert("+dia+")\">" + dia + "</td>";
							dia++;
						} else {
							html += "<td class=\"vasio\"></td>";
						}
						d++;
					}
					html += "</tr>";
				}
				
				document.getElementById( 'm1_tabela-calendario-mes-tbody').innerHTML = html;
			}
		} )
	}	
	
}
export const anoLetivoForm = new AnoLetivoFormService();