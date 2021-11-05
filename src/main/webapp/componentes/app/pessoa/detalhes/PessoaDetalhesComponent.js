
import {sistema} from '../../../../sistema/Sistema.js';
import {conversor} from '../../../../sistema/util/Conversor.js';

import Component from '../../../component/Component.js';

export default class PessoaDetalhesComponent extends Component {
	
	constructor( prefixo ) {
		super( prefixo, 'pessoa-detalhes', 'pessoa_detalhes_el', 'mensagem_el' );
	}
	
	carrega( dados ) {
		let data_nasc = conversor.formataDataString( dados.dataNascimento );
		
		let classes = this.globalParams.classes;
		sistema.carregaComponente( 'field', super.getELID( 'nome' ), { classes : classes, rotulo : "Nome:", valor : dados.nome } );
		sistema.carregaComponente( 'field', super.getELID( 'nome_social' ), { classes : classes, rotulo : "Nome social:", valor : dados.nomeSocial } );
		sistema.carregaComponente( 'field', super.getELID( 'cpf' ), { classes : classes, rotulo : "CPF:", valor : dados.cpf } );
		sistema.carregaComponente( 'field', super.getELID( 'rg' ), { classes : classes, rotulo : "RG:", valor : dados.rg } );
		sistema.carregaComponente( 'field', super.getELID( 'data_nascimento' ), { classes : classes, rotulo : "Data de nascimento:", valor : data_nasc } );
	}
	
}