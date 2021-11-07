
import * as elutil from '../../../../../sistema/util/elutil.js';

import {sistema} from '../../../../../sistema/Sistema.js';

import Component from '../../../../component/Component.js';
import PessoaDetalhesComponent from '../../../pessoa/detalhes/PessoaDetalhesComponent.js';

export default class PaiOuMaeDetalhesComponent extends Component {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'pai-ou-mae-detalhes', compELIDSufixo, 'mensagem_el' );
		
		this.pessoaDetalhesComponent = new PessoaDetalhesComponent( prefixo, 'pessoa_el' );
		
		super.addFilho( this.pessoaDetalhesComponent );
	}
	
	onConfigurado() {				
		this.params.titulo = super.getGlobalParam( 'detalhes_titulo' );
	}
	
	onHTMLCarregado() {
		super.getEL( 'titulo' ).onclick = ( e ) => this.mostrarEsconderPainelConteudo( e );	
	}
	
	carrega( dados ) {
		let desconhecido = ( dados.desconhecido === 'true' ? true : false );
		
		if ( desconhecido === true ) {
			sistema.carregaComponente( 'field', super.getELID( 'situacao' ), { rotulo : "Situação:", valor : "Desconhecido(a)" } );
			elutil.showHide( super.getELID( "painel_dados" ) );				
		} else {
			let falecido_status = ( dados.falecido === 'true' ? 'Sim' : 'Não' );
			sistema.carregaComponente( 'field', super.getELID( 'falecido' ), { rotulo : "Falecido:", valor : falecido_status } );
			elutil.showHide( super.getELID( "painel_situacao" ) );

			this.pessoaDetalhesComponent.carrega( dados.pessoa );
		}				
	}
	
	mostrarEsconderPainelConteudo( e ) {
		elutil.showHide( super.getELID( 'painel_conteudo' ) );
	}
	
}