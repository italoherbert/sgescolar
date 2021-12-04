import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import CursoTelaComponent from './CursoTelaComponent.js';

export default class CursoTelaService {

	colunas = [ 'Descrição', 'Modalidade', 'Escola', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new CursoTelaComponent();		
	}

	onCarregado() {				
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();	
	}

	detalhes( id ) {
		sistema.carregaPagina( 'curso-detalhes', { cursoId : id } );																	
	}
		
	filtra() {	
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
				
		let escolaId = perfilService.getEscolaID();
		
		const instance = this;	
		sistema.ajax( "POST", "/api/curso/filtra/"+escolaId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.telaComponent.getJSON() ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "cursoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "cursoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( dados[ i ].modalidade.label );
					tdados[ i ].push( dados[ i ].escolaNome );
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
			titulo : "Remoção de curso",
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
		sistema.ajax( "DELETE", "/api/curso/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Curso deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'curso-form', { titulo : "Registro de curso", op : "cadastrar" } )
	}		

}
export const cursoTela = new CursoTelaService();