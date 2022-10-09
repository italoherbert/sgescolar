
import {sistema} from '../../../sistema/Sistema.js';
import {conversor} from '../../../sistema/util/Conversor.js';
import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';
import * as elutil from '../../../sistema/util/elutil.js';

import {selectService} from '../../service/SelectService.js';

import TabelaComponent from '../../component/tabela/TabelaComponent.js';
import AlunoAutoCompleteFormComponent from '../../autocomplete/AlunoAutoCompleteFormComponent.js';

export default class BoletimTelaService {			
		
	mediasBoletimTabelaCampos = [ 'Disciplina', 'Média', 'Avaliações' ];
	avaliacoesBoletimTabelaCampos = [ 'Data de avaliação', 'Resultado', 'Peso' ];
	
	boletim = null;
	alunoId = -1;
	
	constructor() {		
		this.mediasTabelaComponent = new TabelaComponent( 'medias_', 'boletim_tabela_el', this.mediasBoletimTabelaCampos );
		this.avaliacoesTabelaComponent = new TabelaComponent( 'avaliacoes_', 'boletim_tabela_el', [] );
		
		this.alunoAutoCompleteFormComponent = new AlunoAutoCompleteFormComponent( 'boletim_form', 'aluno-autocomplete-el' );
		this.alunoAutoCompleteFormComponent.onItemSelecionado = ( sid, svalue) => this.onAlunoSelecionado( sid, svalue );					
	}
	
	onCarregado() {
		this.mediasTabelaComponent.configura( {} );
		this.avaliacoesTabelaComponent.configura( {} );
		this.alunoAutoCompleteFormComponent.configura( {} );

		this.mediasTabelaComponent.carregaHTML();
		this.avaliacoesTabelaComponent.carregaHTML();		
		
		elutil.hide( 'descricao-painel-el' );

		if ( sistema.globalVars.perfil.name === 'ALUNO' || this.params.alunoId !== undefined && this.params.alunoId !== null ) {
			if ( this.params.alunoId !== undefined && this.params.alunoId !== null ) {
				this.alunoId = this.params.alunoId;
			} else {				
				this.alunoId = sistema.globalVars.entidadeId;
			}
			
			selectService.carregaAnosLetivosPorAlunoSelect( this.alunoId, 'anosletivos_select' );
			
			elutil.hide( 'selecao-aluno-el' );
		} else {
			this.alunoAutoCompleteFormComponent.carregaHTML();
		}				
	}
	
	onAlunoSelecionado( sid, svalue ) {
		this.alunoId = sid;
		selectService.carregaAnosLetivosPorAlunoSelect( this.alunoId, 'anosletivos_select' );
	}
		
	carregaBoletim() {				
		let anoLetivoId = document.boletim_form.anoletivo.value;

		elutil.hide( 'descricao-painel-el' );

		this.avaliacoesTabelaComponent.limpaTudo();

		const instance = this;
		sistema.ajax( 'GET', '/api/boletim/gera/'+this.alunoId+'/'+anoLetivoId, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );	
				
				instance.boletim = dados;
				
				let tdados = [];
				for( let i = 0; i < dados.disciplinasBoletins.length; i++ ) {
					let discBoletim = dados.disciplinasBoletins[ i ];
					
					let disciplina = discBoletim.disciplinaDescricao;
					
					tdados[ i ] = new Array();
					tdados[ i ].push( disciplina );
					
					if ( discBoletim.avaliacoes.length === 0 ) {
						tdados[ i ].push( 'Indisponível' );
						tdados[ i ].push( '-' );
					} else {					
						let media = conversor.valorFloat( discBoletim.media );
						let mostrarAvaliacoesLink = htmlBuilder.novoLinkHTML( 'visualizar', 'boletimTela.carregaAvaliacoes( '+i+' )', 'fas fa-eye' );
						
						tdados[ i ].push( conversor.formataFloat( media ) );	
						tdados[ i ].push( mostrarAvaliacoesLink );
					}
				}
				
				instance.mediasTabelaComponent.carregaTBody( tdados );				
			},
			erro : ( msg ) => {
				instance.mediasTabelaComponent.mostraErro( msg );
			}
		} );
	}
	
	carregaAvaliacoes( i ) {
		let discBoletim = this.boletim.disciplinasBoletins[ i ];
		
		document.getElementById( 'disciplina-el' ).innerHTML = discBoletim.disciplinaDescricao;		
		
		let tdados = [];
		for( let j = 0; j < discBoletim.avaliacoes.length; j++ ) {
			let avaliacao = discBoletim.avaliacoes[ j ];
			
			tdados[ j ] = new Array();
			tdados[ j ].push( avaliacao.dataAvaliacao );						
			
			switch( avaliacao.avaliacaoTipo.name ) {
				case 'NUMERICA':
					tdados[ j ].push( conversor.formataFloat( avaliacao.resultado ) );
					break;	
				case 'CONCEITUAL':
					tdados[ j ].push( avaliacao.resultado );
					break;
				case 'DESCRITIVA':					
					let html = htmlBuilder.novoLinkHTML( 'exibir', 'boletimTela.exibirAnaliseDescritiva( '+i+','+j+' )', 'far fa-eye', 'link-primary' );
					tdados[ j ].push( html );
					break;
				default:
					tdados[ j ].push( 'Indisponível' );			
			}
			
			if( avaliacao.avaliacaoTipo.name === 'NUMERICA' ) {
				tdados[ j ].push( conversor.formataFloat( avaliacao.peso ) );
			} else {
				tdados[ j ].push( "-" ); 
			}			
		}				
		
		this.avaliacoesTabelaComponent.tabelaCampos = this.avaliacoesBoletimTabelaCampos;
		this.avaliacoesTabelaComponent.carregaTHead();
		this.avaliacoesTabelaComponent.carregaTBody( tdados );
	}
	
	exibirAnaliseDescritiva( i, j ) {
		let resultado = this.boletim.disciplinasBoletins[ i ].avaliacoes[ j ].resultado;					
		document.getElementById( 'descricao-el' ).innerHTML = resultado;
		
		elutil.show( 'descricao-painel-el' );		
	}
			
}
export const boletimTela = new BoletimTelaService(); 