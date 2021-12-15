
import {selectService} from '../../service/SelectService.js';

import FormComponent from '../FormComponent.js';

export default class HorarioFormComponent extends FormComponent {

	quant_aulas_dia = 5;			

	constructor( formNome, prefixo, compELIDSufixo, msgELIDSufixo ) {
		super( formNome, prefixo, 'horario', compELIDSufixo, msgELIDSufixo );
	}			
				
	novoTBody() {
		let html = "";
		for( let i = 0; i < this.quant_aulas_dia; i++ ) {
			html += "<tr>";
			for( let j = 0; j < 5; j++ ) {
				let elid = this.getSelectELID( i, j );	
				let elname = this.getSelectName( i, j );			
				html += "<td><select id=\""+elid+"\" name=\""+elname+"\" class=\"form-select\"></select></td>";				
			}
			html += "</tr>";
		}		
		
		document.getElementById( 'horario-tabela-tbody' ).innerHTML = html;	
	}
		
	carregaJSON( turmaDisciplinas, quant_aulas_dia, novoHorarioFlag ) {		
		this.quant_aulas_dia = quant_aulas_dia;
		
		this.novoTBody();

		for( let i = 0; i < this.quant_aulas_dia; i++ ) {			
			for( let j = 0; j < 5; j++ ) {
				let htmlOptions = selectService.disciplinaSiglasOptionsHTML( turmaDisciplinas );
				
				let selectELID = this.getSelectELID( i, j );				
				let selectELName = this.getSelectName( i, j );
				
				super.setHTML( selectELID, htmlOptions );	

				super.setSelectFieldValue( selectELName, '-1' );										
			}
		}
								
		if ( novoHorarioFlag === false ) {
			for( let i = 0; i < turmaDisciplinas.length; i++ ) {
				let horarioAulas = turmaDisciplinas[ i ].horarioAulas;
				let tdid = turmaDisciplinas[ i ].id;
				for( let j = 0; j < horarioAulas.length; j++ ) {
					if ( horarioAulas[ j ].ativa !== 'true' )
						continue;
						
					let x = parseInt( horarioAulas[ j ].semanaDia-1 ); // O valor do dia da semana foi incrementado em getJSON, por isso, o decremento aqui.
					let y = parseInt( horarioAulas[ j ].numeroAula );
					if ( y < this.quant_aulas_dia )			
						this.setValor( x, y, tdid );
				}
			}
		}
	}
	
	// O primeiro dia útil da semana, a segunda, tem índice 2 e o domingo tem índice 1
	getJSON() {
		let horarioAulas = [];
		let k = 0;
		for( let i = 0; i < this.quant_aulas_dia; i++ ) {
			for( let j = 0; j < 5; j++ ) {
				let tdid = this.getValor( j+1, i+1 );
				if ( tdid !== '-1' )
					horarioAulas[ k++ ] = { turmaDisciplinaId : tdid, semanaDia : j+2, numeroAula : i+1 };
			}
		}
		return { horarioAulas : horarioAulas };
	}
	
	getValor( x, y ) {
		let name = this.getSelectName( y-1, x-1 );		
		return super.getFieldValue( name );
	}
	
	setValor( x, y, valor ) {
		let name = this.getSelectName( y-1, x-1 );				
		super.setFieldValue( name, valor );
	}
						
	getSelectELID( i, j ) {
		return "horario_celula_" + ( (i*5) + j );
	}
	
	getSelectName( i, j ) {
		return "horario_celula_" + ( (i*5) + j );
	}
				
}