
import {sistema} from '../../../sistema/Sistema.js';

import AutoCompleteFormComponent from '../../component/autocomplete/AutoCompleteFormComponent.js';

export default class CarregaProfessorAutoComplete extends AutoCompleteFormComponent {
	
	constructor( formNome, elidSufixo) {
		super( formNome, '', elidSufixo );
	}
	
	onTeclaDigitada( inputValue ) {
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