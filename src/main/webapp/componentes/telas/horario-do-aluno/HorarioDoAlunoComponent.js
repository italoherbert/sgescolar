
import {sistema} from '../../../../sistema/Sistema.js';

import RootComponent from '../../../component/RootComponent.js';
import HorarioComponent from '../../../component/horario/HorarioComponent.js';

export default class HorarioDoAlunoComponent extends RootComponent {
	
	horarioTabelaCampos = [ 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta' ];
	
	constructor() {
		super( 'mensagem_el' );	
		
		this.horarioComponent = new HorarioComponent( '', 'horario-tabela-el', this.horarioTabelaCampos );
		
		super.addFilho( this.horarioComponent );			
	}
	
	carregouHTMLCompleto() {
		const instance = this;		
		sistema.ajax( "GET", "/api/turma/get/"+this.globalParams.turmaId, {		
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );	
				instance.carrega( dados );																
			},
			erro : function( msg ) {
				instance.mostraErro( msg );	
			}
		} );		
	}
	
	carrega( dados ) {							
		let disciplinasVinculadas = dados.disciplinasVinculadas;
		
		this.horarioComponent.carregaJSON( disciplinasVinculadas );				
	}
	
}