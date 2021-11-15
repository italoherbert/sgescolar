
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';
import PermissoesFormComponent from './PermissoesFormComponent.js';

export default class UsuarioGrupoFormComponent extends RootFormComponent {												
										
	constructor( formNome ) {
		super( formNome, 'mensagem-el' );				
		this.permissoesFormComponent = new PermissoesFormComponent( formNome, 'mensagem-el' );
		
		super.addFilho( this.permissoesFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		if ( this.globalParams.op === 'editar' )
			this.carrega( this.globalParams.usuarioGrupoId );		
	}
				
	getJSON() {		
		return {
			nome : super.getFieldValue( 'nome' ),
			permissaoGrupos : this.permissoesFormComponent.getJSON()
		};
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'nome', dados.nome );

		this.permissoesFormComponent.carregaJSON( dados.permissaoGrupos );						
	}	
	
	limpaForm() {
		super.setFieldValue( 'nome', '' );		
	}
	
	carrega( id ) {
		let instance = this;
		sistema.ajax( "GET", "/api/usuario/grupo/get/"+id, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaJSON( dados );
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );
	}
	
	sincronizaRecursos( op, grupoId ) {
		const instance = this;
		if ( op === 'editar' ) {
			sistema.ajax( 'POST', '/api/usuario/grupo/recursos/sincroniza/'+grupoId, {
				sucesso : ( resposta ) => {		
					instance.permissoesFormComponent.limpaTudo();			
					instance.carrega( grupoId );
					instance.mostraInfo( 'Sincronização de recursos realizada com êxito.' );
				},
				erro : ( msg ) => {
					instance.mostraErro( msg );
				}
			} );
		} else {
			sistema.ajax( 'GET', '/api/recurso/lista', {
				sucesso : ( resposta ) => {
					let dados = JSON.parse( resposta );
					let permissoes = [];
					for( let i = 0; i < dados.length; i++ )
						permissoes[ i ] = { recurso : dados[ i ].nome, leitura : 'false', escrita : 'false', remocao : 'false' };					
					
					instance.permissoesFormComponent.limpaTudo();			
					instance.permissoesFormComponent.carregaJSON( permissoes );
					instance.mostraInfo( 'Sincronização de recursos realizada com êxito.' );
				},
				erro : ( msg ) => {
					instance.mostraErro( msg );
				}
			} )
		}
	}
	
}
