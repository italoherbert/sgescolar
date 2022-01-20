
import Component from '../Component.js';

export default class HorarioComponent extends Component {
				
	quant_aulas_dia = 5;			
				
	constructor( prefixo, compELIDSufixo, msgELIDSufixo ) {
		super( prefixo, 'horario', compELIDSufixo, msgELIDSufixo );
	}			
				
	novoTBody() {				
		let html = "";
		for( let i = 0; i < this.quant_aulas_dia; i++ ) {
			html += "<tr>";
			for( let j = 0; j < 5; j++ ) {
				let elid = this.getSelectELID( i, j );	
				html += "<td><span id=\""+elid+"\">Aula vaga</span></td>";
			}
			html += "</tr>";			
		}		
				
		document.getElementById( 'horario-tabela-tbody' ).innerHTML = html;
	}
	
	carregaPorHorarioJSON( horario ) {
		this.quant_aulas_dia = horario.quantidadeAulasDia;
				
		this.novoTBody();
		
		for( let i = 0; i < horario.aulas.length; i++ ) {
			let horarioAula = horario.aulas[ i ];
			if ( horarioAula.ativa !== 'true' )
				continue;
				
			let x = parseInt( horarioAula.semanaDia-1 );
			let y = parseInt( horarioAula.numeroAula );
			
			this.setValor( x, y, horarioAula.disciplinaSigla );
		}
	}
	
	carregaPorTurmaDisciplinasJSON( turmaDisciplinas, quantidade_aulas_dia ) {
		this.quant_aulas_dia = quantidade_aulas_dia;
		
		this.novoTBody();
						
		for( let i = 0; i < turmaDisciplinas.length; i++ ) {
			let horarioAulas = turmaDisciplinas[ i ].horarioAulas;
			let sigla = turmaDisciplinas[ i ].disciplinaSigla;
			for( let j = 0; j < horarioAulas.length; j++ ) {
				if ( horarioAulas[ j ].ativa !== 'true' )
					continue;
					
				let x = parseInt( horarioAulas[ j ].semanaDia-1 ); // O valor do dia da semana foi incrementado em getJSON de HorárioFormComponent, por isso, o decremento aqui.
				let y = parseInt( horarioAulas[ j ].numeroAula );
				
				if ( y < this.quant_aulas_dia )				
					this.setValor( x, y, sigla );
			}
		}
	}
			
	setValor( x, y, html ) {
		let elid = this.getSelectELID( y-1, x-1 );
		super.setHTML( elid, html );
	}		
					
	getSelectELID( i, j ) {
		return "horario_celula_" + ( ( i * 5 ) + j );
	}
					
}