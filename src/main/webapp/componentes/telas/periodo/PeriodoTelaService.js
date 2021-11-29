import {sistema} from "../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../sistema/util/HTMLBuilder.js";
import {conversor} from '../../../sistema/util/Conversor.js';

import TabelaComponent from '../../component/tabela/TabelaComponent.js';
import PeriodoFormComponent from './form/PeriodoFormComponent.js';
import PeriodoTelaComponent from './PeriodoTelaComponent.js';

export default class PeriodoTelaService {

	colunas = [ 'Tipo', 'Quant. dias letivos', 'Início', 'Fim', 'Lançamento prazo inicial', 'Lançamento prazo final', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.formComponent = new PeriodoFormComponent();
		this.telaComponent = new PeriodoTelaComponent();
		
		this.telaComponent.onChangeAnoLetivo = (e) => this.lista();
	}

	onCarregado() {		
		this.formComponent.configura( {} );
		this.formComponent.carregaHTML();
		
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();		
	}
			
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	lista() {	
		this.telaComponent.limpaMensagem();			

		this.tabelaComponent.limpaMensagem();					
		this.tabelaComponent.limpaTBody();					
		
		let anoLetivoId = this.telaComponent.getFieldValue( 'anoletivo' );
						
		const instance = this;	
		sistema.ajax( "GET", '/api/periodo/lista/'+anoLetivoId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "periodoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].tipo );
					tdados[ i ].push( dados[ i ].diasLetivosQuant );
					tdados[ i ].push( conversor.formataDataString( dados[ i ].dataInicio ) );
					tdados[ i ].push( conversor.formataDataString( dados[ i ].dataFim ) );
					tdados[ i ].push( conversor.formataDataString( dados[ i ].lancamentoDataInicio ) );
					tdados[ i ].push( conversor.formataDataString( dados[ i ].lancamentoDataFim ) );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );	
	}
	
	registra() {
		this.formComponent.limpaMensagem();
		this.tabelaComponent.limpaMensagem();
		
		let anoLetivoId = this.telaComponent.getFieldValue( 'anoletivo' );
		
		if ( isNaN( parseInt( anoLetivoId ) ) === true ) {
			this.formComponent.mostraErro( 'È necessário selecionar o ano letivo antes de efetuar algum registro de período.' );
			return;
		}
						
		const instance = this;	
		sistema.ajax( "POST", '/api/periodo/registra/'+anoLetivoId, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( this.formComponent.getJSON() ),
			sucesso : ( resposta ) => {
				instance.formComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Periodo registrado com sucesso!' );
				instance.lista();
			},
			erro : ( msg ) => {
				instance.formComponent.mostraErro( msg );
			}
		} );
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de periodo",
			msg :  "Digite abaixo o nome <span class='text-danger'>remova</span> para confirmar a remoção",			
			confirm : {
				texto : 'remova',
				bt : {
					rotulo : "Remover",
					onclick : {
						func : function( pars ) {
							this.remove( pars.id );	
						},
						thisref : this,
						params : { id : id }
					}
				}
			}			
		} );
	}

	remove( id ) {			
		this.tabelaComponent.limpaMensagem();	
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/periodo/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				instance.tabelaComponent.mostraInfo( 'Período deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraInfo( msg );	
			}
		} );		
	}

}
export const periodoTela = new PeriodoTelaService();