
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

export default class SelectService {
				
	disciplinaSiglasOptionsHTML( turmaDisciplinas ) {
		let valores = [];
		let textos = [];
		for( let i = 0; i < turmaDisciplinas.length; i++ ) {	
			valores[ i ] = turmaDisciplinas[ i ].id;
			textos[ i ] = turmaDisciplinas[ i ].disciplinaSigla;
		}
		
		return htmlBuilder.novoSelectOptionsHTML( {
			valores : valores,
			textos : textos,
			defaultOption : { texto : 'Aula vaga', valor : '-1' }
		} );
	}			
				
	carregaInstituicoesSelect( elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/instituicao/lista/', {
			ftexto : ( d ) => d.razaoSocial,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a instituição"
		}, onparams );		
	}	
		
	carregaEscolasSelect( instituicaoId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/escola/lista/'+instituicaoId, {
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
	
	carregaAnosLetivosPorAlunoSelect( alunoId, elid, onparams ) {				
		this.carregaEntidadeSelect( elid, '/api/anoletivo/lista/poraluno/'+alunoId, {
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
			ftexto : ( d ) => d.descricaoDetalhada,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a turma"
		}, onparams );			
	}
	
	carregaTurmasPorAnoLetivoSelect( anoLetivoId, elid, onparams ) {			
		this.carregaEntidadeSelect( elid, '/api/turma/lista/poranoletivo/'+anoLetivoId, {
			ftexto : ( d ) => d.descricaoDetalhada,
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
	
	carregaTurmaDisciplinasSelect( turmaId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/turma-disciplina/lista/porturma/'+turmaId, {
			ftexto : ( d ) => d.disciplinaDescricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a disciplina"
		}, onparams );		
	}
	
	carregaProfessoresPorTurmaSelect( turmaId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/professor/lista/porturma/'+turmaId, {
			ftexto : ( d ) => d.funcionario.pessoa.nome,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione o professor"
		}, onparams );		
	}
	
	carregaDisciplinasPorProfessorSelect( professorId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/turma-disciplina/lista/porprof/'+professorId, {
			ftexto : ( d ) => d.disciplinaDescricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a disciplina"
		}, onparams );		
	}
	
	carregaProfessorAlocacaoPorTurmaDisciplinaSelect( turmaDisciplinaId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/professor-alocacao/lista/por-turma-disc/'+turmaDisciplinaId, {
			ftexto : ( d ) => d.professorNome,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione o professor"
		}, onparams );		
	}
		
	carregaAulasSelect( turmaDisciplinaId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/horario/lista/aulas/'+turmaDisciplinaId, {
			ftexto : ( d ) => d.semanaDiaLabel + ", " + d.numeroAula + "º aula",
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a aula"
		}, onparams );		
	}
		
	carregaAvaliacoesNaoRealizadasSelect( turmaDisciplinaId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/avaliacao/lista/naorealizadas/'+turmaDisciplinaId, {
			ftexto : ( d ) => d.dataAgendamento,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione a avaliação"
		}, onparams );
	}
	
	carregaEnsinoPlanejamentosSelect( professorAlocacaoId, elid, onparams ) {
		this.carregaEntidadeSelect( elid, '/api/planejamento/lista/plan-ensino/'+professorAlocacaoId, {
			ftexto : ( d ) => d.descricao,
			fvalor : ( d ) => d.id,
			defaultTexto : "Selecione o planejamento de ensino"
		}, onparams );
	}
	
	carregaPlanejamentoTiposSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/planejamento-tipos', {
			onparams : onparams
		} );
	}
	
	carregaFrequenciaTiposSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/frequencia-tipos', {
			onparams : onparams
		} );
	}
	
	carregaPeriodosSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/periodos', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o período', valor : "-1" }
		} );
	}
	
	carregaCursoModalidadesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/curso-modalidades', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a modalidade', valor : "-1" }
		} );
	}
	
	carregaSexosSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/sexos', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o sexo', valor : "-1" }
		} );
	}
	
	carregaEstadosCivisSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/estados-civis', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o estado civil', valor : "-1" }
		} );
	}
	
	carregaNacionalidadesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/nacionalidades', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a nacionalidade', valor : "-1" }
		} );
	}
	
	carregaRacasSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/racas', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a raça', valor : "-1" }
		} );
	}
	
	carregaReligioesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/religioes', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a religião', valor : "-1" }
		} );
	}
	
	carregaEscolaridadesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/escolaridades', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a escolaridade', valor : "-1" }
		} );
	}
	
	carregaFuncionarioFuncoesSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/funcionario-funcoes', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione a função', valor : "-1" }
		} );
	}
	
	carregaTurnosSelect( elid, onparams ) {
		this.carregaSelect( elid, '/api/tipos/turnos', {
			onparams : onparams,
			defaultOption : { texto : 'Selecione o turno', valor : "-1" }
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
	
	carregaAdminPerfisSelect( elid, onparams ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/admin', {
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
																			
				let html = "";		
				if ( names.length == 1 ) {
					html = htmlBuilder.novoSelectOptionsHTML( {
						valores : [],
						textos : [], 
						defaultOption : { texto : labels[0], valor : names[0] } 
					} );
				} else {					 					
					html = htmlBuilder.novoSelectOptionsHTML( {
						valores : names,
						textos : labels, 
						defaultOption : { texto : txtparams.defaultTexto, valor : "-1" }
					} );
				}							
																
				select_el.innerHTML = html;														
							
				if ( typeof( onload ) === 'function' )
					onload.call( this );	
					
				if ( names.length == 1 && onchange !== undefined && onchange !== null ) 
					select_el.onchange();								
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
	
	carregaUmaOptionSelect( elid, valor, texto ) {
		document.getElementById( elid ).innerHTML = htmlBuilder.novoSelectOptionsHTML( {
			valores : [],
			textos : [],
			defaultOption : { texto : texto, valor : valor }
		} );
	}
		
}
export const selectService = new SelectService();