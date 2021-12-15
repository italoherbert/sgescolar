
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import PlanejamentoFormComponent from './PlanejamentoFormComponent.js';

export default class PlanejamentoFormService {											
						
	constructor() {
		this.component = new PlanejamentoFormComponent();
		this.component.removeObjetivoHTMLLink = this.removeObjetivoHTMLLink;
		this.component.removeConteudoHTMLLink = this.removeConteudoHTMLLink;
		this.component.removeAnexoFieldHTMLLink = this.removeAnexoFieldHTMLLink;
		this.component.deletaAnexoHTMLLink = this.deletaAnexoHTMLLink; 		
	}					
																
	onCarregado() {			
		this.component.configura( {
			planejamentoId : this.params.planejamentoId,
			op : this.params.op
		} );		
		this.component.carregaHTML();				
	}
		
	addObjetivo() {
		this.component.addObjetivo();
	}	
	
	addConteudo() {
		this.component.addConteudo();
	}
	
	addAnexoField() {
		this.component.addAnexoField();
	}
	
	removeObjetivo( i ) {
		this.component.removeObjetivo( i );	
	}
	
	removeConteudo( i ) {
		this.component.removeConteudo( i );
	}
		
	removeAnexoField( i ) {
		this.component.removeAnexoField( i );
	}
		
	removeObjetivoHTMLLink( i ) {
		return htmlBuilder.novoLinkRemoverHTML( "planejamentoForm.removeObjetivo( " + i + " )" );
	}
	
	removeConteudoHTMLLink( i ) {
		return htmlBuilder.novoLinkRemoverHTML( "planejamentoForm.removeConteudo( " + i + " )" );
	}
	
	removeAnexoFieldHTMLLink( i ) {
		return htmlBuilder.novoLinkRemoverHTML( "planejamentoForm.removeAnexoField( " + i + " )" );
	}
	
	deletaAnexoHTMLLink( id ) {
		return htmlBuilder.novoLinkRemoverHTML( "planejamentoForm.removeAnexoConfirm( " + id + " )" );
	}
	
	copiaPlanoEnsinoConteudo() {
		this.component.copiaPlanoEnsinoConteudo();
	}
										
	salva() {								
		this.component.limpaMensagem();
				
		let url;
		let metodo;
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = '/api/planejamento/atualiza/'+this.params.planejamentoId;
		} else {
			let professorAlocacaoId = this.component.getFieldValue( 'professor_alocacao' );
			
			metodo = 'POST';
			url = '/api/planejamento/registra/'+professorAlocacaoId;
		}				
								
		let formdata = new FormData();
		formdata.append( "dados", JSON.stringify( this.component.getJSON() ) );
		
		let anexos = this.component.getAnexos();
		for( let i = 0; i < anexos.length; i++ )		
			formdata.append( "files", anexos[i] );						
										
		let instance = this;
		sistema.ajax( metodo, url, {
			corpo : formdata,
			sucesso : function( resposta ) {					
				instance.component.limpaTudo();
				instance.component.mostraInfo( 'Planejamento salvo com êxito.' );																
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	removeAnexoConfirm( id ) {
		sistema.carregaConfirmModal( 'remover-modal-el', {
			titulo : "Remoção de anexo",
			msg :  "Digite abaixo o nome <span class='text-danger'>remova</span> para confirmar a remoção",			
			confirm : {
				texto : 'remova',
				bt : {
					rotulo : "Remover",
					onclick : {
						func : function( pars ) {
							this.removeAnexo( pars.id );	
						},
						thisref : this,
						params : { id : id }
					}
				}
			}			
		} );
	}

	removeAnexo( id ) {				
		this.component.limpaMensagem();
		
		const instance = this;
		sistema.ajax( "DELETE", "/api/planejamento/anexo/deleta/"+id, {
			sucesso : function( resposta ) {	
				instance.component.removeAnexoPorId( id );			
				instance.component.carregaAnexos();
				instance.component.mostraInfo( 'Anexo deletado com êxito.' );
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );		
	}
	
	paraTela() {
		sistema.carregaPagina( 'planejamento-tela' );
	}
			
}
export const planejamentoForm = new PlanejamentoFormService();