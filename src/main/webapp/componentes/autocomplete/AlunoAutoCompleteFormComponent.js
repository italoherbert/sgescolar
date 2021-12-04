
import {sistema} from '../../sistema/Sistema.js';

import AutoCompleteFormComponent from '../component/autocomplete/AutoCompleteFormComponent.js';

export default class AlunoAutoCompleteFormComponent extends AutoCompleteFormComponent {
	
	constructor( formNome, elidSufixo) {
		super( formNome, '', elidSufixo );
	}
			
	onTeclaDigitada( e, inputValue ) {
		if ( e.ctrlKey !== true || e.keyCode !== 32 )
			return;
		
		const instance = this;
		sistema.ajax( 'POST', '/api/aluno/filtra/5', {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				nomeIni : inputValue
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
												
				let alunos = [];
				for( let i = 0; i < dados.length; i++ )
					alunos[ i ] = { id : dados[ i ].id, value : dados[ i ].pessoa.nome };
				
				instance.carrega( alunos );
			},
			errro : ( msg ) => {
				instance.mostraErro( msg );
			}
		} );
	}
	
}