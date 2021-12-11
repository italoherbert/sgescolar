import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import PlanejamentoTelaComponent from './PlanejamentoTelaComponent.js'

export default class PlanejamentoTelaService {

	colunas = [ 'Descricao', 'Data de início',  'Data de fim', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new PlanejamentoTelaComponent();		
	}

	onCarregado() {
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();	
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'planejamento-detalhes', { planejamentoId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {	
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
						
		let professorAlocacaoId = this.telaComponent.getFieldValue( 'professor_alocacao' );				
						
		const instance = this;	
		sistema.ajax( "POST", "/api/planejamento/filtra/"+professorAlocacaoId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.telaComponent.getJSON() ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "planejamentoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "planejamentoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( dados[ i ].dataIni );
					tdados[ i ].push( dados[ i ].dataFim );
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );				
				
				if ( dados.length == 0 )
					instance.tabelaComponent.mostraInfo( 'Nenhum planejamento encontrado pelos critérios de busca informados.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de planejamento",
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
		sistema.ajax( "DELETE", "/api/planejamento/deleta/"+id, {
			sucesso : function( resposta ) {				
				perfilService.recarregaComponente();		
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Planejamento deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraRegistroForm() {
		sistema.carregaPagina( 'planejamento-form', { titulo : "Registro de planejamento", op : "cadastrar" } );		
	}		

}
export const planejamentoTela = new PlanejamentoTelaService();