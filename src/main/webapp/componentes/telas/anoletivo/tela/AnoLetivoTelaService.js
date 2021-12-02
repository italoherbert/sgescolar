import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import AnoLetivoTelaComponent from './AnoLetivoTelaComponent.js';

export default class AnoLetivoTelaService {

	colunas = [ 'Ano', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new AnoLetivoTelaComponent();
	}

	onCarregado() {				
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();	
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();
	}
	
	onChangeEscola() {
		let todosOsAnos = this.telaComponent.getFieldChecked( 'anostodos' );					
		let ano = this.telaComponent.getFieldValue( 'ano' );
		if ( todosOsAnos === true || isNaN( parseInt( ano ) ) === false )			
			this.busca();		
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'anoletivo-detalhes', { anoLetivoId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	busca() {
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();	
						
		let escolaId = perfilService.getEscolaID();
		
		if ( isNaN( parseInt( escolaId ) ) === true ) {
			this.tabelaComponent.mostraErro( 'Selecione uma escola.' );
			return;
		}
				
		let todosOsAnos = this.telaComponent.getFieldChecked( 'anostodos' );					
						
		let url;
		if ( todosOsAnos === true ) {
			url = '/api/anoletivo/lista/'+escolaId;
		} else {			
			let ano = this.telaComponent.getFieldValue( 'ano' );						
			url = '/api/anoletivo/filtra/'+escolaId+"/"+ano;
		}			
						
		const instance = this;	
		sistema.ajax( "GET", url, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
	
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "anoletivoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "anoletivoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].ano );
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de anoletivo",
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
		sistema.ajax( "DELETE", "/api/anoletivo/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Ano letivo deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'anoletivo-form', { titulo : "Registro de ano letivo", op : "cadastrar" } )
	}		

}
export const anoletivoTela = new AnoLetivoTelaService();