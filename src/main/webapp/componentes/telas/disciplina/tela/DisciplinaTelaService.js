import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import DisciplinaTelaComponent from './DisciplinaTelaComponent.js';

export default class DisciplinaTelaService {

	colunas = [ 'Descrição', 'Sigla', 'Serie', 'Curso', 'Escola', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
		this.telaComponent = new DisciplinaTelaComponent();		
	}

	onCarregado() {				
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
		
		this.telaComponent.configura( {} );
		this.telaComponent.carregaHTML();					
	}
		
	detalhes( id ) {
		sistema.carregaPagina( 'disciplina-detalhes', { disciplinaId : id } );																	
	}
		
	filtra() {	
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
				
		let serieId = this.telaComponent.getFieldValue( 'serie' );
								
		const instance = this;	
		sistema.ajax( "POST", "/api/disciplina/filtra/"+serieId, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.telaComponent.getJSON() ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "disciplinaTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "disciplinaTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].descricao );
					tdados[ i ].push( dados[ i ].sigla );
					tdados[ i ].push( dados[ i ].serie.descricao);
					tdados[ i ].push( dados[ i ].serie.curso.descricao );
					tdados[ i ].push( dados[ i ].serie.curso.escolaNome );
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );				
				
				if ( dados.length == 0 )
					instance.tabelaComponent.mostraInfo( 'Nenhuma disciplina encontrada pelos critérios de busca informados.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de disciplina",
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
		sistema.ajax( "DELETE", "/api/disciplina/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Disciplina deletada com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'disciplina-form', { titulo : "Registro de disciplina", op : "cadastrar" } )
	}		

}
export const disciplinaTela = new DisciplinaTelaService();