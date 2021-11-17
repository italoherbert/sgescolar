
import {sistema} from '../../../../sistema/Sistema.js';
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'

import Component from '../../Component.js';

export default class EnderecoLocalDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'endereco-local-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados, municipio, uf ) {							
		sistema.carregaComponente( 'campo', super.getELID( 'logradouro' ), { rotulo : "Logradouro:", valor : dados.logradouro } );
		sistema.carregaComponente( 'campo', super.getELID( 'complemento' ), { rotulo : "Complemento:", valor : dados.complemento } );
		sistema.carregaComponente( 'campo', super.getELID( 'bairro' ), { rotulo : "Bairro:", valor : dados.bairro } );
		sistema.carregaComponente( 'campo', super.getELID( 'cep' ), { rotulo : "CEP:", valor : dados.cep } );
				
		wsLocalidades.carregaMunicipioPorId( municipio, ( municipio ) => {
			let municipio2 = ( municipio === undefined || municipio === null ? "" : municipio );
			sistema.carregaComponente( 'campo', super.getELID( 'municipio' ), { rotulo : "Municipio:", valor : municipio2 } );			
		} );
		wsLocalidades.carregaEstadoPorId( uf, ( uf ) => {
			let uf2 = ( uf === undefined || uf === null ? "" : uf );
			sistema.carregaComponente( 'campo', super.getELID( 'uf' ), { rotulo : "UF:", valor : uf2 } );			
		} );
	}
	
}