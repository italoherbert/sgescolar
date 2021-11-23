
import {sistema} from '../../../../sistema/Sistema.js';
import {htmlBuilder} from '../../../../sistema/util/HTMLBuilder.js';

export default class SelectService {
		
	carregaEscolasSelect( elid, onparams ) {
		sistema.ajax( "GET", "/api/escola/lista", {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
																
				let textos = [];
				let valores = [];
				for( let i = 0; i < dados.length; i++ ) {
					textos.push( dados[ i ].nome );
					valores.push( dados[ i ].id );
				}
				
				let onchange = ( onparams !== undefined && onparams !== null ? onparams.onchange : undefined );										
				let onload = ( onparams !== undefined && onparams !== null ? onparams.onload : undefined );			
						
				let select_el = document.getElementById( elid );
				select_el.onchange = onchange; 
																
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : valores,
					textos : textos, 
					defaultOption : { texto : 'Selecione a escola', valor : '0' } 
				} );	
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );												
			}
		} );
	}
		
	carregaAnosLetivosSelect( escolaId, elid, onparams ) {		
		sistema.ajax( "GET", '/api/anoletivo/lista/'+escolaId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let textos = [];
				let valores = [];
				for( let i = 0; i < dados.length; i++ ) {
					textos.push( dados[ i ].ano );
					valores.push( dados[ i ].id );
				}
						
				let onchange = ( onparams !== undefined && onparams !== null ? onparams.onchange : undefined );										
				let onload = ( onparams !== undefined && onparams !== null ? onparams.onload : undefined );			
												
				let select_el = document.getElementById( elid );
				select_el.onchange = onchange; 
																
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : valores,
					textos : textos, 
					defaultOption : { texto : 'Selecione o ano', valor : '0' } 
				} );
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );	
	}
	
	carregaCursosSelect( escolaId, elid, onparams ) {		
		sistema.ajax( "GET", '/api/curso/lista/'+escolaId, {
			sucesso : function( resposta ) {
				let dados = JSON.parse( resposta );
									
				let textos = [];
				let valores = [];
				for( let i = 0; i < dados.length; i++ ) {
					textos.push( dados[ i ].nome );
					valores.push( dados[ i ].id );
				}
						
				let onchange = ( onparams !== undefined && onparams !== null ? onparams.onchange : undefined );										
				let onload = ( onparams !== undefined && onparams !== null ? onparams.onload : undefined );			
												
				let select_el = document.getElementById( elid );
				select_el.onchange = onchange; 
																
				select_el.innerHTML = htmlBuilder.novoSelectOptionsHTML( {
					valores : valores,
					textos : textos, 
					defaultOption : { texto : 'Selecione o curso', valor : '0' } 
				} );
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );	
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
		
	carregaAlunoPerfisSelect( elid ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/aluno' );
	}
	
	carregaProfessorPerfisSelect( elid ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/professor' );		
	}
	
	carregaSecretarioPerfisSelect( elid ) {
		this.carregaPerfisSelect( elid, '/api/tipos/perfis/secretario' );		
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
					textos : dados.textos
				} );				
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );
	}
	
	carregaSelect( elid, url, params ) {
		sistema.ajax( 'GET', url, {
			sucesso : ( resposta) => {
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
					textos : dados.textos, 
					defaultOption : defaultOption
				} );
				
				if ( typeof( onload ) === 'function' )
					onload.call( this );
			}
		} );
	}	
		
}
export const selectService = new SelectService();