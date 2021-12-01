
import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';

import FormComponent from '../FormComponent.js';

export default class HorarioFormComponent extends FormComponent {
		
	constructor( formNome, prefixo, compELIDSufixo, msgELIDSufixo ) {
		super( formNome, prefixo, 'horario', compELIDSufixo, msgELIDSufixo );
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
				let elname = this.getSelectName( i, j );			
				html += "<td><select id=\""+elid+"\" name=\""+elname+"\" class=\"form-select\"></select></td>";				
			}
			html += "</tr>";
		}		
		
		document.getElementById( 'horario-tabela-tbody' ).innerHTML = html;	
	}
	
	carregaJSON( disciplinasVinculadas ) {
		for( let i = 0; i < 5; i++ ) {			
			for( let j = 0; j < 5; j++ ) {
				let htmlOptions = this.getSelectOptionsHTML( disciplinasVinculadas );
				
				let selectELID = this.getSelectELID( i, j );
				super.setHTML( selectELID, htmlOptions );								
			}
		}
		
		for( let i = 0; i < disciplinasVinculadas.length; i++ ) {
			let aulas = disciplinasVinculadas[ i ].aulas;
			let tdid = disciplinasVinculadas[ i ].id;
			for( let j = 0; j < aulas.length; j++ ) {
				let x = parseInt( aulas[ j ].semanaDia );
				let y = parseInt( aulas[ j ].numeroAula );
				this.setValor( x, y, tdid );
			}
		}
	}
	
	getJSON() {
		let aulas = [];
		let k = 0;
		for( let i = 0; i < 5; i++ ) {
			for( let j = 0; j < 5; j++ ) {
				let tdid = this.getValor( j+1, i+1 );
				aulas[ k++ ] = { turmaDisciplinaId : tdid, semanaDia : j+1, numeroAula : i+1 };
			}
		}
		return { aulas };
	}
	
	getValor( x, y ) {
		let name = this.getSelectName( y-1, x-1 );		
		return super.getFieldValue( name );
	}
	
	setValor( x, y, valor ) {
		let name = this.getSelectName( y-1, x-1 );				
		super.setFieldValue( name, valor );
	}
	
	getSelectOptionsHTML( disciplinasVinculadas ) {
		let valores = [];
		let textos = [];
		for( let i = 0; i < disciplinasVinculadas.length; i++ ) {	
			valores[ i ] = disciplinasVinculadas[ i ].id;
			textos[ i ] = disciplinasVinculadas[ i ].disciplinaSigla;
		}
		
		return htmlBuilder.novoSelectOptionsHTML( {
			valores : valores,
			textos : textos
		} );
	}
					
	getSelectELID( i, j ) {
		return "horario_celula_" + ( (i*5) + j );
	}
	
	getSelectName( i, j ) {
		return "horario_celula_" + ( (i*5) + j );
	}
				
}