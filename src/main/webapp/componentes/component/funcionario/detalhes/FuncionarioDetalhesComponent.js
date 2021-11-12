
import {sistema} from '../../../../sistema/Sistema.js';

import Component from '../../Component.js';
import PessoaDetalhesComponent from '../../pessoa/detalhes/PessoaDetalhesComponent.js';
import UsuarioDetalhesComponent from '../../usuario/detalhes/UsuarioDetalhesComponent.js';

export default class FuncionarioDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'funcionario-detalhes', compELIDSufixo, 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( prefixo, 'pessoa_el' );
		this.usuarioDetalhesComponent = new UsuarioDetalhesComponent( prefixo, 'usuario_el' );
		
		super.addFilho( this.pessoaDetalhesComponent );
		super.addFilho( this.usuarioDetalhesComponent );
	}
	
	carrega( dados ) {		
		let escolaFunc = ( dados.escolaFunc === 'true' ? 'Sim' : 'Não' );
		sistema.carregaComponente( 'campo', super.getELID( 'codigo_inep' ), { rotulo : "Código INEP:", valor : dados.codigoInep } );
		sistema.carregaComponente( 'campo', super.getELID( 'escolaridade' ), { rotulo : "Escolaridade:", valor : dados.escolaridade } );
		sistema.carregaComponente( 'campo', super.getELID( 'escola_func' ), { rotulo : "Funcionário de escola:", valor : escolaFunc } );
		sistema.carregaComponente( 'campo', super.getELID( 'carga_horaria' ), { rotulo : "Carga horária:", valor : dados.cargaHoraria } );
		
		this.pessoaDetalhesComponent.carrega( dados.pessoa );
		this.usuarioDetalhesComponent.carrega( dados.usuario );
	}
	
}