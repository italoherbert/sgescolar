
import * as elutil from '../../../../../sistema/util/elutil.js';

import DetalhesComponent from '../../../../component/DetalhesComponent.js';
import PessoaDetalhesComponent from '../../../../component/pessoa/detalhes/PessoaDetalhesComponent.js';

export default class ResponsavelDetalhesComponent extends DetalhesComponent {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'responsavel-detalhes', compELIDSufixo, 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( prefixo, 'pessoa_el' );
		
		super.addFilho( this.pessoaDetalhesComponent );
	}
	
	onConfigurado() {				
		this.params.titulo = super.getGlobalParam( 'detalhes_titulo' );
	}
	
	onHTMLCarregado() {
		super.getEL( 'titulo' ).onclick = ( e ) => this.mostraOuEscondePainelConteudo( e );
	}
	
	carrega( dados ) {
		let registrado = ( dados.registrado === 'true' ? true : false );
		
		if ( registrado === true ) {					
			let falecido_status = ( dados.falecido === 'true' ? 'Sim' : 'Não' );
			super.setHTMLCampoValor( 'falecido', 'Falecido(a)', falecido_status );
			elutil.showHide( super.getELID( "painel_situacao" ) );

			this.pessoaDetalhesComponent.carrega( dados.pessoa );
		} else {
			super.setHTMLCampoValor( 'situacao', 'Situação', 'Não registrado(a)' );
			elutil.showHide( super.getELID( "painel_dados" ) );	
		}				
	}
	
	mostraOuEscondePainelConteudo( e ) {	
		super.getEL( "esconde_icone" ).classList.toggle( 'hidden' );
		super.getEL( "esconde_icone" ).classList.toggle( 'visible' );
		super.getEL( "esconde_icone" ).classList.toggle( 'd-none' );
		super.getEL( "esconde_icone" ).classList.toggle( 'd-inline-block' );
		super.getEL( "expande_icone" ).classList.toggle( 'hidden' );
		super.getEL( "expande_icone" ).classList.toggle( 'visible' );
		super.getEL( "expande_icone" ).classList.toggle( 'd-none' );
		super.getEL( "expande_icone" ).classList.toggle( 'd-inline-block' );
		
		elutil.showHide( super.getELID( 'painel_conteudo' ) );
	}	
}