
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class CursoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		const instance = this;
		if ( this.globalParams.op === 'editar' ) {
			sistema.ajax( "GET", "/api/curso/get/"+this.globalParams.cursoId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );				
		} else {
			selectService.carregaInstituicoesSelect( 'instituicoes_select', { 
				onchange : () => {
					let instituicaoId = instance.getFieldValue( 'instituicao' );
					selectService.carregaEscolasSelect( instituicaoId, 'escolas_select' );	
				} 
			} );
			
			selectService.carregaCursoModalidadesSelect( 'modalidades_select' );
		}
	}	
		
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			modalidade : super.getFieldValue( 'modalidade' ),
			cargaHoraria : super.getFieldValue( 'carga_horaria' )
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;
		selectService.carregaInstituicoesSelect( 'instituicoes_select', { 
			onload : () => {
				instance.setFieldValue( 'instituicao', dados.instituicaoId );
				selectService.carregaEscolasSelect( dados.instituicaoId, 'escolas_select', {
					onload : () => {
						instance.setFieldValue( 'escola', dados.escolaId );
					}
				} );	
			} 
		} );
		
		selectService.carregaCursoModalidadesSelect( 'modalidades_select', {
			onload : () => {
				instance.setFieldValue( 'modalidade', dados.modalidade.name );
			}	
		} );
		
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'carga_horaria', dados.cargaHoraria );		
	}	
		
	limpaForm() {
		super.setFieldValue( 'instituicao', "0" );
		super.setFieldValue( 'escola', "0" );		
		super.setFieldValue( 'modalidade', "0" );		
		super.setFieldValue( 'descricao', "" );		
		super.setFieldValue( 'carga_horaria', "" );		
	}		
}
