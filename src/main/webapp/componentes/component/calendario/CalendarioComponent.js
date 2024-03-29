
import Component from '../Component.js';
import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';

import MesCalendarioComponent from './mes/MesCalendarioComponent.js';

export default class CalendarioComponent extends Component {
	
	meses = [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Desembro' ];
	mesCalendarioComponents = [];
	
	constructor( prefixo, compELIDSufixo, ano ) {						
		super( prefixo, 'calendario', compELIDSufixo, 'calendario_mensagem_el' );
		
		for( let i = 0; i < this.meses.length; i++ )
			this.mesCalendarioComponents[ i ] = new MesCalendarioComponent( prefixo, 'calendario_el', i, this.meses[ i ] );		
	}
	
	onConfigurado() {
		for( let i = 0; i < this.meses.length; i++ )
			this.mesCalendarioComponents[ i ].configura( this.globalParams );
	}
				
	onHTMLCarregado() {
		let indices = [];
		for( let i = 0; i < this.meses.length; i++ )
			indices[ i ] = i;		
		
		super.getEL( 'mes_select' ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
			valores : indices,
			textos : this.meses,
			checkedValor : indices[ 0 ]
		} );
		
		super.getEL( 'mes_select' ).onchange = () => this.mesSelecionado();
		 
		this.mesCalendarioComponents[ 0 ].carregaHTML();		
	}
	
	mesSelecionado() {
		let i = super.getEL( 'mes_select' ).value;
		
		let mesComp = this.mesCalendarioComponents[ i ]; 
		mesComp.carregaHTML();
	}
	
}