
import Component from '../../Component.js';

export default class MesCalendarioComponent extends Component {
	
	diaOnClick = () => {};
		
	constructor( prefixo, compELIDSufixo ) {						
		super( prefixo, 'calendario-mes', compELIDSufixo, 'calendario_mes_mensagem_el' );		
	}
	
	onHTMLCarregado() {
		let ano = this.globalParams.ano;
		let mes = this.globalParams.mes;
		let feriados = this.globalParams.feriados;
		let hoje = this.globalParams.hoje;
		
		let mes2 = ( mes < 10 ? '0'+mes : ''+mes );
				
		let week = moment( ano + '-' + mes2 + '-01', 'YYYY-MM-DD' ).weekday();
		let ndias = moment( '' + ano + '-' + mes2, 'YYYY-MM' ).daysInMonth();
				
		let html = "";
		let d = 1;
		let dia = 1;
		for( let i = 0; i < 6; i++ ) {
			html += "<tr>";
			for( let j = 0; j < 7; j++ ) {						
				if ( d >= week && dia <= ndias ) {
					let ehDiaNaoUtil = false;
					
					if ( j < 1 || j > 5 ) {
						ehDiaNaoUtil = true;
					} else if ( feriados !== undefined && feriados !== null ) {						
						for( let k = 0; ehDiaNaoUtil === false && k < feriados.length; k++ )
							if ( dia === feriados[ k ] )
								ehDiaNaoUtil = true;														
					}
					
					let classes = ( ehDiaNaoUtil === true ? 'dianaoutil' : 'diautil' );
					
					if ( hoje !== undefined && hoje !== null )
						if ( dia === hoje )
							classes += ' hoje';
					
					let onclick = "this.diaOnClick( " + dia + ", " + ehDiaNaoUtil + " )";					
					html += "<td class=\"" + classes + "\" onclick=\"" + onclick + "\">" + dia + "</td>";
					dia++;
				} else {
					html += "<td class=\"vasio\"></td>";
				}
				d++;
			}
			html += "</tr>";
		}
		
		super.setHTML( 'calendario_mes_tbody', html );
	}
		
}