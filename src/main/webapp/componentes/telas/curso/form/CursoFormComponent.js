
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class CursoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		selectService.carregaEscolasSelect( 'escolas_select', { onload : () => this.onEscolasCarregadas() } );				
	}
	
	onEscolasCarregadas() {
		selectService.carregaCursoModalidadesSelect( 'modalidades_select', { onload : () => this.onModalidadesCarregadas() } );						
	}
	
	onModalidadesCarregadas() {
		super.limpaTudo();
				
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			
			sistema.ajax( "GET", "/api/curso/get/"+this.globalParams.cursoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		}
	}
		
	getJSON() {
		return {
			nome : super.getFieldValue( 'nome' ),
			modalidade : super.getFieldValue( 'modalidade' ),
			cargaHoraria : super.getFieldValue( 'carga_horaria' )
		}
	}	
		
	carregaJSON( dados ) {
		super.setFieldValue( 'escola', dados.escolaId );
		super.setFieldValue( 'nome', dados.nome );
		super.setFieldValue( 'carga_horaria', dados.cargaHoraria );		
		super.setFieldValue( 'modalidade', dados.modalidade.name );
	}	
		
	limpaForm() {
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'modalidade', "0" );		
		super.setFieldValue( 'nome', "" );		
		super.setFieldValue( 'carga_horaria', "" );		
	}		
}
