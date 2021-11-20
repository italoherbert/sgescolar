
import DetalhesComponent from '../../DetalhesComponent.js';

export default class ContatoInfoDetalhesComponent extends DetalhesComponent {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'contato-info-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {		
		super.setHTMLCampoValor( 'telefone_fixo', 'Telefone fixo:', dados.telefoneFixo );
		super.setHTMLCampoValor( 'telefone_celular', 'Telefone celular: ', dados.telefoneCelular );
		super.setHTMLCampoValor( 'email', 'E-Mail:', dados.email );				
	}	
	
}