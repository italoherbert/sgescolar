
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

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
			selectService.carregaCursoModalidadesSelect( 'modalidades_select' );
		}
	}	
		
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			modalidade : super.getFieldValue( 'modalidade' ),
			cargaHoraria : super.getFieldValue( 'carga_horaria' ),
			quantidadeAulasDia : super.getFieldValue( 'quantidade_aulas_dia' ),
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;		
		
		perfilService.setInstituicaoID( dados.instituicaoId );
		perfilService.setEscolaID( dados.escolaId );
		
		selectService.carregaCursoModalidadesSelect( 'modalidades_select', {
			onload : () => {
				instance.setFieldValue( 'modalidade', dados.modalidade.name );
			}	
		} );
														
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'carga_horaria', dados.cargaHoraria );		
		super.setFieldValue( 'quantidade_aulas_dia', dados.quantidadeAulasDia );
	}	
		
	limpaForm() {	
		super.setFieldValue( 'descricao', "" );		
		super.setFieldValue( 'carga_horaria', "" );
		super.setFieldValue( 'quantidade_aulas_dia', '' );		
	}		
}
