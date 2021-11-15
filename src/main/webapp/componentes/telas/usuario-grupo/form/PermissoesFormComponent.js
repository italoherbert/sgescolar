
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

import RootFormComponent from '../../../component/RootFormComponent.js';
import TabelaComponent from '../../../component/TabelaComponent.js';

export default class PermissoesFormComponent extends RootFormComponent {	
										
	permissoesTabelaCampos = [ 'Nome do recurso', 'Leitura', 'Escrita', 'Remoção' ];	
	recursosNomes = [];								
										
	constructor( formNome ) {
		super( formNome, 'mensagem-el' );				
		this.permissoesTabelaComponent = new TabelaComponent( 'tabela', 'tabela-permissoes-el', this.permissoesTabelaCampos );
		
		super.addFilho( this.permissoesTabelaComponent );
	}			
						
	getJSON() {
		let permissaoGrupos = [];		
		for( let i = 0; i < this.recursosNomes.length; i++ ) {
			let recursoNome = this.recursosNomes[ i ];

			permissaoGrupos[ i ] = {
				recurso : recursoNome,
				leitura : super.getFieldChecked( recursoNome + "_LEITURA" ),
				escrita : super.getFieldChecked( recursoNome + "_ESCRITA" ),
				remocao : super.getFieldChecked( recursoNome + "_REMOCAO" ),
			}			
		}
		
		return permissaoGrupos;
	}	
		
	carregaJSON( dados ) {
		this.recursosNomes = [];
		
		let tdados = [];
		for( let i = 0; i < dados.length; i++ ) {
			tdados[ i ] = new Array();
			
			let recursoNome = dados[ i ].recurso;
			
			tdados[ i ].push( recursoNome );
			tdados[ i ].push( htmlBuilder.novoCheckboxHTML( recursoNome + '_LEITURA', dados[ i ].leitura ) );
			tdados[ i ].push( htmlBuilder.novoCheckboxHTML( recursoNome + '_ESCRITA', dados[ i ].escrita ) );
			tdados[ i ].push( htmlBuilder.novoCheckboxHTML( recursoNome + '_REMOCAO', dados[ i ].remocao ) );
			
			this.recursosNomes[ i ] = recursoNome;
		}
		
		this.permissoesTabelaComponent.carregaTBody( tdados );					
	}	
	
	limpaForm() {		
		this.recursosNomes = [];
		this.permissoesTabelaComponent.limpaTBody();
	}
					
}