
import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';

import Component from '../Component.js';

export default class TabelaComponent extends Component {
					
	tabelaSufixo = "table";
	tabelaHeadSufixo = "thead";
	tabelaBodySufixo = "tbody";
	
	tabelaCompELIDSufixo = "";		
	tabelaCampos = [];		
	
	onTabelaModeloCarregado = () => {};						
				
	constructor( prefixo, compELIDSufixo, tabelaCampos ) {						
		super( prefixo, 'tabela', compELIDSufixo, 'tabela_mensagem_el' );
		this.tabelaCampos = tabelaCampos;
	}
	
	onHTMLCarregado() {							
		this.carregaTHead();				
	}
	
	carregaTHead() {
		let thead_el = this.getTHeadEL();
		thead_el.innerHTML = htmlBuilder.novoConteudoTHeadHTML( this.tabelaCampos );
		
		if ( typeof( this.onTabelaModeloCarregado ) === 'function' )
			this.onTabelaModeloCarregado.call( this );
	}
	
	carregaTBody( dados ) {
		let tbody_el = this.getTBodyEL();
		tbody_el.innerHTML = htmlBuilder.novoConteudoTBodyHTML( dados );
	}
	
	limpaDados() {
		this.limpaTBody();
	}
	
	limpaTBody() {
		let tbody_el = this.getTBodyEL();
		tbody_el.innerHTML = "";
	}
		
	getTabelaEL() {
		return super.getEL( this.tabelaSufixo );
	}
	
	getTHeadEL() {
		return super.getEL( this.tabelaHeadSufixo );
	}
	
	getTBodyEL() {
		return super.getEL( this.tabelaBodySufixo );
	}
		
	getTabelaELID() {
		return this.prefixo + this.tabelaSufixo;
	}	
	
	getTHeadELID() {
		return this.prefixo + this.tabelaHeadSufixo;
	}	
	
	getTBodyELID() {
		return this.prefixo + this.tabelaBodySufixo;
	}
			
}