
import {sistema} from './Sistema.js';

export default class WSLocalidades {
	
	carregaEstados( select_uf_el, select_municipio_el, params ) {	
		const instance = this;			
		sistema.ajax( 'GET', 'https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome', {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				let uf_sel_el = document.getElementById( select_uf_el );
				if ( uf_sel_el === undefined || uf_sel_el === null )
					throw "ID de select de carregamento de estados não encontrado.";
				
				let html = "<option value=\"0\">Selecione o estado</option>";
				for( let i = 0; i < dados.length; i++ )
					html += "<option value=\""+dados[i].id+"\">"+dados[i].nome+"</option>";
				
				uf_sel_el.innerHTML = html;	
				
				uf_sel_el.onchange = ( e ) => {		
					let ufid = document.getElementById( select_uf_el ).value;						
					instance.carregaMunicipios( ufid, select_municipio_el, params );
				};						
				if ( params !== undefined && params !== null )
					if ( typeof( params.estadosCarregados ) === 'function' )
						params.estadosCarregados.call( this, dados );
			},
			erro : ( msg ) => {
				if ( params !== undefined && params !== null )
					if ( typeof( params.estadosNaoCarregados ) === 'function' )
						params.estadosNaoCarregados.call( this, 'Houve um problema no carregamento de estados.' );
			}
		} );
	}
	
	carregaMunicipios( estadoID, select_elid, params ) {
		sistema.ajax( 'GET', 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'+estadoID+'/municipios?orderBy=nome', {
			sucesso : ( resposta ) => {
				let dados = JSON.parse( resposta );
				
				let select_el = document.getElementById( select_elid );
				if ( select_el === undefined || select_el === null )
					throw "ID de select de carregamento de minicípios não encontrado.";
				
				let html = "<option value=\"0\">Selecione o município</option>";
				for( let i = 0; i < dados.length; i++ )
					html += "<option value=\""+dados[i].id+"\">"+dados[i].nome+"</option>";
				
				select_el.innerHTML = html;
																
				if ( params !== undefined && params !== null )
					if ( typeof( params.municipiosCarregados ) == 'function' )
						params.municipiosCarregados.call( this, dados );				
			},
			erro : ( msg ) => {
				if ( params !== undefined && params !== null )
					if ( typeof( params.municipiosNaoCarregados ) == 'function' )
						params.municipiosNaoCarregados.call( this, 'Houve um problema no carregamento de municípios do estado.' );
			}
		} );
	}
		
}
export const wsLocalidades = new WSLocalidades();