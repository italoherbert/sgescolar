
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'

import DetalhesComponent from '../../DetalhesComponent.js';

export default class EnderecoLocalDetalhesComponent extends DetalhesComponent {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'endereco-local-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados, municipio, uf ) {	
		super.setHTMLCampoValor( 'logradouro', 'Logradouro:', dados.logradouro );						
		super.setHTMLCampoValor( 'complemento', 'Complemento:', dados.complemento );						
		super.setHTMLCampoValor( 'bairro', 'Bairro:', dados.bairro );						
		super.setHTMLCampoValor( 'cep', 'CEP:', dados.cep );						
		
		const instance = this;
		
		wsLocalidades.carregaMunicipioPorId( municipio, ( municipio ) => {
			let municipio2 = ( municipio === undefined || municipio === null ? "" : municipio );
			instance.setHTMLCampoValor( 'municipio', 'Município:', municipio2 );
		} );
		wsLocalidades.carregaEstadoPorId( uf, ( uf ) => {
			let uf2 = ( uf === undefined || uf === null ? "" : uf );
			instance.setHTMLCampoValor( 'uf', 'UF:', uf2 );			
		} );				
	}
	
}