
import {sistema} from '../../../../sistema/Sistema.js';
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'

import Component from '../../Component.js';

export default class EnderecoDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'endereco-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {
				
		sistema.carregaComponente( 'campo', super.getELID( 'logradouro' ), { rotulo : "Logradouro:", valor : dados.logradouro } );
		sistema.carregaComponente( 'campo', super.getELID( 'complemento' ), { rotulo : "Complemento:", valor : dados.complemento } );
		sistema.carregaComponente( 'campo', super.getELID( 'bairro' ), { rotulo : "Bairro:", valor : dados.bairro } );
		sistema.carregaComponente( 'campo', super.getELID( 'cep' ), { rotulo : "CEP:", valor : dados.cep } );
		
		wsLocalidades.carregaMunicipioPorId( dados.municipio, ( municipio ) => {
			sistema.carregaComponente( 'campo', super.getELID( 'municipio' ), { rotulo : "Municipio:", valor : municipio } );			
		} );
		wsLocalidades.carregaEstadoPorId( dados.uf, ( uf ) => {
			sistema.carregaComponente( 'campo', super.getELID( 'uf' ), { rotulo : "UF:", valor : uf } );			
		} );
	}
	
}