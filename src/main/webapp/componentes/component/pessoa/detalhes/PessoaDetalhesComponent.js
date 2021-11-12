
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import Component from '../../Component.js';
import EnderecoDetalhesComponent from '../../endereco/detalhes/EnderecoDetalhesComponent.js';
import ContatoInfoDetalhesComponent from '../../contato-info/detalhes/ContatoInfoDetalhesComponent.js';

export default class PessoaDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'pessoa-detalhes', compELIDSufixo, 'mensagem_el' );
		
		this.enderecoDetalhesComponent = new EnderecoDetalhesComponent( prefixo, 'endereco_el' );
		this.contatoInfoDetalhesComponent = new ContatoInfoDetalhesComponent( prefixo, 'contato_info_el' );
		
		super.addFilho( this.enderecoDetalhesComponent );
		super.addFilho( this.contatoInfoDetalhesComponent );
	}
	
	carrega( dados ) {
		let data_nasc = conversor.formataDataString( dados.dataNascimento );
		
		sistema.carregaComponente( 'campo', super.getELID( 'nome' ), { rotulo : "Nome:", valor : dados.nome } );
		sistema.carregaComponente( 'campo', super.getELID( 'nome_social' ), { rotulo : "Nome social:", valor : dados.nomeSocial } );
		sistema.carregaComponente( 'campo', super.getELID( 'cpf' ), { rotulo : "CPF:", valor : dados.cpf } );
		sistema.carregaComponente( 'campo', super.getELID( 'rg' ), { rotulo : "RG:", valor : dados.rg } );
		sistema.carregaComponente( 'campo', super.getELID( 'data_nascimento' ), { rotulo : "Data de nascimento:", valor : data_nasc } );

		sistema.carregaComponente( 'campo', super.getELID( 'sexo' ), { rotulo : "Sexo:", valor : dados.sexo } );
		sistema.carregaComponente( 'campo', super.getELID( 'estado_civil' ), { rotulo : "Estado civil:", valor : dados.estadoCivil } );
		sistema.carregaComponente( 'campo', super.getELID( 'nacionalidade' ), { rotulo : "Nacionalidade:", valor : dados.nacionalidade } );
		sistema.carregaComponente( 'campo', super.getELID( 'raca' ), { rotulo : "Raça:", valor : dados.raca } );
		sistema.carregaComponente( 'campo', super.getELID( 'religiao' ), { rotulo : "Religião:", valor : dados.religiao } );
		
		this.enderecoDetalhesComponent.carrega( dados.endereco );
		this.contatoInfoDetalhesComponent.carrega( dados.contatoInfo );
	}
	
}