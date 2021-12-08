import {sistema} from "../../../../sistema/Sistema.js";
import {htmlBuilder} from "../../../../sistema/util/HTMLBuilder.js";

import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class ProfessorTelaService {

	colunas = [ 'Nome', 'Telefone', 'E-Mail', 'Detalhes', 'Remover' ];

	constructor() {
		this.tabelaComponent = new TabelaComponent( '', 'tabela-el', this.colunas );
	}

	onCarregado() {			
		this.tabelaComponent.configura( {} );
		this.tabelaComponent.carregaHTML();
	}
	
	detalhes( id ) {
		sistema.carregaPagina( 'professor-detalhes', { professorId : id } );																	
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
		sistema.ajax( "POST", "/api/professor/filtra/", {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( {
				nomeIni : document.professor_filtro_form.nomeini.value
			} ),
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
																											
				let tdados = [];
				for( let i = 0; i < dados.length; i++ ) {
					let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( "professorTela.detalhes( " + dados[ i ].id + " )" );
					let removerLink = htmlBuilder.novoLinkRemoverHTML( "professorTela.removeConfirm( " + dados[ i ].id + " )" );
					
					tdados[ i ] = new Array();
					tdados[ i ].push( dados[ i ].funcionario.pessoa.nome );
					tdados[ i ].push( dados[ i ].funcionario.pessoa.contatoInfo.telefoneCelular );
					tdados[ i ].push( dados[ i ].funcionario.pessoa.contatoInfo.email );
					tdados[ i ].push( detalhesLink );
					tdados[ i ].push( removerLink );					
				}
								
				instance.tabelaComponent.carregaTBody( tdados );						
				
				if ( dados.length == 0 )
					instance.tabelaComponent.mostraInfo( 'Nenhum professor encontrado pelos critérios de busca informados.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );	
	}
	
	removeConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de professor",
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
		sistema.ajax( "DELETE", "/api/professor/deleta/"+id, {
			sucesso : function( resposta ) {						
				instance.filtra();
				instance.tabelaComponent.mostraInfo( 'Professor deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.tabelaComponent.mostraErro( msg );	
			}
		} );		
	}
	
	paraFormRegistro() {
		sistema.carregaPagina( 'professor-form', { titulo : "Registro de professor", op : "cadastrar" } );
	}	

}
export const professorTela = new ProfessorTelaService();