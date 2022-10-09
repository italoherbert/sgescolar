
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';
import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class DisciplinaFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/disciplina/get/"+this.globalParams.disciplinaId, {
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
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' ),
			sigla : super.getFieldValue( 'sigla' )
		}
	}	
		
	carregaJSON( dados ) {			
		selectService.carregaUmaOptionSelect( 'cursos_select', dados.serie.curso.id, dados.serie.curso.descricao );	
		selectService.carregaUmaOptionSelect( 'series_select', dados.serie.id, dados.serie.descricao );	
									
		perfilService.setInstituicaoID( dados.serie.curso.instituicaoId );
		perfilService.setEscolaID( dados.serie.curso.escolaId );
				
		super.setFieldValue( 'descricao', dados.descricao );
		super.setFieldValue( 'sigla', dados.sigla );
	}	
		
	limpaForm() {			
		super.setFieldValue( 'descricao', "" );		
		super.setFieldValue( 'sigla', '' );
	}		
}
