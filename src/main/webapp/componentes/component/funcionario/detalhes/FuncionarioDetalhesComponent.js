
import DetalhesComponent from '../../DetalhesComponent.js';
import PessoaDetalhesComponent from '../../pessoa/detalhes/PessoaDetalhesComponent.js';
import UsuarioDetalhesComponent from '../../usuario/detalhes/UsuarioDetalhesComponent.js';

export default class FuncionarioDetalhesComponent extends DetalhesComponent {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'funcionario-detalhes', compELIDSufixo, 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( prefixo, 'pessoa_el' );
		this.usuarioDetalhesComponent = new UsuarioDetalhesComponent( prefixo, 'usuario_el' );
		
		super.addFilho( this.pessoaDetalhesComponent );
		super.addFilho( this.usuarioDetalhesComponent );
	}
	
	carrega( dados ) {		
		let escolaFunc = ( dados.escolaFunc === 'true' ? 'Sim' : 'Não' );
		
		super.setHTMLCampoValor( 'codigo_inep', 'Código INEP:', dados.codigoInep );						
		super.setHTMLCampoValor( 'escolaridade', 'Escolaridade:', dados.escolaridade.label );						
		super.setHTMLCampoValor( 'escola_func', 'Funcionário de escola:', escolaFunc );						
		super.setHTMLCampoValor( 'carga_horaria', 'Carga horária:', dados.cargaHoraria );						

		this.pessoaDetalhesComponent.carrega( dados.pessoa );
		this.usuarioDetalhesComponent.carrega( dados.usuario );
	}
	
}