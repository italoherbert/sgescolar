
import {sistema} from '../../../../sistema/Sistema.js';
import {wsLocalidades} from '../../../../sistema/WSLocalidades.js'

import Component from '../../../component/Component.js';

export default class EnderecoDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'endereco-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {
				
		sistema.carregaComponente( 'field', super.getELID( 'logradouro' ), { rotulo : "Logradouro:", valor : dados.logradouro } );
		sistema.carregaComponente( 'field', super.getELID( 'complemento' ), { rotulo : "Complemento:", valor : dados.complemento } );
		sistema.carregaComponente( 'field', super.getELID( 'bairro' ), { rotulo : "Bairro:", valor : dados.bairro } );
		sistema.carregaComponente( 'field', super.getELID( 'cep' ), { rotulo : "CEP:", valor : dados.cep } );
		
		wsLocalidades.carregaMunicipioPorId( dados.municipio, ( municipio ) => {
			sistema.carregaComponente( 'field', super.getELID( 'municipio' ), { rotulo : "Municipio:", valor : municipio } );			
		} );
		wsLocalidades.carregaEstadoPorId( dados.uf, ( uf ) => {
			sistema.carregaComponente( 'field', super.getELID( 'uf' ), { rotulo : "UF:", valor : uf } );			
		} );
	}
	
}