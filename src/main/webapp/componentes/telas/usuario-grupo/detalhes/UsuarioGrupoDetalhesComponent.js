
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import RootDetalhesComponent from '../../../component/RootDetalhesComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class UsuarioGrupoDetalhesComponent extends RootDetalhesComponent {
	
	permissoesTabelaCampos = [ 'Nome do recurso', 'Leitura', 'Escrita', 'Remoção' ];	
	
	constructor() {
		super( 'mensagem-el' );
		
		this.permissoesTabelaComponent = new TabelaComponent( '', 'tabela-permissoes-el', this.permissoesTabelaCampos );
		
		super.addFilho( this.permissoesTabelaComponent );		
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		
		sistema.ajax( "GET", "/api/usuario/grupo/get/"+this.globalParams.usuarioGrupoId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );	
				instance.carrega( dados );																
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );		
	}
	
	carrega( dados ) {			
		super.setHTMLCampoValor( 'nome', 'Nome do grupo:', dados.nome );	
				
		let tdados = [];
		let permissoes = dados.permissaoGrupos;
		for( let i = 0; i < permissoes.length; i++ ) {
			tdados[ i ] = new Array();
			
			let recursoNome = permissoes[ i ].recurso;
									
			tdados[ i ].push( recursoNome );
			tdados[ i ].push( htmlBuilder.novoSVGFontImageHTML( this.getIconClasses( permissoes[ i ].leitura ) ) );
			tdados[ i ].push( htmlBuilder.novoSVGFontImageHTML( this.getIconClasses( permissoes[ i ].escrita ) ) );
			tdados[ i ].push( htmlBuilder.novoSVGFontImageHTML( this.getIconClasses( permissoes[ i ].remocao ) ) );			
		}
		
		this.permissoesTabelaComponent.carregaTBody( tdados );		
	}
	
	getIconClasses( perm ) {
		if ( perm === 'true' || perm === true )
			return "bi bi-check-lg text-primary";
		return "bi bi-x-lg text-danger";		
	}
	
}