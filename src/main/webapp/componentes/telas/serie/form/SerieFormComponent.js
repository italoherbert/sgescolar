
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import {perfilService} from '../../../layout/app/perfil/PerfilService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class SerieFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );				
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();

		const instance = this;				
		if ( this.globalParams.op === 'editar' ) {			
			sistema.ajax( "GET", "/api/serie/get/"+this.globalParams.serieId, {
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
			
			selectService.carregaCursosSelect( escolaId, 'cursos_select' );								
		}			
	}
				
	getJSON() {
		return {
			descricao : super.getFieldValue( 'descricao' )
		}
	}	
		
	carregaJSON( dados ) {		
		perfilService.setInstituicaoID( dados.curso.instituicaoId );
		perfilService.setEscolaID( dados.curso.escolaId );
		
		selectService.carregaUmaOptionSelect( 'cursos_select', dados.curso.id, dados.curso.descricao );
				
		super.setFieldValue( 'descricao', dados.descricao );
	}	
		
	limpaForm() {	
		super.setFieldValue( 'descricao', "" );		
	}		
}
