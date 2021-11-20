
import {sistema} from '../../../../sistema/Sistema.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

import BimestreFormComponent from './bimestre/BimestreFormComponent.js';

export default class AnoLetivoFormComponent extends RootFormComponent {
										
	constructor( formNome ) {
		super( formNome, 'mensagem_el' );
			
		this.primeiroBimestreFormComponent = new BimestreFormComponent( formNome, '', 'primeiro-bimestre-el' );	
		this.segundoBimestreFormComponent = new BimestreFormComponent( formNome, '', 'segundo-bimestre-el' );	
		this.terceiroBimestreFormComponent = new BimestreFormComponent( formNome, '', 'terceiro-bimestre-el' );	
		this.quartoBimestreFormComponent = new BimestreFormComponent( formNome, '', 'quarto-bimestre-el' );	
		
		super.addFilho( this.primeiroBimestreFormComponent );
		super.addFilho( this.segundoBimestreFormComponent );
		super.addFilho( this.terceiroBimestreFormComponent );
		super.addFilho( this.quartoBimestreFormComponent );	
	}			
	
	onConfigurado() {
		this.primeiroBimestreFormComponent.params.titulo = 'Primeiro bimestre';
		this.segundoBimestreFormComponent.params.titulo = 'Segundo bimestre';
		this.terceiroBimestreFormComponent.params.titulo = 'Terceiro bimestre';
		this.quartoBimestreFormComponent.params.titulo = 'Quarto bimestre';
	}
			
	carregouHTMLCompleto() {
		super.limpaTudo();
		
		if ( this.globalParams.op === 'editar' ) {
			let instance = this;
			sistema.ajax( "GET", "/api/anoletivo/get/"+this.globalParams.anoLetivoId, {
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
			primeiroBimestre : this.primeiroBimestreFormComponent.getJSON(),
			segundoBimestre : this.segundoBimestreFormComponent.getJSON(),
			terceiroBimestre : this.terceiroBimestreFormComponent.getJSON(),
			quartoBimestre : this.quartoBimestreFormComponent.getJSON()
		}
	}	
		
	carregaJSON( dados ) {
		this.primeiroBimestreFormComponent.carregaJSON( dados.primeiroBimestre );
		this.segundoBimestreFormComponent.carregaJSON( dados.segundoBimestre );
		this.terceiroBimestreFormComponent.carregaJSON( dados.terceiroBimestre );
		this.quartoBimestreFormComponent.carregaJSON( dados.quartoBimestre );
	}	
		
}
