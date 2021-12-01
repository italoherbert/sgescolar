
import Component from '../Component.js';

export default class HorarioComponent extends Component {
		
	constructor( prefixo, compELIDSufixo, msgELIDSufixo ) {
		super( prefixo, 'horario', compELIDSufixo, msgELIDSufixo );
	}			
																
	onHTMLCarregado() {									
		this.novoTBody();														
	}	
	
	novoTBody() {
		let html = "";
		for( let i = 0; i < 5; i++ ) {
			html += "<tr>";
			for( let j = 0; j < 5; j++ ) {
				let elid = this.getSelectELID( i, j );	
				html += "<td><span id=\""+elid+"\"></span></td>";				
			}
			html += "</tr>";
		}		
		
		document.getElementById( 'horario-tabela-tbody' ).innerHTML = html;	
	}
	
	carregaJSON( disciplinasVinculadas ) {				
		for( let i = 0; i < disciplinasVinculadas.length; i++ ) {
			let aulas = disciplinasVinculadas[ i ].aulas;
			let sigla = disciplinasVinculadas[ i ].disciplinaSigla;
			for( let j = 0; j < aulas.length; j++ ) {
				let x = parseInt( aulas[ j ].semanaDia );
				let y = parseInt( aulas[ j ].numeroAula );
				this.setValor( x, y, sigla );
			}
		}
	}
			
	setValor( x, y, html ) {
		let elid = this.getSelectELID( y-1, x-1 );
		super.setHTML( elid, html );
	}		
					
	getSelectELID( i, j ) {
		return "horario_celula_" + ( (i*5) + j );
	}
					
}