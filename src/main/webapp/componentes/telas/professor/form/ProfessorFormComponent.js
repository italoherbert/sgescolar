
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

import FuncionarioFormComponent from '../../../component/funcionario/form/FuncionarioFormComponent.js';

export default class ProfessorFormComponent extends RootFormComponent {
								
	tabelaDiplomasCampos = [ 'Curso', 'Titulação', 'Remover'];
	
	diplomas = [];			
	proxDiplomaId = 0;		
	
	novoHTMLLinkRemoveDiploma = null;			
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
		
		this.funcionarioFormComponent = new FuncionarioFormComponent( formNome, '', 'funcionario_form_el' );						
		this.diplomasTabelaComponent = new TabelaComponent( '', 'diplomas-tabela-el', this.tabelaDiplomasCampos ); 

		this.funcionarioFormComponent.usuarioFormComponent.carregaPerfis = ( sel_elid, onparams ) => this.carregaUsuarioPerfis( sel_elid, onparams );		
				
		super.addFilho( this.funcionarioFormComponent );
		super.addFilho( this.diplomasTabelaComponent );
	}			
			
	carregouHTMLCompleto() {
		super.limpaTudo();				
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/professor/get/"+this.globalParams.professorId, {
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
	
	addDiploma() {
		this.diplomas.push( {
			id : this.proxDiplomaId, 
			curso : super.getFieldValue( 'diploma_curso' ),
			titulacao : super.getFieldValue( 'diploma_titulacao' ) 
		} );
		
		this.proxDiplomaId++;
		
		super.setFieldValue( 'diploma_curso', '' );
		super.setFieldValue( 'diploma_titulacao', '' );
		
		this.carregaDiplomas();
	}
	
	removeDiploma( id ) {
		for( let i = 0; i < this.diplomas.length; i++ ) {
			if ( this.diplomas[ i ].id === id ) {
				this.diplomas.splice( i, 1 );
				this.carregaDiplomas();
				return;	
			}			
		}
	}
	
	carregaDiplomas() {
		let tdados = [];
		for( let i = 0; i < this.diplomas.length; i++ ) {
			let diploma = this.diplomas[ i ];
			
			tdados[ i ] = new Array();
			tdados[ i ].push( diploma.curso );
			tdados[ i ].push( diploma.titulacao );
			tdados[ i ].push( this.novoHTMLLinkRemoveDiploma( diploma.id ) );
		}
		this.diplomasTabelaComponent.carregaTBody( tdados );
	}
	
	carregaUsuarioPerfis( select_elid, onparams ) {
		selectService.carregaProfessorPerfisSelect( select_elid, onparams );			
	}
		
	getJSON() {				
		return {
			funcionario : this.funcionarioFormComponent.getJSON(),
			diplomas : this.diplomas
		}
	}	
		
	carregaJSON( dados ) {
		this.funcionarioFormComponent.carregaJSON( dados.funcionario );
		
		this.diplomas.splice( 0, this.diplomas.length );
		
		for( let i = 0; i < dados.diplomas.length; i++ ) {
			let diploma = dados.diplomas[ i ];
			
			this.diplomas.push( {
				id : this.proxDiplomaId, 
				curso : diploma.curso,
				titulacao : diploma.titulacao
			} );
			
			this.proxDiplomaId++;
		}
		this.carregaDiplomas();
	}	
										
}
