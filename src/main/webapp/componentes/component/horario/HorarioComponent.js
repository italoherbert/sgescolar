
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
	
	carregaAulasJSON( horarioAulas ) {
		for( let i = 0; i < horarioAulas.length; i++ ) {
			let horarioAula = horarioAulas[ i ];
			let x = parseInt( horarioAula.semanaDia-1 );
			let y = parseInt( horarioAula.numeroAula );
			this.setValor( x, y, horarioAula.disciplinaSigla );
		}
	}
	
	carregaPorTurmaDisciplinasJSON( turmaDisciplinas ) {				
		for( let i = 0; i < turmaDisciplinas.length; i++ ) {
			let aulas = turmaDisciplinas[ i ].aulas;
			let sigla = turmaDisciplinas[ i ].disciplinaSigla;
			for( let j = 0; j < aulas.length; j++ ) {
				let x = parseInt( aulas[ j ].semanaDia-1 ); // O valor do dia da semana foi incrementado em getJSON de HorárioFormComponent, por isso, o decremento aqui.
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