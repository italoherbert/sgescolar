import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import TurmaTelaComponent from './TurmaTelaComponent.js';

export default class TurmaTelaService {

	colunas = [ 'Descrição', 'Turno', 'Serie', 'Curso', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new TurmaTelaComponent();
	}

	onCarregado() {				
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();	
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();				
	}
		
	detalhes( id ) {
		sistema.carregaPagina( 'turma-detalhes', { turmaId : id } );																	
	}
		
	filtra( tipo ) {	
		this.tabelaComponent.limpaMensagem();
			
		let url;	
		if ( tipo === 'serie' ) {
			let serieId = this.telaComponent.getFieldValue( 'serie' );
			url = '/api/turma/filtra/porserie/'+serieId;
		} else {
			let anoLetivoId = this.telaComponent.getFieldValue( 'anoletivo' );
			url = '/api/turma/filtra/poranoletivo/'+anoLetivoId;			
		}	
				
								
		const instance = this;	
		sistema.ajax( "POST", url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.telaComponent.getJSON() ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "turmaTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "turmaTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );					
					tdados[ i ].push( dados[ i ].turno.label );
					tdados[ i ].push( dados[ i ].serie.descricao );
					tdados[ i ].push( dados[ i ].serie.curso.descricao );
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
			titulo : "Remoção de turma",
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
		instance.tabelaComponent.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/turma/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Turma deletada com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'turma-form', { op : 'cadastrar', titulo : 'Registro de turma' } );
	}	

}
export const turmaTela = new TurmaTelaService();