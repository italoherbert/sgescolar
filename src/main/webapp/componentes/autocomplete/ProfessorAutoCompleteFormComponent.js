
import {sistema} from '../../sistema/Sistema.js';

import AutoCompleteFormComponent from '../component/autocomplete/AutoCompleteFormComponent.js';

export default class ProfessorAutoCompleteFormComponent extends AutoCompleteFormComponent {
	
	contador = 0;
	
	constructor( formNome, elidSufixo) {
		super( formNome, '', elidSufixo );
	}
			
	onTeclaDigitada( e, inputValue ) {
		this.contador++;
		if ( this.contador % 3 !== 0 && ( e.ctrlKey === false || ( e.ctrlKey === true && e.keyCode !== 32 ) ) )
			return;
		
		const instance = this;
		sistema.ajax( 'POST', '/api/professor/filtra/5', {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				nomeIni : inputValue
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
												
				let profs = [];
				for( let i = 0; i < dados.length; i++ )
					profs[ i ] = { id : dados[ i ].id, value : dados[ i ].funcionario.pessoa.nome };
				
				instance.carrega( profs );
			},
			errro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );
	}
	
}