
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class TurmaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/turma/get/"+this.globalParams.turmaId, {
				sucesso : function( resposta ) {
					let dados = JSON.parse( resposta );
					instance.carregaJSON( dados );						
				},
				erro : function( msg ) {
					instance.mostraErro( msg );	
				}
			} );
		} else {			
			let escolaId = perfilService.getEscolaID();
			if ( escolaId === '-1' ) {
				this.mostraErro( 'Escola não selecionada.' );
				return;	
			}
			
			selectService.carregaCursosSelect( escolaId, 'cursos_select', {
				onchange : () => {
					let cursoId = instance.getFieldValue( 'curso' );
					selectService.carregaSeriesSelect( cursoId, 'series_select' );
				}
			} );
			selectService.carregaAnosLetivosSelect( escolaId, 'anosletivos_select' );	
			
			selectService.carregaTurnosSelect( 'turnos_select' );		
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			quantidadeAulasDia : super.getFieldValue( 'quantidade_aulas_dia' ),
			turno : super.getFieldValue( 'turno' )
		}
	}	
		
	carregaJSON( dados ) {
		const instance = this;	
		
		perfilService.setInstituicaoID( dados.serie.curso.instituicaoId );
		perfilService.setEscolaID( dados.serie.curso.escolaId );
		
		selectService.carregaUmaOptionSelect( 'anosletivos_select', dados.anoLetivoId, dados.anoLetivoAno );
		selectService.carregaUmaOptionSelect( 'cursos_select', dados.serie.curso.id, dados.serie.curso.descricao );
		selectService.carregaUmaOptionSelect( 'series_select', dados.serie.id, dados.serie.descricao );
							
		
		selectService.carregaTurnosSelect( 'turnos_select', {
			onload : () => {
				instance.setFieldValue( 'turno', dados.turno.name );
			}
		} );
		
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'quantidade_aulas_dia', dados.quantidadeAulasDia );
	}	
		
	limpaForm() {	
		super.setFieldValue( 'turno', '-1' );
		super.setFieldValue( 'descricao', "" );		
	}		
}
