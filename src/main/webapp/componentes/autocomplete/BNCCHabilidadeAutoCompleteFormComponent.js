
import {sistema} from '../../sistema/Sistema.js';

import AutoCompleteFormComponent from '../component/autocomplete/AutoCompleteFormComponent.js';

export default class BNCCHabilidadeAutoCompleteFormComponent extends AutoCompleteFormComponent {
	
	contador = 0;
	
	constructor( formNome, elidSufixo) {
		super( formNome, '', elidSufixo );
	}
			
	onTeclaDigitada( e, inputValue ) {
		this.contador++;
		if ( this.contador % 3 != 0 || ( e.ctrlKey === true && e.keyCode !== 32 ) )
			return;
		
		const instance = this;
		sistema.ajax( 'POST', '/api/bncc-habilidade/filtra/limit/5', {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				codigoIni : inputValue
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
							
				let habilidades = [];
				for( let i = 0; i < dados.length; i++ )
					habilidades[ i ] = { codigo : dados[ i ].codigo, value : dados[ i ].habilidade };
				
				instance.carrega( habilidades );
			},
			errro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );
	}
	
}