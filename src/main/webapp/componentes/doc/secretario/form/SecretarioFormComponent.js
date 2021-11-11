
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import FuncionarioFormComponent from '../../../component/funcionario/form/FuncionarioFormComponent.js';

export default class SecretarioFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.funcionarioFormComponent = new FuncionarioFormComponent( formNome, '', 'funcionario_form_el' );
		
		this.funcionarioFormComponent.usuarioFormComponent.carregaPerfis = ( sel_elid ) => this.carregaUsuarioPerfis( sel_elid );
				
		super.addFilho( this.funcionarioFormComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
						
		const instance = this;						
		sistema.ajax( "POST", "/api/escola/filtra", {
			cabecalhos : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			corpo : JSON.stringify( {
				nomeIni : '*'
			} ),
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
																
				let textos = [];
				let valores = [];
				for( let i = 0; i < dados.length; i++ ) {
					textos.push( dados[ i ].nome );
					valores.push( dados[ i ].id );
				}
												
				super.getEL( "escolas_select" ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					textos : textos, 
					valores : valores,
					defaultOption : { texto : 'Selecione a escola', valor : '0' } 
				} );
								
				if ( instance.globalParams.op === 'editar' ) {
					sistema.ajax( "GET", "/api/secretario/get/"+instance.globalParams.secretarioId, {
						sucesso : function( resposta2 ) {
							let dados2 = JSON.parse( resposta2 );
							instance.carregaJSON( dados2 );						
						},
						erro : function( msg ) {
							instance.mostraErro( msg );	
						}
					} );
				}	
			}
		} );				
	}
		
	carregaUsuarioPerfis( select_elid ) {
		sistema.ajax( "GET", "/api/tipos/perfis/secretario", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				super.getEL( select_elid ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : dados, 
					defaultOption : { texto : 'Selecione o perfil', valor : '0' }
				} );				
			}
		} );	
	}
			
	getJSON() {
		return {
			escolaId : super.getFieldValue( 'escola' ),
			funcionario : this.funcionarioFormComponent.getJSON(),
		}
	}	
		
	carregaJSON( dados ) {
		this.setFieldValue( 'escola', dados.escolaId ),		
		this.funcionarioFormComponent.carregaJSON( dados.funcionario );
	}	
								
}
