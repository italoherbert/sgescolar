import {sistema} from "../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../sistema/util/HTMLBuilder.js";
import {conversor} from '../../../sistema/util/Conversor.js';

import TabelaComponent from '../../component/tabela/TabelaComponent.js';
import FeriadoTelaComponent from './FeriadoTelaComponent.js';
import FeriadoFormComponent from './form/FeriadoFormComponent.js';

export default class FeriadoTelaService {

	colunas = [ 'Descrição', 'Data de início', 'Data de fim', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.formComponent = new FeriadoFormComponent();
		this.telaComponent = new FeriadoTelaComponent();
		
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
		sistema.ajax( "GET", '/api/feriado/lista/'+anoLetivoId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "feriadoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( conversor.formataDataString( dados[ i ].dataInicio ) );
					tdados[ i ].push( conversor.formataDataString( dados[ i ].dataFim ) );
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
			this.formComponent.mostraErro( 'È necessário selecionar o ano letivo antes de efetuar algum registro de feriado.' );
			return;
		}
						
		const instance = this;	
		sistema.ajax( "POST", '/api/feriado/registra/'+anoLetivoId, {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( this.formComponent.getJSON() ),
			sucesso : ( resposta ) => {
				instance.formComponent.limpaTudo();
				instance.formComponent.mostraInfo( 'Feriado registrado com sucesso!' );
				instance.lista();
			},
			erro : ( msg ) => {
				this.formComponent.mostraErro( msg );
			}
		} );
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de feriado",
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
		sistema.limpaMensagem( "lista-mensagem-el" );
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/feriado/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.lista();
				sistema.mostraMensagemInfo( "lista-mensagem-el", 'Ano letivo deletado com êxito.' );
			},
			erro : function( msg ) {
				sistema.mostraMensagemErro( "lista-mensagem-el", msg );	
			}
		} );		
	}

}
export const feriadoTela = new FeriadoTelaService();