
import Component from '../../Component.js';

export default class MesCalendarioComponent extends Component {
	
	diaOnClick = () => {};
	
	mes = 0;
	stringMes = '';
		
	constructor( prefixo, compELIDSufixo, mes, stringMes ) {						
		super( prefixo, 'calendario-mes', compELIDSufixo, 'calendario_mes_mensagem_el' );
		this.mes = mes;		
		this.stringMes = stringMes;
	}
	
	onHTMLCarregado() {
		let ano = this.globalParams.ano;
		let feriados = [];
		if ( this.globalParams.feriados !== undefined && this.globalParams.feriados !== null ) {
			for( let i = 0; i < this.globalParams.feriados.length; i++ ) {
				let d = this.globalParams.feriados[ i ];
				if ( d.getMonth() === this.mes )
					feriados[ feriados.length ] = d.getDate();				
			}
		}
		
		let hoje = undefined;
		let dcurr = new Date();
		if ( dcurr.getMonth() == this.mes )
			hoje = dcurr.getDate();
					
		let mes2 = ( (this.mes+1) < 10 ? '0'+(this.mes+1) : ''+(this.mes+1) );
								
		let week = moment( ano + '-' + mes2 + '-01', 'YYYY-MM-DD' ).weekday() + 1;
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
					html += "<td class=\"vasio\">&nbsp;</td>";
				}
				d++;
			}
			html += "</tr>";
		}
		
		super.setHTML( 'calendario_mes_tbody', html );
	}
		
}