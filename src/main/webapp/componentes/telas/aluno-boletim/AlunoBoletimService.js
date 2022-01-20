
import {sistema} from '../../../sistema/Sistema.js';
import {conversor} from '../../../sistema/util/Conversor.js';
import {htmlBuilder} from '../../../sistema/util/HTMLBuilder.js';

import TabelaComponent from '../../component/tabela/TabelaComponent.js';

export default class AlunoBoletimService {			
		
	mediasBoletimTabelaCampos = [ 'Disciplina', 'Média', 'detalhes' ];
	avaliacoesBoletimTabelaCampos = [ 'Data de avaliação', 'Nota', 'Peso' ];
	
	boletim = null;
	
	constructor() {		
		this.mediasTabelaComponent = new TabelaComponent( 'medias_', 'boletim_tabela_el', this.mediasBoletimTabelaCampos );
		this.avaliacoesTabelaComponent = new TabelaComponent( 'avaliacoes_', 'boletim_tabela_el', [] );					
	}
	
	onCarregado() {
		this.mediasTabelaComponent.configura( {} );
		this.avaliacoesTabelaComponent.configura( {} );
		
		this.mediasTabelaComponent.carregaHTML();
		this.avaliacoesTabelaComponent.carregaHTML();
		
		this.carregaBoletim();
	}
		
	carregaBoletim() {		
		let alunoId = this.params.alunoId;
		if ( alunoId === undefined || alunoId === null )		
			alunoId = sistema.globalVars.entidadeId;
		
		const instance = this;
		sistema.ajax( 'GET', '/api/boletim/gera/atual/'+alunoId, {
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
						let detalhesLink = htmlBuilder.novoLinkDetalhesHTML( 'alunoBoletim.detalhes( '+i+' )' );
						
						tdados[ i ].push( conversor.formataFloat( media ) );	
						tdados[ i ].push( detalhesLink );
					}
				}
				
				instance.mediasTabelaComponent.carregaTBody( tdados );				
			},
			erro : ( msg ) => {
				instance.mediasTabelaComponent.mostraErro( msg );
			}
		} );
	}
	
	detalhes( i ) {
		let discBoletim = this.boletim.disciplinasBoletins[ i ];
		
		document.getElementById( 'avaliacoes_disciplina' ).innerHTML = discBoletim.disciplinaDescricao;
		
		let tdados = [];
		for( let j = 0; j < discBoletim.avaliacoes.length; j++ ) {
			let avaliacao = discBoletim.avaliacoes[ j ];
			
			tdados[ j ] = new Array();
			tdados[ j ].push( avaliacao.dataAvaliacao );
			tdados[ j ].push( conversor.formataFloat( avaliacao.nota ) );
			tdados[ j ].push( conversor.formataFloat( avaliacao.peso ) );
		}
		
		this.avaliacoesTabelaComponent.tabelaCampos = this.avaliacoesBoletimTabelaCampos;
		this.avaliacoesTabelaComponent.carregaTHead();
		this.avaliacoesTabelaComponent.carregaTBody( tdados );
	}
			
}
export const alunoBoletim = new AlunoBoletimService(); 