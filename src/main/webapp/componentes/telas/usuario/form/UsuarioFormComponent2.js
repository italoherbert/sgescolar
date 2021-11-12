
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import UsuarioFormComponent from '../../../component/usuario/form/UsuarioFormComponent.js';

export default class UsuarioFormComponent2 extends RootFormComponent {
						
	usuarioGrupos = [];
	outrosGrupos = [];					
										
	constructor( formNome ) {
		super( formNome, 'form-mensagem-el' );
		
		this.usuarioFormComponent = new UsuarioFormComponent( formNome, '', 'form-el' );		
		this.usuarioFormComponent.end.comp = 'usuario-form-end';
		this.usuarioFormComponent.carregaPerfis = ( sel_elid ) => this.carregaUsuarioPerfis( sel_elid );
				
		super.addFilho( this.usuarioFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		this.usuarioGrupos = [];

		if ( this.globalParams.op === 'editar' ) {
			this.edita( this.globalParams.usuarioId );
		} else {
			const instance = this;
			sistema.ajax( "GET", "/api/usuario/grupo/lista", {
				sucesso : ( resposta ) => {
					let dados = JSON.parse( resposta );
					instance.outrosGrupos = dados;
				}
			} );
		}																					
	}
	
	edita( id ) {
		const instance = this;						
		sistema.ajax( "GET", "/api/usuario/get/"+id, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
				instance.carregaJSON( dados );	
												
				sistema.ajax( "GET", "/api/usuario/grupo/lista", {
					sucesso : ( resposta ) => {
						let dados2 = JSON.parse( resposta );
														
						instance.outrosGrupos = [];
						for( let i = 0; i < dados2.length; i++ ) {
							let achou = false;
							for( let j = 0; achou === false && j < instance.usuarioGrupos.length; j++ )
								if ( dados2[ i ] === instance.usuarioGrupos[ j ] )
									achou = true;
							
							if ( achou === false )
								instance.outrosGrupos.push( dados2[ i ] );
						}
								
						instance.refreshGruposSelects();											
					}
				} );
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );
	}
		
	refreshGruposSelects() {		
		super.getEL( 'usuario-grupos-select' ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
			valores : this.usuarioGrupos 
		} );
		
		super.getEL( 'outros-grupos-select' ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
			valores : this.outrosGrupos
		} );
	}
				
	addGruposSelecionados() {
		let ug_sel_options = super.getEL( 'usuario-grupos-select' ).options;
		
		for( let i = 0; i < ug_sel_options.length; i++ ) {
			if ( ug_sel_options[ i ].selected === true ) {
				this.usuarioGrupos.splice( i, 1 );
				this.outrosGrupos.push( ug_sel_options[ i ].value );
			}
		}
		
		this.refreshGruposSelects();
	}
	
	removeGruposSelecionados() {
		let og_sel_options = super.getEL( 'outros-grupos-select' ).options;
		
		for( let i = 0; i < og_sel_options.length; i++ ) {
			if ( og_sel_options[ i ].selected === true ) {
				this.outrosGrupos.splice( i, 1 );
				this.usuarioGrupos.push( og_sel_options[ i ].value );
			}
		}
		
		this.refreshGruposSelects();
	}
	
	reiniciaUsuarioGrupos() {				
		for( let i = 0; i < this.usuarioGrupos.length; i++ )	
			this.outrosGrupos.push( this.usuarioGrupos[ i ] );
		
		this.usuarioGrupos.splice( 0, this.usuarioGrupos.length );
		
		this.refreshGruposSelects();
	}
			
	carregaUsuarioPerfis( select_elid ) {
		const instance = this;
		if ( this.globalParams.op !== 'editar' ) {				
			sistema.ajax( "GET", "/api/tipos/perfis/admin", {
				sucesso : ( resposta ) => {
					let dados = JSON.parse( resposta );
					
					instance.getEL( select_elid ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
						valores : dados, 
						defaultOption : { texto : 'Selecione o perfil', valor : '0' }
					} );				
				}
			} );	
		} else {
			sistema.ajax( "GET", "/api/usuario/get/"+this.globalParams.usuarioId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.getEL( select_elid ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
						valores : [dados.perfil] 
					} );
				}
			} );
		}
	}
			
	getJSON() {
		return Object.assign( {
				grupos : this.usuarioGrupos,	
			}, 
			this.usuarioFormComponent.getJSON()
		);
	}	
		
	carregaJSON( dados ) {
		this.usuarioFormComponent.carregaJSON( dados );
		
		this.usuarioGrupos = [];
		for( let i = 0; i < dados.grupos.length; i++ )
			this.usuarioGrupos.push( dados.grupos[ i ].nome );						
	}	
									
}