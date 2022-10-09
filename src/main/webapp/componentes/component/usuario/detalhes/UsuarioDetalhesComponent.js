
import DetalhesComponent from '../../DetalhesComponent.js';

export default class UsuarioDetalhesComponent extends DetalhesComponent {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'usuario-detalhes', compELIDSufixo, 'mensagem_el' );
	}
	
	carrega( dados ) {			
		super.setHTMLCampoValor( 'username', 'Nome de usuário:', dados.username );
		super.setHTMLCampoValor( 'perfil', 'Perfil:', dados.perfil.label );
		
		let html = "";
		for( let i = 0; i < dados.grupos.length; i++ )
			html += dados.grupos[ i ].nome + ( i < dados.grupos.length-1 ? ", " : "" );	
		
		super.setHTMLCampoValor( 'grupos', 'Grupos de usuário:', html );
	}	
	
}