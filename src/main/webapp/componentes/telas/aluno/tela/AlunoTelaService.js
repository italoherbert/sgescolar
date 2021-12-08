
import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class AlunoTelaService {

	colunas = [ 'Nome', 'Telefone', 'E-Mail', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
	}

	detalhes( id ) {
		sistema.carregaPagina( 'aluno-detalhes', { alunoId : id } );																	
	}
	
	onTeclaPressionada( e ) {
		e.preventDefault();
				
		if ( e.keyCode === 13 )
			this.filtra();
	}
	
	filtra() {
		this.tabelaComponent.limpaMensagem();
		this.tabelaComponent.limpaTBody();
						
		const instance = this;		
		sistema.ajax( "POST", "/api/aluno/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.aluno_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "alunoTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "alunoTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].pessoa.nome );
					tdados[ i ].push( dados[ i ].pessoa.contatoInfo.telefoneCelular );
					tdados[ i ].push( dados[ i ].pessoa.contatoInfo.email );
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );				
				
				if ( dados.length == 0 )
					instance.tabelaComponent.mostraInfo( 'Nenhum aluno encontrado pelos critérios de busca informados.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de aluno",
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
		sistema.ajax( "DELETE", "/api/aluno/deleta/"+id, {
			sucesso : function( resposta ) {
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Aluno deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );				
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'aluno-form', { titulo : "Registro de aluno", op : "cadastrar" } )
	}		

}
export const alunoTela = new AlunoTelaService();