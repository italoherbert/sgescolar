
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

export default class SelectService {
		
	carregaEscolasSelect( elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/escola/lista/', {
			ftexto : ( d ) => d.nome,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a escola"
		}, onparams );		
	}
		
	carregaAnosLetivosSelect( escolaId, elid, onparams ) {				
		this.carregaEntidadeSelect( elid, '/api/anoletivo/lista/'+escolaId, {
			ftexto : ( d ) => d.ano,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione o ano letivo"
		}, onparams );
			
	}
	
	carregaCursosSelect( escolaId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/curso/lista/'+escolaId, {
			ftexto : ( d ) => d.descricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione o curso"
		}, onparams );				
	}
	
	carregaSeriesSelect( cursoId, elid, onparams ) {			
		this.carregaEntidadeSelect( elid, '/api/serie/lista/'+cursoId, {
			ftexto : ( d ) => d.descricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a série"
		}, onparams );			
	}
	
	carregaTurmasPorSerieSelect( serieId, elid, onparams ) {			
		this.carregaEntidadeSelect( elid, '/api/turma/lista/porserie/'+serieId, {
			ftexto : ( d ) => d.descricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a turma"
		}, onparams );			
	}
	
	carregaTurmasPorAnoLetivoSelect( anoLetivoId, elid, onparams ) {			
		this.carregaEntidadeSelect( elid, '/api/turma/lista/poranoletivo/'+anoLetivoId, {
			ftexto : ( d ) => d.descricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a turma"
		}, onparams );			
	}
	
	carregaDisciplinasSelect( serieId, elid, onparams ) {			
		this.carregaEntidadeSelect( elid, '/api/disciplina/lista/'+serieId, {
			ftexto : ( d ) => d.descricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a disciplina"
		}, onparams );			
	}
	
	carregaPeriodosSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/periodos', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o período', valor : '0' }
		} );
	}
	
	carregaCursoModalidadesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/curso-modalidades', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a modalidade', valor : '0' }
		} );
	}
	
	carregaSexosSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/sexos', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o sexo', valor : '0' }
		} );
	}
	
	carregaEstadosCivisSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/estados-civis', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o estado civil', valor : '0' }
		} );
	}
	
	carregaNacionalidadesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/nacionalidades', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a nacionalidade', valor : '0' }
		} );
	}
	
	carregaRacasSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/racas', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a raça', valor : '0' }
		} );
	}
	
	carregaReligioesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/religioes', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a religião', valor : '0' }
		} );
	}
	
	carregaEscolaridadesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/escolaridades', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a escolaridade', valor : '0' }
		} );
	}
	
	carregaFuncionarioFuncoesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/funcionario-funcoes', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a função', valor : '0' }
		} );
	}
		
	carregaAlunoPerfisSelect( elid, onparams ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/aluno', {
			onparams : onparams
		} );
	}
	
	carregaProfessorPerfisSelect( elid, onparams ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/professor', {
			onparams : onparams
		} );		
	}
	
	carregaSecretarioPerfisSelect( elid, onparams ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/secretario', {
			onparams : onparams
		} );	
	}
	
	carregaSelect( elid, url, params ) {
		sistema.ajax( 'GET', url, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
					
				let defaultOption = undefined;		
				let onchange = undefined;										
				let onload = undefined;
				if ( params !== undefined && params !== null ) {
					defaultOption = params.defaultOption;
					if ( params.onparams !== undefined && params.onparams !== null ) {
						onchange = params.onparams.onchange;
						onload = params.onparams.onload;
					}
				}			
							
				let select_el = document.getElementById( elid );
				select_el.onchange = onchange; 
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : dados.names,
					textos : dados.labels, 
					defaultOption : defaultOption
				} );
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );
	}	
	
	carregaEntidadeSelect( elid, url, txtparams, onparams ) {		
		sistema.ajax( "GET", url, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let names = [];
				let labels = [];
				for( let i = 0; i < dados.length; i++ ) {
					labels.push( txtparams.ftexto( dados[ i ] ) );
					names.push( txtparams.fvalor( dados[ i ] ) );
				}
						
				let onchange = ( onparams !== undefined && onparams !== null ? onparams.onchange : undefined );										
				let onload = ( onparams !== undefined && onparams !== null ? onparams.onload : undefined );			
												
				let select_el = document.getElementById( elid );
				select_el.onchange = onchange; 
																
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : names,
					textos : labels, 
					defaultOption : { texto : txtparams.defaultTexto, valor : '0' } 
				} );
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );	
	}
	
	carregaPerfisSelect( elid, url, params ) {
		sistema.ajax( "GET", url, {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				let onchange = undefined;										
				let onload = undefined;
				if ( params !== undefined && params !== null ) {
					if ( params.onparams !== undefined && params.onparams !== null ) {
						onchange = params.onparams.onchange;
						onload = params.onparams.onload;
					}
				}			
					
				let select_el = document.getElementById( elid );
				select_el.onchange = onchange;
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : dados.names,
					textos : dados.labels
				} );				
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );
	}
		
}
export const selectService = new SelectService();